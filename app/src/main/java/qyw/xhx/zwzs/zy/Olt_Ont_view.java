package qyw.xhx.zwzs.zy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import qyw.xhx.zwzs.MyApplication;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.HttpUtil;
import qyw.xhx.zwzs.util.Md5Utils;
import qyw.xhx.zwzs.util.MessageTransmit;
import qyw.xhx.zwzs.wh.Olt;
import qyw.xhx.zwzs.wh.OltAdapter;
import qyw.xhx.zwzs.wh.Oltport_view;

public class Olt_Ont_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private Button oltbutton;
    private Button backbtn;
    private TextView ponchaxun;
    private TextView title;
    private List<Olt> mDatas;
    private OltAdapter oltAdapter;
    private EditText olteditText;
    private Handler handler=null;

    private String olt_ip;
    private String olt_cj;
    private String olt_mc;
    private String olt_id;
    private String message;
    private String number;
    private String city;
    private String city_id;
    private String server_url;

    private Socket mSocket;
    private BufferedReader mReader = null;
    private OutputStream mWriter = null;
    private MessageTransmit mTransmit;
    private MyApplication myApplication;//初始化全局变量
    private static final String SOCKET_IP = "122.80.61.118";
    private static final int SOCKET_PORT = 9051;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pon_layout);
        myApplication = (MyApplication) getApplication(); //获得自定义的应用程序YApp
        number=myApplication.getNumber();
        city=myApplication.getCity();
        city_id=myApplication.getCity_id();
        server_url=myApplication.getServer_url();
        Log.i("非标查询", "InitLabel:"+myApplication.getNumber());   //将我们放到进程中的全局变量拿出来，看是不是我们曾经设置的值
        Log.i("非标查询", "InitLabel:"+myApplication.getCity());   //将我们放到进程中的全局变量拿出来，看是不是我们曾经设置的值

        //创建属于主线程的handler
        handler=new Handler();
        initView();
        title.setText("OLT查询-ONT下线");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        oltbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id =olteditText.getText().toString();
                    if (id.equals("")){
                        Toast.makeText(Olt_Ont_view.this, "输入数据为空", Toast.LENGTH_SHORT).show();
                    }else{
                        initData(id);
                        ponchaxun.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        Toast.makeText(Olt_Ont_view.this, "有输入", Toast.LENGTH_SHORT).show();
                    }
            }
        });


    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        oltbutton=(Button) findViewById(R.id.olt_search);
        olteditText=(EditText) findViewById(R.id.olt);
        ponchaxun=(TextView) findViewById(R.id.pon_chaxun);
        ponchaxun.setMovementMethod(ScrollingMovementMethod.getInstance());
        backbtn=(Button) findViewById(R.id.back_button);
        title=(TextView) findViewById(R.id.title_text);

    }
    //方法；初始化Data
    private void initData(String id) {
        mDatas = new ArrayList<Olt>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String t=format.format(new Date());
        String key=Md5Utils.md5("khsl"+t);
        Log.d("key",key);

        queryFromServer(server_url+"?type=sheng_olt&key="+key+"&id="+olteditText.getText().toString()+"&city_id="+city_id , "county");

        //为数据绑定适配器
        oltAdapter = new OltAdapter(this, mDatas);
        listView.setAdapter(oltAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Olt olt =mDatas.get(position);
                olt_mc=olt.getUSER_LABEL();
                olt_ip=olt.getDEV_IPADDR();
                olt_cj=olt.getVENDOR();
                olt_id=olt.getOLT_ID();
                Toast.makeText(Olt_Ont_view.this,olt.getOLT_ID(),Toast.LENGTH_SHORT).show();
                //跳转Oltport_view.class
                Intent intent = new Intent(Olt_Ont_view.this,Ont_Oltport_view.class);
                intent.putExtra("olt_id",olt_id);
                intent.putExtra("olt_mc",olt_mc);
                intent.putExtra("olt_ip",olt_ip);
                startActivity(intent);
                //需要查询等待showProgressDialog
//                将listview显示出来，同时listview 设为gone
//                ..............
//                ponchaxun.setVisibility(View.VISIBLE);
//                ponchaxun.setText("正在查询，请稍后....");
//                listView.setVisibility(View.INVISIBLE);
////                需要在线程中
//                Thread loginRunnable = new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        initSocket(olt_ip,olt_cj);
//                    }
//                };
//                loginRunnable.start();
//                Toast.makeText(Feibiao_view.this,olt.getDEV_IPADDR(),Toast.LENGTH_SHORT).show();
//                ...................
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
                boolean result = false;
                if ("".equals(responseText)){
                    Toast.makeText(Olt_Ont_view.this, "未查询到数据", Toast.LENGTH_SHORT).show();
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
                     Olt_Ont_view.this.runOnUiThread(new Runnable() {
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
                Olt_Ont_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Olt_Ont_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Olt> leibiao=gson.fromJson(jsonData,new TypeToken<List<Olt>>(){}.getType());
        mDatas.clear();
        for (Olt olt:leibiao){
//            Log.d("MainActivity", "COUNTY_ID is " + county.getCOUNTY_ID());
            olt = new Olt(olt.getUSER_LABEL(),olt.getEQUIP_ROOM(),olt.getDEV_IPADDR(),olt.getVENDOR(),olt.getOLT_ID());
            mDatas.add(olt);
        }
        Olt_Ont_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                oltAdapter.notifyDataSetChanged();
            }
        });

    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Olt_Ont_view.this);
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