package qyw.xhx.zwzs.wh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
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
import qyw.xhx.zwzs.wh.zhongduan.Zh_all;
import qyw.xhx.zwzs.wh.zhongduan.Zh_data;
//import qyw.xhx.zwzs.zy.Zone_view;


public class Zdzy_new_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private List<Zh_data> mDatas;
    private Zdzy_new_Adapter zdzy_new_adapter;
    private Button backbtn;
    private Button zhbutton;
    private Button zdbutton;
    private EditText zheditText;
    private EditText zdeditText;
    private String key;
    private String id;
    private MyApplication myApplication;//初始化全局变量
    private String number;
    private String city;
    private String city_id;
    private String server_url;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zdzy_layout_new);
        myApplication = (MyApplication) getApplication(); //获得自定义的应用程序YApp
        number=myApplication.getNumber();
        city=myApplication.getCity();
        city_id=myApplication.getCity_id();
        server_url=myApplication.getServer_url();
//        //获取上一页传过来的值
//        Intent intent =getIntent();
//        id=intent.getStringExtra("hold_pos_id");
        initView();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        zhbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id =zheditText.getText().toString();
                if (id.equals("")){
                    Toast.makeText(Zdzy_new_view.this, "输入数据为空", Toast.LENGTH_SHORT).show();
                }else{
                    url="https://xcx.iorai.com/WebService/ott.ashx?token=76F9D0E5-910C-4BF7-B6FD-2D01066BD8D1&type=220&account=";
                    initData(id,url);
                    listView.setVisibility(View.VISIBLE);
                    Toast.makeText(Zdzy_new_view.this, "有输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        zdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id =zdeditText.getText().toString();
                if (id.equals("")){
                    Toast.makeText(Zdzy_new_view.this, "输入数据为空", Toast.LENGTH_SHORT).show();
                }else{
                    url="https://xcx.iorai.com/WebService/ott.ashx?token=76F9D0E5-910C-4BF7-B6FD-2D01066BD8D1&type=221&sn=";
                    initData(id,url);
                    listView.setVisibility(View.VISIBLE);
                    Toast.makeText(Zdzy_new_view.this, "有输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        backbtn=(Button) findViewById(R.id.back_button);
        zhbutton=(Button) findViewById(R.id.zh_search);
        zdbutton=(Button) findViewById(R.id.zd_search);
        zheditText=(EditText) findViewById(R.id.zh);
        zdeditText=(EditText) findViewById(R.id.zd);
    }
    //方法；初始化Data
    private void initData(String id,String url) {
        mDatas = new ArrayList<Zh_data>();
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        key= Md5Utils.md5("khsl"+format.format(new Date()));
        queryFromServer(url+id, "county");
        //为数据绑定适配器
        zdzy_new_adapter = new Zdzy_new_Adapter(this, mDatas);
        listView.setAdapter(zdzy_new_adapter);
    }
    //根据传入的地址从服务器查询数据
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        Log.d("查询地址",address);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
             @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if (!"".equals(responseText)) {
                    if ("[]".equals(responseText)){
                        Zdzy_new_view.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeProgressDialog();
                                Toast.makeText(Zdzy_new_view.this, "没有数据返回", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Log.d("aaaaaaaaaaaaaaa",responseText);
                        /**使用gson解析**/
//                        zhInfo(responseText);
                        parseJSONWithGSON(responseText);
                        result =true;
                    }
                }
                if (result) {
                    Log.d("bbbbbb","判断是否下载完毕");
                    Zdzy_new_view.this.runOnUiThread(new Runnable() {
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
                Zdzy_new_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Zdzy_new_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        Zh_all zh_all = gson.fromJson(jsonData, Zh_all.class);
        Log.d("Zdzy_new_view", "result is " + zh_all.getResult());
        if ("0".equals(zh_all.getResult())){
            //查询成功，做循环
            mDatas.clear();
//            Zh_data m = new Zh_data();
//            List<Zh_data> leibiao=gson.fromJson(jsonData,new TypeToken<List<Zh_data>>(){}.getType());
            for(int i = 0;i < zh_all.getData().size(); i ++){
                Log.d("Zdzy_new_view", "data is " + zh_all.getData().get(i).cityName);
                Log.d("Zdzy_new_view", "SN is " + zh_all.getData().get(i).sn);
                Zh_data m = new Zh_data(zh_all.getData().get(i).cityName,zh_all.getData().get(i).sn,zh_all.getData().get(i).account,
                        zh_all.getData().get(i).vendor,zh_all.getData().get(i).devType,
                        zh_all.getData().get(i).godownName,zh_all.getData().get(i).statusTrip,
                        zh_all.getData().get(i).statusUsable,zh_all.getData().get(i).inTime);
                mDatas.add(m);
            }
        }
        Zdzy_new_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                zdzy_new_adapter.notifyDataSetChanged();
            }
        });

    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Zdzy_new_view.this);
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