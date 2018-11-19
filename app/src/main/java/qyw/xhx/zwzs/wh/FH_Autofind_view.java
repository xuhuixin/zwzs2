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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import qyw.xhx.zwzs.MyApplication;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.DateUtil;
import qyw.xhx.zwzs.util.MessageTransmit;
import qyw.xhx.zwzs.util.MyKey;

public class FH_Autofind_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private Button backbtn;
    private Button againbtn;
    private TextView title;
    private TextView oltmc;
    private List<FH_Autofind> mDatas;
    private FH_Autofind_Adapter fh_autofind_adapter;
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
        setContentView(R.layout.fh_pon_autofind_layout);
        //接收传值

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
        mDatas = new ArrayList<FH_Autofind>();
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
        fh_autofind_adapter = new FH_Autofind_Adapter(this, mDatas);
        listView.setAdapter(fh_autofind_adapter);
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
                parseJSONWithGSON(content1); //。。。。。
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<FH_Autofind> leibiao=gson.fromJson(jsonData,new TypeToken<List<FH_Autofind>>(){}.getType());
        mDatas.clear();
        for (FH_Autofind fh_autofind:leibiao){
            Log.d("fh_autofind_view", "Pon_number is " + fh_autofind.getSLOT());
            fh_autofind = new FH_Autofind(fh_autofind.getSLOT(),
                    fh_autofind.getPON(),fh_autofind.getPHYID());
            mDatas.add(fh_autofind);
        }
        FH_Autofind_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fh_autofind_adapter.notifyDataSetChanged();
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
                    progressDialog = new ProgressDialog(FH_Autofind_view.this);
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
                Toast.makeText(FH_Autofind_view.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}