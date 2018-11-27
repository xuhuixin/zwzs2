package qyw.xhx.zwzs.wh;
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
import qyw.xhx.zwzs.MyApplication;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.DateUtil;
import qyw.xhx.zwzs.util.HttpUtil;
import qyw.xhx.zwzs.util.MessageTransmit;
import qyw.xhx.zwzs.util.MyKey;
import qyw.xhx.zwzs.wh.Autofind;
import qyw.xhx.zwzs.wh.Autofind_Adapter;

public class Autofind_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private Button backbtn;
    private Button againbtn;
    private TextView title;
    private TextView oltmc;
    private List<Autofind> mDatas;
    private Autofind_Adapter autofind_adapter;
    private Handler handler=null;
    private String ponkou;
    private String olt_cj;
    private String olt_mc;
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
        setContentView(R.layout.pon_autofind_layout);
        //接收传值
        Log.d("aaaaaaaaaaa","华为");
        Intent intent =getIntent();
        olt_mc=intent.getStringExtra("olt_mc");
        olt_cj=intent.getStringExtra("olt_cj");
        olt_ip=intent.getStringExtra("olt_ip");
        Log.d("接收到的olt_mc",olt_mc);
        Log.d("接收到的olt_cj",olt_cj);
        Log.d("接收到的olt_ip",olt_ip);
        myApplication = (MyApplication) getApplication(); //获得自定义的应用程序YApp
        number=myApplication.getNumber();
        city=myApplication.getCity();
        city_id=myApplication.getCity_id();
        server_url=myApplication.getServer_url();

        //创建属于主线程的handler
//        handler=new Handler();
        initView();
        oltmc.setText(olt_mc+"-"+olt_ip+"-"+olt_cj);

        initData();


        title.setText("OLT光猫新发现查询");
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.auto_find_view);
        backbtn=(Button) findViewById(R.id.back_button);
//        againbtn=(Button) findViewById(R.id.again);
        title=(TextView) findViewById(R.id.title_text);
        oltmc=(TextView) findViewById(R.id.olt_mc);
    }
    //方法；初始化Data
    private void initData() {
        mDatas = new ArrayList<Autofind>();
        Thread loginRunnable = new Thread() {
            @Override
            public void run() {
                showProgressDialog("正在登陆设备查询，请稍后...");
                initSocket(olt_ip, olt_cj);
            }
        };
        loginRunnable.start();
        //
        Log.d("MYKEY",MyKey.key());

        //为数据绑定适配器
        autofind_adapter = new Autofind_Adapter(this, mDatas);
        listView.setAdapter(autofind_adapter);
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

    private void initSocket(String ip,String cj) {
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
//            Log.d("第一次",content);
            if (content!=null){
                //发送验证信息
                int jiami=ny+key;
                JSONObject fanhui = new JSONObject();
                fanhui.put("askey", jiami);
                fanhui.put("action", "olt");
                fanhui.put("ip", olt_ip);
                fanhui.put("cj", olt_cj);
                mWriter.write(fanhui.toString(1).getBytes());
                String content1 = mReader.readLine();
                Log.d("第二次",content1);
                if (content1.equals("[OLT无法访问]")) {
                    closeProgressDialog("OLT无法访问，请稍后再试");
                    showToast("OLT无法访问，请稍后再试");
                }else if(content1.equals("[]")){
                    closeProgressDialog("无新发现光猫");
                    showToast("无新发现光猫");
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
        List<Autofind> leibiao=gson.fromJson(jsonData,new TypeToken<List<Autofind>>(){}.getType());
        for (Autofind autofind:leibiao){
            Log.d("autofind_view", "Pon_number is " + autofind.getPon_number());
            autofind = new Autofind(autofind.getPon_number(),
                    autofind.getPon_kou(),autofind.getOnt_sn(),autofind.getPwd(),autofind.getAutofind_time());
            mDatas.add(autofind);
        }
        Autofind_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                autofind_adapter.notifyDataSetChanged();
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
                    progressDialog = new ProgressDialog(Autofind_view.this);
                    progressDialog.setMessage(msg);
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
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Autofind_view.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}