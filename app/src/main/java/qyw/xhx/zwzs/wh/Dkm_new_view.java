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
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.HttpUtil;
import qyw.xhx.zwzs.util.Md5Utils;
//import qyw.xhx.zwzs.zy.Zone_view;


public class Dkm_new_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private List<Dkm_new> mDatas;
    private Dkm_new_Adapter dkm_new_adapter;
    private Button backbtn;
    private Button dkmbutton;
    private EditText dkmeditText;
    private String key;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dkm_layout_new);
//        //获取上一页传过来的值
//        Intent intent =getIntent();
//        id=intent.getStringExtra("hold_pos_id");
        initView();
//        initData();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dkmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id =dkmeditText.getText().toString();
                if (id.equals("")){
                    Toast.makeText(Dkm_new_view.this, "输入数据为空", Toast.LENGTH_SHORT).show();
                }else{
                    initData(id);
                    listView.setVisibility(View.VISIBLE);
                    Toast.makeText(Dkm_new_view.this, "有输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        backbtn=(Button) findViewById(R.id.back_button);
        dkmbutton=(Button) findViewById(R.id.dkm_search);
        dkmeditText=(EditText) findViewById(R.id.dkm);
    }
    //方法；初始化Data
    private void initData(String id) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        key= Md5Utils.md5("khsl"+format.format(new Date()));
        mDatas = new ArrayList<Dkm_new>();
        queryFromServer("https://ai.iorai.com/webservice/newjk.ashx?type=dkm_1&id="+id+"&key="+key, "county");
        //为数据绑定适配器
        dkm_new_adapter = new Dkm_new_Adapter(this, mDatas);
        listView.setAdapter(dkm_new_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dkm_new dkm_new =mDatas.get(position);
                //跳转Dkm_view.class
                Intent intent = new Intent(Dkm_new_view.this,Dkm_view.class);
                intent.putExtra("flow_id",dkm_new.getFLOWID());
                startActivity(intent);
                Toast.makeText(Dkm_new_view.this,dkm_new.getFLOWID(),Toast.LENGTH_SHORT).show();
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
                if (!"".equals(responseText)) {
//                    if ("county".equals(type)) {
                    Log.d("aaaaaaa",responseText);
                    /**使用gson解析**/
                    parseJSONWithGSON(responseText);
                    result =true;
                }
                if (result) {
                    Log.d("bbbbbb","判断是否下载完毕");
                    Dkm_new_view.this.runOnUiThread(new Runnable() {
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
                Dkm_new_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Dkm_new_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Dkm_new> leibiao=gson.fromJson(jsonData,new TypeToken<List<Dkm_new>>(){}.getType());
        mDatas.clear();
        for (Dkm_new dkm_new:leibiao){
//            Log.d("MainActivity", "COUNTY_ID is " + county.getCOUNTY_ID());
            dkm_new = new Dkm_new(dkm_new.getFLOWID(),dkm_new.getFLOW_NAME(),dkm_new.getBAND_ACCOUNT(),
                    dkm_new.getFORM_STATE(),dkm_new.getEND_TIME(),dkm_new.getZONE_NAME());
//            coverpos1= ("端口","宽带账号","状态","端口状态时间");
            mDatas.add(dkm_new);
        }
        Dkm_new_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dkm_new_adapter.notifyDataSetChanged();
            }
        });

    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Dkm_new_view.this);
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