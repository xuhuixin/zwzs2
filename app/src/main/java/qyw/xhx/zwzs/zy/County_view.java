package qyw.xhx.zwzs.zy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class County_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private List<County> mDatas;
    private CountyAdapter countyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.county_layout);
        initView();
        initData();
    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
    }
    //方法；初始化Data
    private void initData() {
        mDatas = new ArrayList<County>();
        queryFromServer("https://ai.iorai.com/webservice/newjk.ashx?type=zy_county", "county");
        //将数据装到集合中去
//        County county = new County("天桥","小区数", "楼数", "房号数", "分光器数");
//        mDatas.add(county);
        //为数据绑定适配器
        countyAdapter = new CountyAdapter(this, mDatas);
        listView.setAdapter(countyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                County county =mDatas.get(position);
                //跳转Zone_view.class
                Intent intent = new Intent(County_view.this,Zone_view.class);
                intent.putExtra("county_id",county.getCOUNTY_ID());
                startActivity(intent);
                Toast.makeText(County_view.this,county.getCOUNTY_ID(),Toast.LENGTH_SHORT).show();
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
                if ("county".equals(type)) {
                    Log.d("aaaaaaa",responseText);
                    /**使用gson解析**/
                    parseJSONWithGSON(responseText);
                    result =true;
                }
                if (result) {
                    Log.d("bbbbbb","判断是否下载完毕");
                    County_view.this.runOnUiThread(new Runnable() {
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
                County_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(County_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<County> leibiao=gson.fromJson(jsonData,new TypeToken<List<County>>(){}.getType());
        mDatas.clear();
        for (County county:leibiao){
//            Log.d("MainActivity", "COUNTY_ID is " + county.getCOUNTY_ID());
            county = new County(county.getCOUNTY_ID(),county.getCOUNTY_NAME(),"小区数:"+county.getXQS(),"楼数:"+county.getLS(),
                    "房号:"+county.getFHS(),"分光器:"+county.getFGQS());
            mDatas.add(county);
        }
        County_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                countyAdapter.notifyDataSetChanged();
            }
        });

    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(County_view.this);
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