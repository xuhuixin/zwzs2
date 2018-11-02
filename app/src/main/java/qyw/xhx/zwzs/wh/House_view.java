package qyw.xhx.zwzs.wh;

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

public class House_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private Button backbtn;
    private TextView title;
    private TextView oltmc;
    private List<House> mDatas;
    private HouseAdapter houseAdapter;
    private Handler handler=null;

    private String ponkou;
    private String olt_cj;
    private String olt_name;
    private String pon_name;
    private String fenguangqi_name;
    private String fenguangqi_id;
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
        setContentView(R.layout.house_layout);
        //接收传值

        Intent intent =getIntent();
        olt_name=intent.getStringExtra("olt_name");
        pon_name=intent.getStringExtra("pon_name");
        fenguangqi_name=intent.getStringExtra("fenguangqi_name");
        fenguangqi_id=intent.getStringExtra("fenguangqi_id");
        Log.d("接收到的fenguangqi_id",fenguangqi_id);
        myApplication = (MyApplication) getApplication(); //获得自定义的应用程序YApp
        number=myApplication.getNumber();
        city=myApplication.getCity();
        city_id=myApplication.getCity_id();
        server_url=myApplication.getServer_url();

        //创建属于主线程的handler
//        handler=new Handler();
        initView();
        oltmc.setText(olt_name+"-"+pon_name+"-"+fenguangqi_name);
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
        listView = (ListView) findViewById(R.id.house_list_view);
        backbtn=(Button) findViewById(R.id.back_button);
        title=(TextView) findViewById(R.id.title_text);
        oltmc=(TextView) findViewById(R.id.olt_mc);
    }
    //方法；初始化Data
    private void initData() {
        mDatas = new ArrayList<House>();
        Log.d("MYKEY",MyKey.key());
        queryFromServer(server_url+"?type=sheng_house&key="+MyKey.key()+"&id="+fenguangqi_id+"&city_id="+city_id , "county");
        //为数据绑定适配器
        houseAdapter = new HouseAdapter(this, mDatas);
        listView.setAdapter(houseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ponkou=oltport.getUSER_LABEL();
//                Toast.makeText(House_view.this,fenguangqi.getINT_ID(),Toast.LENGTH_SHORT).show();
                //跳转Oltport_view.class
//                Intent intent = new Intent(Feibiao_view.this,Oltport_view.class);
//                intent.putExtra("olt_id",olt_id);
//                intent.putExtra("olt_mc",olt_mc);
//                startActivity(intent);

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
                    Toast.makeText(House_view.this, "未查询到数据", Toast.LENGTH_SHORT).show();
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
                     House_view.this.runOnUiThread(new Runnable() {
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
                House_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(House_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<House> leibiao=gson.fromJson(jsonData,new TypeToken<List<House>>(){}.getType());
        mDatas.clear();
        for (House house:leibiao){
            Log.d("MainActivity", "COUNTY_ID is " + house.getSFZY());
            house = new House(house.getFULL_ADDR(),house.getSFZY());
            mDatas.add(house);
        }
        House_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                houseAdapter.notifyDataSetChanged();
            }
        });

    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(House_view.this);
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