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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import qyw.xhx.zwzs.MainActivity;
import qyw.xhx.zwzs.Main_view;
import qyw.xhx.zwzs.MyApplication;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.DateUtil;
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
    private Button againbtn;
    private TextView title;
    private TextView oltmc;
    private List<Ont_Down> mDatas;
    private Ont_Down_Adapter ont_down_adapter;
    private Handler handler=null;

    private String ponkou;
    private String olt_cj;
    private String olt_name;
    private String pon_name;
    private String ponkou_id;
    private String olt_ip;
    private String message;
    private String number;
    private String sjm;
    private String city;
    private String city_id;
    private String server_url;

    private Socket mSocket;
    private BufferedReader mReader = null;
    private OutputStream mWriter = null;
    private MessageTransmit mTransmit;
    private MyApplication myApplication;//初始化全局变量

    private static final String SOCKET_IP = "122.80.61.118";
    private static final int SOCKET_PORT = 9061;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ont_down_layout);
        //接收传值

        Intent intent =getIntent();
        pon_name=intent.getStringExtra("pon_name");
        olt_name=intent.getStringExtra("olt_name");
        ponkou_id=intent.getStringExtra("ponkou_id");
        olt_ip=intent.getStringExtra("olt_ip");
        Log.d("接收到的ponkou_id",ponkou_id);
        Log.d("接收到的pon_name",pon_name);
        Log.d("接收到的olt_ip",olt_ip);
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

        againbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Log.d("zaicichaxun","再次查询");
                initDataAgain();


            }
        });

    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.fenguangqi_list_view);
        backbtn=(Button) findViewById(R.id.back_button);
        againbtn=(Button) findViewById(R.id.again);
        title=(TextView) findViewById(R.id.title_text);
        oltmc=(TextView) findViewById(R.id.olt_mc);
    }
    //方法；初始化Data
    private void initData() {
        mDatas = new ArrayList<Ont_Down>();
        Thread loginRunnable = new Thread() {
            @Override
            public void run() {
                initSocket(olt_ip, pon_name);
            }
        };
        loginRunnable.start();
        //
        Log.d("MYKEY",MyKey.key());

        //为数据绑定适配器
        ont_down_adapter = new Ont_Down_Adapter(this, mDatas);
        listView.setAdapter(ont_down_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
    private void initDataAgain() {
        mDatas.clear();
        Thread loginRunnable = new Thread() {
            @Override
            public void run() {
                initSocketAgain(olt_ip, pon_name);
            }
        };
        loginRunnable.start();
        //
        Log.d("MYKEY",MyKey.key());

        //为数据绑定适配器
        ont_down_adapter = new Ont_Down_Adapter(this, mDatas);
        listView.setAdapter(ont_down_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
    private void initSocket(String ip,String pon) {
        mSocket = new Socket();
        String nowyear= DateUtil.getNowYear();
        Log.d("yyyy",nowyear);
        int ny;
        ny = Integer.parseInt(nowyear);
//        Log.d("username",us);
//        Log.d("password",ps);

        try {
            mSocket.connect(new InetSocketAddress(SOCKET_IP, SOCKET_PORT), 3000);
            mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mWriter = mSocket.getOutputStream();
            String content = mReader.readLine();
            Log.d("连接获取加密",content);
            JSONObject jsonObject = new JSONObject(content);
            int key= jsonObject.getInt("key");
//            Log.d("第一次",content);
            if (content!=null){
                //发送验证信息
                int jiami=ny+key;
                JSONObject fanhui = new JSONObject();
                fanhui.put("askey", jiami);
                fanhui.put("olt_ip", olt_ip);
                fanhui.put("pon", pon_name);
                fanhui.put("action", "pon_down");
                fanhui.put("sjm", "first");
                fanhui.put("zh", number);
                mWriter.write(fanhui.toString(1).getBytes());
                String content1 = mReader.readLine();
                Log.d("第二次",content1);
//                解析
                JSONObject jsonObject_ok = new JSONObject(content1);
                String content2= jsonObject_ok.getString("content");
                String number= jsonObject_ok.getString("number");
                if (content2.equals("scuess")){
                    queryFromServer(server_url+"?type=531_hw_pon_down&key="+MyKey.key()+"&id="+number , "county");
                    Log.d("数据获取网址:",server_url+"?type=531_hw_pon_down&key="+MyKey.key()+"&id="+number);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initSocketAgain(String ip,String pon) {
        mSocket = new Socket();
        String nowyear= DateUtil.getNowYear();
        Log.d("yyyy",nowyear);
        int ny;
        ny = Integer.parseInt(nowyear);
//        Log.d("username",us);
//        Log.d("password",ps);

        try {
            mSocket.connect(new InetSocketAddress(SOCKET_IP, SOCKET_PORT), 3000);
            mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mWriter = mSocket.getOutputStream();
            String content = mReader.readLine();
            Log.d("连接获取加密",content);
            JSONObject jsonObject = new JSONObject(content);
            int key= jsonObject.getInt("key");
//            Log.d("第一次",content);
            if (content!=null){
                //发送验证信息
                int jiami=ny+key;
                JSONObject fanhui = new JSONObject();
                fanhui.put("askey", jiami);
                fanhui.put("olt_ip", olt_ip);
                fanhui.put("pon", pon_name);
                fanhui.put("action", "pon_down");
                fanhui.put("sjm", sjm);
                fanhui.put("zh", number);
                mWriter.write(fanhui.toString(1).getBytes());
                String content1 = mReader.readLine();
                Log.d("第二次",content1);
//                解析
                JSONObject jsonObject_ok = new JSONObject(content1);
                String content2= jsonObject_ok.getString("content");
                String number= jsonObject_ok.getString("number");
                if (content2.equals("scuess")){
                    queryFromServer(server_url+"?type=531_hw_pon_down1&key="+MyKey.key()+"&id="+number , "county");

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据传入的地址从服务器查询数据
    private void queryFromServer(String address, final String type) {
        showProgressDialog("正在获取数据");
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
                             closeProgressDialog("获取完毕");
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
                        closeProgressDialog("获取完毕");
                        Toast.makeText(Ont_Down_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Ont_Down> leibiao=gson.fromJson(jsonData,new TypeToken<List<Ont_Down>>(){}.getType());
        mDatas.clear();
        for (Ont_Down ont_down:leibiao){
//            Log.d("MainActivity", "COUNTY_ID is " + county.getCOUNTY_ID());
            ont_down = new Ont_Down(ont_down.getIP(),ont_down.getPON(),
                    ont_down.getAHTHOR_VALUE(),ont_down.getBAND_ACCOUNT(),ont_down.getSTATE(),ont_down.getCOVER_DEVICE(),ont_down.getSJM());
            sjm=ont_down.getSJM();
            mDatas.add(ont_down);
        }
        Ont_Down_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ont_down_adapter.notifyDataSetChanged();
                againbtn.setVisibility(View.VISIBLE);
            }
        });

    }

    public void showProgressDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressDialog ==null)
                {
                    progressDialog = new ProgressDialog(Ont_Down_view.this);
                    progressDialog.setMessage("正在加载...");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
                progressDialog.show();
            }
        });
    }

    public void closeProgressDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

}