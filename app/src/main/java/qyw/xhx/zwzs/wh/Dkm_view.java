package qyw.xhx.zwzs.wh;

import android.app.ProgressDialog;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.DateUtil;
import qyw.xhx.zwzs.util.HttpUtil;
import qyw.xhx.zwzs.util.Md5Utils;
import qyw.xhx.zwzs.util.MessageTransmit;

public class Dkm_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private Button dkmbutton;
    private Button backbtn;
    private TextView fgqchaxun;
    private List<Dkm> mDatas;
    private DkmAdapter dkmAdapter;
    private EditText dkmeditText;

    private Handler handler=null;

    private String url;
    private String key;
    private String message;
    private String id;

    private Socket mSocket;
    private BufferedReader mReader = null;
    private OutputStream mWriter = null;
    private MessageTransmit mTransmit;

    private static final String SOCKET_IP = "122.80.61.118";
    private static final int SOCKET_PORT = 9051;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dkm_layout);
        //创建属于主线程的handler
        handler=new Handler();
        initView();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//
        dkmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id =dkmeditText.getText().toString();
                if (id.equals("")){
                    Toast.makeText(Dkm_view.this, "输入数据为空", Toast.LENGTH_SHORT).show();
                }else{
                    initData(id);
//                    fgqchaxun.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    Toast.makeText(Dkm_view.this, "有输入", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.fgq_list_view);
        dkmbutton=(Button) findViewById(R.id.dkm_search);
        dkmeditText=(EditText) findViewById(R.id.dkm);
        fgqchaxun=(TextView) findViewById(R.id.fgq_chaxun);
        fgqchaxun.setMovementMethod(ScrollingMovementMethod.getInstance());
        backbtn=(Button) findViewById(R.id.back_button);

    }
    //方法；初始化Data
    private void initData(String id) {
        mDatas = new ArrayList<Dkm>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        Log.e("msg", t);
        key=Md5Utils.md5("khsl"+format.format(new Date()));
        Log.d("key=",key);
        Log.d("输入",dkmeditText.getText().toString());
        url="https://ai.iorai.com/webservice/newjk.ashx?type=khsl_new&id="+dkmeditText.getText().toString()+"&key="+key;
        queryFromServer(url,"county");
        //将数据装到集合中去
//        County county = new County("天桥","小区数", "楼数", "房号数", "分光器数");
//        mDatas.add(county);
        //为数据绑定适配器
        dkmAdapter = new DkmAdapter(this,mDatas);
        listView.setAdapter(dkmAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Olt olt =mDatas.get(position);
////                Log.d("IP",olt.getDEV_IPADDR());
////                Log.d("厂家",olt.getVENDOR());
//                olt_mc=olt.getUSER_LABEL();
//                olt_ip=olt.getDEV_IPADDR();
//                olt_cj=olt.getVENDOR();

                //跳转Zone_view.class
//                Intent intent = new Intent(Pon_view.this,Zone_view.class);
//                intent.putExtra("county_id",county.getCOUNTY_ID());
//                startActivity(intent);
                //需要查询等待showProgressDialog
//                将listview显示出来，同时listview 设为gone
//                ponchaxun.setVisibility(View.VISIBLE);
//                ponchaxun.setText("正在查询，请稍后....");
//                listView.setVisibility(View.INVISIBLE);
//                需要在线程中
//                Thread loginRunnable = new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        initSocket(olt_ip,olt_cj);
//                    }
//                };
//                loginRunnable.start();
//
//                Toast.makeText(Dkm_view.this,olt.getDEV_IPADDR(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void initSocket(String olt_ip,String olt_cj) {
        mSocket = new Socket();
        String nowyear=DateUtil.getNowYear();
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
            JSONObject jsonObject = new JSONObject(content);
            int key= jsonObject.getInt("key");
//            Log.d("key",Integer.toString(key));
            Log.d("第一次",content);
            if (content!=null){
                //发送验证信息
                int jiami=ny+key;
                JSONObject fanhui = new JSONObject();
                fanhui.put("askey", jiami);
                fanhui.put("action", "olt");
                fanhui.put("ip", olt_ip);
                fanhui.put("cj", olt_cj);
                System.out.println(fanhui.toString(1));
                mWriter.write(fanhui.toString(1).getBytes());
                String content1 = mReader.readLine();
                Log.d("第二次",content1);
//                解析
                JSONObject jsonObject_ok = new JSONObject(content1);
                String result= jsonObject_ok.getString("result");
                message= jsonObject_ok.getString("message");
//                String number= jsonObject_ok.getString("number");
                Log.d("result",result);
                Log.d("message",message);
//                Log.d("number",number);
                if (result.equals("success")){
                    message=message.replace(",","\n\n");
//                    message="OLT名称:"+olt_mc+"\n\n"+message;

                    handler.post(runnableUi);
                }
//                if (content2.equals("NPASS")){
//                    showToast("用户名或者密码错误");
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 构建Runnable对象，在runnable中更新界面
    Runnable runnableUi=new Runnable() {
        @Override
        public void run() {
            //更新界面
//            ponchaxun.setText(message);
        }
    };


    //根据传入的地址从服务器查询数据
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
             @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.d("bbbbbbbbbbbb",responseText);

                boolean result = false;
                if ("[]".equals(responseText)){
                    Dkm_view.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            Toast.makeText(Dkm_view.this, "未查询到数据", Toast.LENGTH_SHORT).show();
//                            Adapter.notifyDataSetChanged();
                        }
                    });

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
                     Dkm_view.this.runOnUiThread(new Runnable() {
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
                Dkm_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Dkm_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Dkm> leibiao=gson.fromJson(jsonData,new TypeToken<List<Dkm>>(){}.getType());
        mDatas.clear();
        for (Dkm dkm:leibiao){
            Log.d("MainActivity", "COUNTY_ID is " + dkm.getCOVER_DEVICE());

            if ("0".equals(dkm.getCOVER_DEVICE())){
                Log.d("aaaaaaaaaaa","aaaaaaaaaaaaaaaaa");
                Log.d("key1111111",key);
                url="https://ai.iorai.com/webservice/newjk.ashx?type=khsl_his&id="+dkmeditText.getText().toString()+"&key="+key;
                queryFromServer(url,"county");
//                initData(id);
            }
            dkm = new Dkm(dkm.getZH_LABEL(),dkm.getFULL_ADDR(),dkm.getFLOWID(),
                    dkm.getCOVER_DEVICE(),dkm.getCOVER_PORT(),dkm.getGRID_NAME(),
                    dkm.getCOVER_TYPE(),dkm.getEND_TIME(),dkm.getFLOW_TITLE(),
                    dkm.getHOLD_POS_ID(),dkm.getAHTHOR_VALUE(),dkm.getHOUSE_ID());
            mDatas.add(dkm);
        }
        Dkm_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dkmAdapter.notifyDataSetChanged();
            }
        });

    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Dkm_view.this);
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