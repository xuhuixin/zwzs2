package qyw.xhx.zwzs.wh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import qyw.xhx.zwzs.wh.Coverpos;
import qyw.xhx.zwzs.wh.CoverposAdapter;
//import qyw.xhx.zwzs.zy.Zone_view;


public class Cover_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private List<Coverpos> mDatas;
    private CoverposAdapter coverposAdapter;
    private Button backbtn;

    private String key;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duankou_layout);
        //获取上一页传过来的值
        Intent intent =getIntent();
        id=intent.getStringExtra("hold_pos_id");

        initView();
        initData();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        backbtn=(Button) findViewById(R.id.back_button);
    }
    //方法；初始化Data
    private void initData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        key= Md5Utils.md5("khsl"+format.format(new Date()));

        mDatas = new ArrayList<Coverpos>();
        queryFromServer("https://ai.iorai.com/webservice/newjk.ashx?type=cover_pos&id="+id+"&key="+key, "county");
        //将数据装到集合中去
//        County county = new County("天桥","小区数", "楼数", "房号数", "分光器数");
//        mDatas.add(county);id
        //为数据绑定适配器
        coverposAdapter = new CoverposAdapter(this, mDatas);
        listView.setAdapter(coverposAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coverpos coverpos =mDatas.get(position);
                //跳转Zone_view.class
//                Intent intent = new Intent(Cover_view.this,Cover_view.class);
//                intent.putExtra("county_id",county.getCOUNTY_ID());
//                startActivity(intent);
//                Toast.makeText(Cover_view.this,county.getCOUNTY_ID(),Toast.LENGTH_SHORT).show();
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
                    Cover_view.this.runOnUiThread(new Runnable() {
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
                Cover_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Cover_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Coverpos> leibiao=gson.fromJson(jsonData,new TypeToken<List<Coverpos>>(){}.getType());
        mDatas.clear();
        for (Coverpos coverpos:leibiao){
//            Log.d("MainActivity", "COUNTY_ID is " + county.getCOUNTY_ID());
            coverpos = new Coverpos(coverpos.getDUANKOU(),coverpos.getZH_LABEL(),coverpos.getFINISH_STATE(),coverpos.getTIME_STAMP());
//            coverpos1= ("端口","宽带账号","状态","端口状态时间");
            mDatas.add(coverpos);
        }
        Cover_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                coverposAdapter.notifyDataSetChanged();
            }
        });

    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Cover_view.this);
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