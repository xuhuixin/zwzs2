package qyw.xhx.zwzs.wh;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
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
import java.util.HashMap;
import java.util.List;

import Jack.WewinPrinterHelper.AsyncProgress;
import Jack.WewinPrinterHelper.Print;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import qyw.xhx.zwzs.CommonScanActivity;
import qyw.xhx.zwzs.MyApplication;
import qyw.xhx.zwzs.MyBaseAdapter;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.util.DateUtil;
import qyw.xhx.zwzs.util.HttpUtil;
import qyw.xhx.zwzs.util.Md5Utils;
import qyw.xhx.zwzs.util.MessageTransmit;
import qyw.xhx.zwzs.utils.Constant;
import qyw.xhx.zwzs.widget.LoadingDialog;
import qyw.xhx.zwzs.zy.County_view;
import qyw.xhx.zwzs.zy.Zone_view;
import qyw.xhx.zwzs.util.MyKey;
import static android.media.CamcorderProfile.get;

public class Dkm_view extends AppCompatActivity {
    private ProgressDialog progressDialog;
    //    private ListView listView;
    private Button dkmbutton;
    private Button backbtn;
    private Button saomiaobtn;
    private TextView fgqchaxun;
    private List<Dkm> mDatas;
    private DkmAdapter dkmAdapter;
    private EditText dkmeditText;
    private ListView mListView;
    private TextView title_text;

    private Handler handler=null;

    private String url;
    private String key;
    private String pwd="";
    private String message;
    private String id;
    private String title;
    private String city_id;
    private ArrayList dkmArrayList;
    private HashMap dkmMap;
    private Print p = null;
    private String xml = "";
    private String xmlStr1;
    private ImageView imageView1;
    public boolean flag = true;

    private String number;
    private String city;
    private String server_url;
    private MyApplication myApplication;//初始化全局变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dkm_layout);
        p = new Print(Dkm_view.this);
        myApplication = (MyApplication) getApplication(); //获得自定义的应用程序YApp
        number=myApplication.getNumber();
        city=myApplication.getCity();
        city_id=myApplication.getCity_id();
        server_url=myApplication.getServer_url();
        //接收传值
        Intent intent =getIntent();
        id=intent.getStringExtra("flow_id");
        title=intent.getStringExtra("title");
        //创建属于主线程的handler
        handler=new Handler();
        dkmArrayList = new ArrayList();
        initView();
        initData(id);
        title_text.setText(title);
        if(dkmAdapter==null){
            dkmAdapter = new DkmAdapter(this,dkmArrayList);
        }else {
            //刷新适配器,不用每次都new SongAdapter(this,songArrayList)
            dkmAdapter.notifyDataSetChanged();
        }
        mListView.setAdapter(dkmAdapter);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //方法：初始化View
    private void initView() {
        mListView = (ListView) findViewById(R.id.fgq_list_view);
        saomiaobtn=(Button) findViewById(R.id.dkm_saomiao);
        dkmbutton=(Button) findViewById(R.id.dkm_search);
        dkmeditText=(EditText) findViewById(R.id.dkm);
        title_text=(TextView) findViewById(R.id.title_text);
//        fgqchaxun=(TextView) findViewById(R.id.fgq_chaxun);
//        fgqchaxun.setMovementMethod(ScrollingMovementMethod.getInstance());
        backbtn=(Button) findViewById(R.id.back_button);

    }
    //方法；初始化Data
    private void initData(String id) {
//        mDatas = new ArrayList<Dkm>();
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        key=Md5Utils.md5("khsl"+format.format(new Date()));
////        pwd=dkmeditText.getText().toString();
//        Log.d("MYKEY",MyKey.key());
        url=server_url+"?type=dkm_sheng2&id="+id+"&key="+MyKey.key()+"&city_id="+city_id;
        queryFromServer(url,"county");
    }



    class DkmAdapter extends MyBaseAdapter<ArrayList> {
        private Context context;
        DkmAdapter(Context c,ArrayList arrayList){
            super(arrayList);
            context=c;
        }
        //重用了convertView，很大程度上的减少了内存的消耗。通过判断convertView是否为null，
        // 是的话就需要产生一个视图出来，然后给这个视图数据，最后将这个视图返回给底层，呈献给用户。
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView =inflater.inflate(R.layout.dkm_item,null);
                viewHolder = new ViewHolder();
//                viewHolder.singerImageView =(ImageView)convertView.findViewById(R.id.singerImageView);
                viewHolder.zh_label =(TextView)convertView.findViewById(R.id.zh_label);
                viewHolder.cover_type =(TextView)convertView.findViewById(R.id.cover_type);
                viewHolder.cover_device =(TextView)convertView.findViewById(R.id.cover_device);
                viewHolder.fgqid =(ImageView) convertView.findViewById(R.id.fgqid);
                viewHolder.cover_port =(TextView) convertView.findViewById(R.id.cover_port);
                viewHolder.flow_title =(TextView) convertView.findViewById(R.id.flow_title);
                viewHolder.end_time =(TextView) convertView.findViewById(R.id.end_time);
                viewHolder.ahthor_value =(TextView) convertView.findViewById(R.id.ahthor_value);
                viewHolder.olt_name =(TextView) convertView.findViewById(R.id.olt_name);
                viewHolder.olt_pon =(TextView) convertView.findViewById(R.id.olt_pon);
                viewHolder.grid_name =(TextView) convertView.findViewById(R.id.grid_name);
                viewHolder.full_addr =(TextView) convertView.findViewById(R.id.full_addr);
                viewHolder.btn1 =(RadioButton) convertView.findViewById(R.id.btn1);
                viewHolder.btn2 =(RadioButton) convertView.findViewById(R.id.btn2);
                viewHolder.btnlx1 =(RadioButton) convertView.findViewById(R.id.btnlx1);
                viewHolder.btnlx2 =(RadioButton) convertView.findViewById(R.id.btnlx2);
                viewHolder.imageView1 =(ImageView) convertView.findViewById(R.id.imageView1);
                convertView.setTag(viewHolder);
                viewHolder.fgqid.setTag(position);
                viewHolder.fgqid.setTag(position);
                viewHolder.btn1.setTag(position);
                viewHolder.btn2.setTag(position);
                viewHolder.btnlx1.setTag(position);
                viewHolder.btnlx2.setTag(position);
            }else {
                viewHolder =(ViewHolder)convertView.getTag();
            }
            viewHolder.zh_label.setText((String)((HashMap)dkmArrayList.get(position)).get("zh_label"));
            viewHolder.cover_type.setText((String)((HashMap)dkmArrayList.get(position)).get("cover_type"));
            viewHolder.cover_device.setText((String)((HashMap)dkmArrayList.get(position)).get("cover_device"));
            viewHolder.cover_port.setText((String)((HashMap)dkmArrayList.get(position)).get("cover_port"));
            viewHolder.flow_title.setText((String)((HashMap)dkmArrayList.get(position)).get("flow_title"));
            viewHolder.end_time.setText((String)((HashMap)dkmArrayList.get(position)).get("end_time"));
            viewHolder.ahthor_value.setText((String)((HashMap)dkmArrayList.get(position)).get("ahthor_value"));
            viewHolder.olt_name.setText((String)((HashMap)dkmArrayList.get(position)).get("olt_name"));
            viewHolder.olt_pon.setText((String)((HashMap)dkmArrayList.get(position)).get("olt_pon"));
            viewHolder.grid_name.setText((String)((HashMap)dkmArrayList.get(position)).get("grid_name"));
            viewHolder.full_addr.setText((String)((HashMap)dkmArrayList.get(position)).get("full_addr"));
            if (((HashMap)dkmArrayList.get(position)).get("hold_pos_id").equals("0")){
                Log.d("dkm_view","hold_pos_id is null");
                viewHolder.fgqid.setVisibility(View.INVISIBLE);
                viewHolder.btn1.setVisibility(View.INVISIBLE);
                viewHolder.btn2.setVisibility(View.INVISIBLE);
                viewHolder.btnlx1.setVisibility(View.INVISIBLE);
                viewHolder.btnlx2.setVisibility(View.INVISIBLE);
            }else{
            viewHolder.fgqid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int downLoadPosition =(Integer)v.getTag();
                    String hold_pos_id =(String)((HashMap)dkmArrayList.get(downLoadPosition)).get("hold_pos_id");
//                    打印一下
                    Log.d("点击了",hold_pos_id);
                    //跳转Zone_view.class
                    Intent intent = new Intent(Dkm_view.this,Cover_view.class);
                    intent.putExtra("hold_pos_id",hold_pos_id);
                    startActivity(intent);
//                    Toast.makeText(Dkm_view.this,"正在查询，请稍后...",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int downLoadPosition =(Integer)v.getTag();
                    String house_id =(String)((HashMap)dkmArrayList.get(downLoadPosition)).get("house_id");
                    //跳转Zone_view.class
                    Toast.makeText(Dkm_view.this,"已选择了品胜",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int downLoadPosition =(Integer)v.getTag();
                    String hold_pos_id =(String)((HashMap)dkmArrayList.get(downLoadPosition)).get("hold_pos_id");
//                    打印一下
                    Log.d("点击了",hold_pos_id);

                    //跳转Zone_view.class
                    Toast.makeText(Dkm_view.this,"该厂家打印功能还在开发中...",Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.btnlx1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int downLoadPosition =(Integer)v.getTag();
                    String house_id =(String)((HashMap)dkmArrayList.get(downLoadPosition)).get("house_id");
                    String full_addr =(String)((HashMap)dkmArrayList.get(downLoadPosition)).get("full_addr");
                    xmlStr1 = "<Data><Print><row><type>02F</type><code>"+house_id+"</code><text>"+full_addr+"</text></row></Print></Data>";
                    String hold_pos_id =(String)((HashMap)dkmArrayList.get(downLoadPosition)).get("hold_pos_id");
//                    打印一下
//                    Log.d("dayin",full_addr);
//                    Log.d("dayin1",house_id);
                    Toast.makeText(Dkm_view.this,"测试",Toast.LENGTH_SHORT).show();
                    xml = xmlStr1;

                    AsyncProgress.indeterminate(Dkm_view.this, handler, "正在打印中，请稍候...",
                            new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        while (flag) {
                                            String result = p.LabelPrint(xml,
                                                    Dkm_view.this);
                                            Toast.makeText(Dkm_view.this,"正在打印分光器侧标签...",
                                                    Toast.LENGTH_SHORT).show();
                                            viewHolder.btnlx1.setBackground(getResources().getDrawable(R.drawable.radiobutton_background_unchecked));
//                    imageView1
//                            .setImageBitmap(p.getBitmapForPreview(xml, Saomiao_view.this));
                                            // key code
                                            flag = false;
                                        }
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                }
                            }, new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    flag = true;
                                }
                            });

//                    Log.d("点击了",hold_pos_id);
                    //跳转Zone_view.class

                }
            });
            viewHolder.btnlx2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int downLoadPosition =(Integer)v.getTag();
                    String hold_pos_id =(String)((HashMap)dkmArrayList.get(downLoadPosition)).get("hold_pos_id");
                    String cover_device =(String)((HashMap)dkmArrayList.get(downLoadPosition)).get("cover_device");
                    xmlStr1 = "<Data><Print><row><type>02F</type><code>"+hold_pos_id+"</code><text>"+cover_device+"</text></row></Print></Data>";

//                    打印一下
                    xml = xmlStr1;
//                    imageView1
//                            .setImageBitmap(p.getBitmapForPreview(xml, Saomiao_view.this));
                    // key code
                    AsyncProgress.indeterminate(Dkm_view.this, handler, "正在打印中，请稍候...",
                            new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        while (flag) {
                                            String result = p.LabelPrint(xml,
                                                    Dkm_view.this);
                                            Toast.makeText(Dkm_view.this,"正在打印用户侧标签...",
                                                    Toast.LENGTH_SHORT).show();
                                            flag = false;
                                        }
                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                }
                            }, new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    flag = true;
                                }
                            });
                }
            });

            }


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("点击了","view");
                }
            });
            return convertView;
        }
    }
    //避免了就是每次在getVIew的时候，都需要重新的findViewById，
    // 重新找到控件，然后进行控件的赋值以及事件相应设置。这样其实在做重复的事情)
    class ViewHolder{
        TextView zh_label;
        TextView flow_title;
        TextView cover_type;
        TextView cover_device;
        ImageView fgqid;
        TextView cover_port;
        TextView end_time;
        TextView ahthor_value;
        TextView olt_name;
        TextView olt_pon;
        TextView grid_name;
        TextView full_addr;
        ImageView imageView1;
        RadioButton btn1;
        RadioButton btn2;
        RadioButton btnlx1;
        RadioButton btnlx2;

    }
    //根据传入的地址从服务器查询数据
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("[]".equals(responseText)){
                    Dkm_view.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            Toast.makeText(Dkm_view.this, "未查询到数据", Toast.LENGTH_SHORT).show();
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
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("SHIBAI","网络加载失败");
                e.printStackTrace();

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
        dkmArrayList.clear();
        for (Dkm dkm:leibiao){
            Log.d("MainActivity", "COUNTY_ID is " + dkm.getCOVER_DEVICE());
            Log.d("MainActivity", "hold_pos_id is " + dkm.getHOLD_POS_ID());
//            如果是历史库数据重新查询
            dkmMap = new HashMap();
            dkmMap.put("zh_label",dkm.getZH_LABEL());
            dkmMap.put("full_addr",dkm.getFULL_ADDR());
            dkmMap.put("cover_device",dkm.getCOVER_DEVICE());
            dkmMap.put("cover_port",dkm.getCOVER_PORT());
            dkmMap.put("flow_title",dkm.getFLOW_TITLE());
            dkmMap.put("end_time",dkm.getEND_TIME());
            dkmMap.put("ahthor_value",dkm.getAHTHOR_VALUE());
            dkmMap.put("olt_name",dkm.getOLT_NAME());
            dkmMap.put("olt_pon",dkm.getOLT_PON());
            dkmMap.put("grid_name",dkm.getGRID_NAME());
            dkmMap.put("cover_type",dkm.getCOVER_TYPE());
            dkmMap.put("hold_pos_id",dkm.getHOLD_POS_ID());
            dkmMap.put("house_id",dkm.getHOUSE_ID());
            dkmArrayList.add(dkmMap);
//            dkm = new Dkm(dkm.getZH_LABEL(),dkm.getFULL_ADDR(),dkm.getFLOWID(),
//                    dkm.getCOVER_DEVICE(),dkm.getCOVER_PORT(),dkm.getGRID_NAME(),
//                    dkm.getCOVER_TYPE(),dkm.getEND_TIME(),dkm.getFLOW_TITLE(),
//                    dkm.getHOLD_POS_ID(),dkm.getAHTHOR_VALUE(),dkm.getHOUSE_ID());
//            mDatas.add(dkm);

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

    @Override
    protected void onPause() {
        p.bluetoothClose();
        super.onPause();
    }
}