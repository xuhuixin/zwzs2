package qyw.xhx.zwzs.zy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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


public class Zone_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ListView listView;
    private List<Zone> mDatas;
    private List<Wangge> nDatas;
    private ZoneAdapter zoneAdapter;
    private String county_id;
    private String tt_wg;
    //下拉菜单
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zone_layout);
        //获取上一页传过来的值
        Intent intent =getIntent();
        county_id=intent.getStringExtra("county_id");
//        Log.d("传过来的值：",county_id);
        spinner = (Spinner) findViewById(R.id.wangge);//网格下拉
        initWangge();
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arr_adapter);
        //给Spinner添加事件监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //当选中某一个数据项时触发该方法
            /*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                String data = (String)spinner.getItemAtPosition(position);//从spinner中获取被选择的数据
                if("--请选择网格--".equals(data)){

                }else {
                    Toast.makeText(Zone_view.this, data, Toast.LENGTH_SHORT).show();
                    tt_wg = data;
                    initData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        initView();

    }
    //方法：初始化View
    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
    }
    //方法；初始化Data
    private void initData() {
        mDatas = new ArrayList<Zone>();
        queryFromServer("https://ai.iorai.com/webservice/newjk.ashx?type=zy_zone&county_id="+county_id+"&tt_wg="+tt_wg, "zone");
        //将数据装到集合中去
//        County county = new County("天桥","小区数", "楼数", "房号数", "分光器数");
//        mDatas.add(county);
        //为数据绑定适配器
        zoneAdapter = new ZoneAdapter(Zone_view.this, mDatas);
        listView.setAdapter(zoneAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Zone zone =mDatas.get(position);
                Toast.makeText(Zone_view.this,zone.getZONE_ID(),Toast.LENGTH_SHORT).show();
                //跳转Building_view.class
//                Intent intent = new Intent(Zone_view.this,Building_view.class);
//                intent.putExtra("zone_id",zone.getZONE_ID());
//                intent.putExtra("zone_name",zone.getZONE_NAME());
//                startActivity(intent);


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
//                Log.d("返回",responseText);
                boolean result = false;
                if ("zone".equals(type)) {
                    Log.d("aaaaaaa",responseText);
                    /**使用gson解析**/
                    parseJSONWithGSON(responseText);
                    result =true;
                }
                if (result) {
                    Log.d("bbbbbb","判断是否下载完毕");
                    Zone_view.this.runOnUiThread(new Runnable() {
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
                Zone_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Zone_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Zone> leibiao=gson.fromJson(jsonData,new TypeToken<List<Zone>>(){}.getType());
        mDatas.clear();
        for (Zone zone:leibiao){
//            Log.d("MainActivity", "COUNTY_ID is " + county.getCOUNTY_ID());
            zone = new Zone(zone.getZONE_ID(),zone.getZONE_NAME(),"楼数:"+zone.getLS(),
                    "房号数:"+zone.getFHS(),"分光器数:"+zone.getFGQS());
            mDatas.add(zone);
        }
        Zone_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                zoneAdapter.notifyDataSetChanged();
            }
        });

    }

    private void initWangge(){
        data_list = new ArrayList<String>();
//        data_list.add("北京");
//        data_list.add("上海");
        queryFromWangge("https://ai.iorai.com/webservice/newjk.ashx?type=zy_wangge&county_id="+county_id);
    }
    private void queryFromWangge(String address) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                if(responseText.length()>0 ){
                    Log.d("----",responseText);
                    Log.d("----","有数据返回");
                    /**使用gson解析**/
                    Zone_view.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                        }
                    });
                    wanggeJSONWithGSON(responseText);
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("WANGGESHIBAI","网络加载失败");
                // 通过runOnUiThread()方法回到主线程处理逻辑
                Zone_view.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(Zone_view.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void wanggeJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        /**County类一定注意大写，因为传过来的是大写**/
        List<Wangge> wgleibiao = gson.fromJson(jsonData, new TypeToken<List<Wangge>>() {
        }.getType());
//        nDatas.clear();
        data_list.add("--请选择网格--");
        for (Wangge wangge : wgleibiao) {
            data_list.add(wangge.getTT_WG());
        }
        Zone_view.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //更新网格列表
                arr_adapter.notifyDataSetChanged();
            }
        });
//        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
//        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(arr_adapter);
//        //给Spinner添加事件监听
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            //当选中某一个数据项时触发该方法
//            /*
//             * parent接收的是被选择的数据项所属的 Spinner对象，
//             * view参数接收的是显示被选择的数据项的TextView对象
//             * position接收的是被选择的数据项在适配器中的位置
//             * id被选择的数据项的行号
//             */
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
//                String data = (String)spinner.getItemAtPosition(position);//从spinner中获取被选择的数据
//                if("--请选择网格--".equals(data)){
//
//                }else {
//                    Toast.makeText(Zone_view.this, data, Toast.LENGTH_SHORT).show();
//                    initView();
//                    initData();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Zone_view.this);
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