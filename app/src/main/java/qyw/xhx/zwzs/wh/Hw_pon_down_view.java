package qyw.xhx.zwzs.wh;
//该页需要登陆设备获取第一次pon口下所有在线SDJN
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
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
import qyw.xhx.zwzs.MyApplication;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.DateUtil;
import qyw.xhx.zwzs.util.HttpUtil;
import qyw.xhx.zwzs.util.MessageTransmit;
import qyw.xhx.zwzs.util.MyKey;


public class Hw_pon_down_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private Button backbtn;
    private Button message_btn;
    private TextView title;
    private TextView oltmc;
    private List<Pon_down> mDatas;
//    private List<mess> mDatasmess;
    private Pon_down_Adapter pon_down_adapter;
    private Handler handler=null;
    private String ponkou;
    private String olt_cj;
    private String olt_name;
    private String pon_name;
    private String ponkou_id;
    private String olt_ip;
    private TextView message_title;
    private TextView message;
    private String message_all;
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
        setContentView(R.layout.pon_down_layout);
        //接收传值
        Log.d("aaaaaaaaaaa","华为");
        Intent intent =getIntent();
        olt_name=intent.getStringExtra("olt_name");
        olt_ip=intent.getStringExtra("olt_ip");
        olt_cj=intent.getStringExtra("olt_cj");
        pon_name=intent.getStringExtra("pon_name");
        ponkou_id=intent.getStringExtra("ponkou_id");
        Log.d("接收到的olt_name",olt_name);
        Log.d("接收到的olt_port_id",ponkou_id);
        Log.d("接收到的olt_cj",olt_cj);
        Log.d("接收到的olt_ip",olt_ip);
        Log.d("接收到的pon_name",pon_name);
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


        title.setText("PON口状态查询");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MYKEY",MyKey.key());
                queryFromServer(server_url+"?type=531_pon_message&key="+MyKey.key()+"&olt_port_id="+ponkou_id , "county");
//                mDatas = new ArrayList<Oltport>();
            }
        });
    }
    //根据传入的地址从服务器查询数据
    private void queryFromServer(String address, final String type) {
//        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("".equals(responseText)){
                    Toast.makeText(Hw_pon_down_view.this, "未查询到数据", Toast.LENGTH_SHORT).show();
                }
                else {
                    if ("county".equals(type)) {
                        Log.d("aaaaaaa", responseText);
                        /**使用gson解析**/
                        parseJSONWithGSON1(responseText);
                        result = true;
                    }
                }
                if (result) {
                    Log.d("bbbbbb", "判断是否下载完毕");
                    Hw_pon_down_view.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            closeProgressDialog();
//                            Adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("SHIBAI","网络加载失败");
                // 通过runOnUiThread()方法回到主线程处理逻辑
                Hw_pon_down_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        closeProgressDialog();
                        Toast.makeText(Hw_pon_down_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON1(String jsonData) {
        message_all="";
        try{
            JSONArray jsonArray =new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String OLTPORT_ID=jsonObject.getString("OLTPORT_ID");
                String FGFW=jsonObject.getString("FGFW");
                String SL=jsonObject.getString("SL");
                message_all=message_all+FGFW+";";
                Log.d("message_all",message_all);
            }
            upmessage(message_all);
        }catch (Exception e){
            e.printStackTrace();
        }

//        Gson gson = new Gson();
//        /**County类一定注意大写，因为传过来的是大写**/
//        List<Olt> leibiao=gson.fromJson(jsonData,new TypeToken<List<Olt>>(){}.getType());
//        mDatas.clear();
//        for (Olt olt:leibiao){
////            Log.d("MainActivity", "COUNTY_ID is " + county.getCOUNTY_ID());
//            olt = new Olt(olt.getUSER_LABEL(),olt.getEQUIP_ROOM(),olt.getDEV_IPADDR(),olt.getVENDOR(),olt.getOLT_ID());
////            mDatas.add(olt);
//        }
//        Pon_search_view.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                oltAdapter.notifyDataSetChanged();
//            }
//        });

    }



    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.pon_down_view);
        backbtn=(Button) findViewById(R.id.back_button);
        message_btn=(Button) findViewById(R.id.message_btn);
        title=(TextView) findViewById(R.id.title_text);
        oltmc=(TextView) findViewById(R.id.olt_mc);
        message=(TextView) findViewById(R.id.message);
        message_title=(TextView) findViewById(R.id.message_title);
    }
    //方法；初始化Data
    private void initData() {
        mDatas = new ArrayList<Pon_down>();
        Thread loginRunnable = new Thread() {
            @Override
            public void run() {
                showProgressDialog("正在登陆设备查询，请稍后...");
                initSocket(olt_ip, olt_cj,pon_name);
            }
        };
        loginRunnable.start();
        //
        Log.d("MYKEY",MyKey.key());

        //为数据绑定适配器
        pon_down_adapter = new Pon_down_Adapter(this, mDatas);
        listView.setAdapter(pon_down_adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //传值打印
////                Ont_Down ont_down =mDatas.get(position);
////                Log.d("dianji",ont_down.getFLOWID());
////                Intent intent = new Intent(Autofind_view.this,Dkm_view.class);
////                intent.putExtra("flow_id",ont_down.getFLOWID());
////                intent.putExtra("title","ONT下线查询");
////                startActivity(intent);
//            }
//        });

    }

    private void initSocket(String ip,String cj,String pon) {
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
//            Log.d("连接获取加密",content);
            JSONObject jsonObject = new JSONObject(content);
            int key= jsonObject.getInt("key");
            Log.d("第一次",content);
            if (content!=null){
                //发送验证信息
                int jiami=ny+key;
                JSONObject fanhui = new JSONObject();
                fanhui.put("askey", jiami);
                fanhui.put("action", "ont_down");
                fanhui.put("ip", olt_ip);
                fanhui.put("cj", olt_cj);
                fanhui.put("pon", pon_name);
                fanhui.put("zh", number);
                fanhui.put("olt_name", olt_name);
                mWriter.write(fanhui.toString(1).getBytes());
                String content1 = mReader.readLine();
                Log.d("第二次",content1);
                if (content1.equals("[OLT无法访问]")) {
                    closeProgressDialog("OLT无法访问，请稍后再试");
                    showToast("OLT无法访问，请稍后再试");
                }else if(content1.equals("[]")){
                    closeProgressDialog("无数据");
                    showToast("无数据");
                }else {

                    parseJSONWithGSON(content1); //。。。。。
                }
//                解析
//                JSONObject jsonObject_ok = new JSONObject(content1);
//                String Pon_number= jsonObject_ok.getString("content");
//                String Pon_kou= jsonObject_ok.getString("number");
//                if (content2.equals("scuess")){
//                    closeProgressDialog("设备查询完毕，正在获取返回数据");
//                    queryFromServer(server_url+"?type=531_hw_pon_down&key="+MyKey.key()+"&id="+number , "county");
//                    Log.d("数据获取网址:",server_url+"?type=531_hw_pon_down&key="+MyKey.key()+"&id="+number);
//                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        Log.d("jsonData",jsonData);
        mDatas.clear();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Pon_down> leibiao=gson.fromJson(jsonData,new TypeToken<List<Pon_down>>(){}.getType());
        for (Pon_down pon_down:leibiao){
            Log.d("Hw_pon_down_view", "getPon_kou is " + pon_down.getPon());
            Log.d("Hw_pon_down_view", "getStatus is " + pon_down.getPort_state());
            pon_down = new Pon_down(pon_down.getPort_state(),
                    pon_down.getLast_up_time(),pon_down.getLast_down_time(),pon_down.getOlt_name(),
                    pon_down.getPon(),pon_down.getOptical_Module_status());
            mDatas.add(pon_down);
        }
        Log.d("mdatas",mDatas.toString());
        Hw_pon_down_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pon_down_adapter.notifyDataSetChanged();
                message.setVisibility(View.VISIBLE);
                message_title.setVisibility(View.VISIBLE);
                message_btn.setVisibility(View.VISIBLE);
                closeProgressDialog("设备查询完毕，正在获取返回数据");
//                againbtn.setVisibility(View.VISIBLE);
            }
        });

    }

    public void showProgressDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressDialog ==null)
                {
                    progressDialog = new ProgressDialog(Hw_pon_down_view.this);
                    progressDialog.setMessage(msg);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
                progressDialog.show();
            }
        });
    }

    public void upmessage(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("msg",msg);
                message.setText(msg);

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
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Hw_pon_down_view.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}