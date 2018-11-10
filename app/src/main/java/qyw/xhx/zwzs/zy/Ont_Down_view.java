package qyw.xhx.zwzs.zy;
//该页需要登陆设备获取第一次pon口下所有在线SDJN
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import qyw.xhx.zwzs.MyApplication;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.HttpUtil;
import qyw.xhx.zwzs.util.MessageTransmit;
import qyw.xhx.zwzs.util.MyKey;
import qyw.xhx.zwzs.wh.Fenguangqi;
import qyw.xhx.zwzs.wh.FenguangqiAdapter;
import qyw.xhx.zwzs.wh.House_view;

public class Ont_Down_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private Button backbtn;
    private TextView title;
    private TextView oltmc;
    private List<Fenguangqi> mDatas;
    private FenguangqiAdapter fenguangqiAdapter;
    private Handler handler=null;

    private String ponkou;
    private String olt_cj;
    private String olt_name;
    private String pon_name;
    private String ponkou_id;
    private String message;
    private String number;
    private String city;
    private String city_id;
    private String server_url;


    private BufferedReader mReader = null;
    private OutputStream mWriter = null;
    private MessageTransmit mTransmit;
    private MyApplication myApplication;//初始化全局变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ont_down_layout);
        //接收传值

        Intent intent =getIntent();
        pon_name=intent.getStringExtra("pon_name");
        olt_name=intent.getStringExtra("olt_name");
        ponkou_id=intent.getStringExtra("ponkou_id");
        Log.d("接收到的ponkou_id",ponkou_id);
        myApplication = (MyApplication) getApplication(); //获得自定义的应用程序YApp
        number=myApplication.getNumber();
        city=myApplication.getCity();
        city_id=myApplication.getCity_id();
        server_url=myApplication.getServer_url();

        //创建属于主线程的handler
//        handler=new Handler();
        initView();
        oltmc.setText(olt_name+"-"+pon_name);
        initData();
//        title.setText("非标小区OLT查询");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.fenguangqi_list_view);
        backbtn=(Button) findViewById(R.id.back_button);
        title=(TextView) findViewById(R.id.title_text);
        oltmc=(TextView) findViewById(R.id.olt_mc);
    }
    //方法；初始化Data
    private void initData() {
        mDatas = new ArrayList<Fenguangqi>();
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        String t=format.format(new Date());
//        String key=Md5Utils.md5("khsl"+t);
        //先往socket服务器发送查询PON口在线状态

        //
        Log.d("MYKEY",MyKey.key());
        queryFromServer(server_url+"?type=sheng_cover_pos&key="+MyKey.key()+"&id="+ponkou_id+"&city_id="+city_id , "county");
        //为数据绑定适配器
        fenguangqiAdapter = new FenguangqiAdapter(this, mDatas);
        listView.setAdapter(fenguangqiAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fenguangqi fenguangqi =mDatas.get(position);
//                ponkou=oltport.getUSER_LABEL();
                Toast.makeText(Ont_Down_view.this,fenguangqi.getINT_ID(),Toast.LENGTH_SHORT).show();
                //跳转House_view.class
                Intent intent = new Intent(Ont_Down_view.this,House_view.class);

                //下一页上面显示OLT名称、pon口名称，同时传入pon_id
                intent.putExtra("olt_name",olt_name);
                intent.putExtra("pon_name",pon_name);
                intent.putExtra("fenguangqi_name",fenguangqi.getZH_LABEL());
                intent.putExtra("fenguangqi_id",fenguangqi.getINT_ID());
                startActivity(intent);

            }
        });

    }



    //根据传入的地址从服务器查询数据
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
             @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                 Log.d("服务器返回",responseText);
                boolean result = false;
                if ("".equals(responseText)){
                    Toast.makeText(Ont_Down_view.this, "未查询到数据", Toast.LENGTH_SHORT).show();
                }
                else {
                    if ("county".equals(type)) {
                        Log.d("aaaaaaa", responseText);
                        /**使用gson解析**/
                        parseJSONWithGSON(responseText);
                        result = true;
                    }
                }
                 if (result) {
                     Log.d("bbbbbb", "判断是否下载完毕");
                     Ont_Down_view.this.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             closeProgressDialog();
//                            Adapter.notifyDataSetChanged();
                         }
                     });
                 }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("SHIBAI","网络加载失败");
                // 通过runOnUiThread()方法回到主线程处理逻辑
                Ont_Down_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Ont_Down_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Fenguangqi> leibiao=gson.fromJson(jsonData,new TypeToken<List<Fenguangqi>>(){}.getType());
        mDatas.clear();
        for (Fenguangqi fenguangqi:leibiao){
//            Log.d("MainActivity", "COUNTY_ID is " + county.getCOUNTY_ID());
            fenguangqi = new Fenguangqi(fenguangqi.getZH_LABEL(),fenguangqi.getINT_ID());
            mDatas.add(fenguangqi);
        }
        Ont_Down_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fenguangqiAdapter.notifyDataSetChanged();
            }
        });

    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Ont_Down_view.this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}