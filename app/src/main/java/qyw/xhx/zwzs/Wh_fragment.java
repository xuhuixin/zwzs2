package qyw.xhx.zwzs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import qyw.xhx.zwzs.R;

public class Wh_fragment extends Fragment {
    //定义图标数组
    private int[] imageRes = {
            R.drawable.icon_pon,
            R.drawable.icon_dkm,
            R.drawable.icon_jqm,
};

    //定义图标下方的名称数组
    private String[] name = {
            "pon口查询",
            "端口满查询",
            "鉴权码查询"
    };
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wh_layout_main, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mContext = getActivity();

//        GridView gridview = (GridView) findViewById(R.id.gridview);
        GridView gridview = (GridView) getActivity().findViewById(R.id.whgridview);

        int length = imageRes.length;

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", imageRes[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),
                lstImageItem,//数据来源
                R.layout.wh_layout,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.img_shoukuan, R.id.txt_shoukuan});
        //添加并且显示
        gridview.setAdapter(saImageItems);
        //添加消息处理
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        /**打开区县class，intent 需在androidMainfest.xml中注册**/
                        Intent intent =new Intent("qyw.xhx.zwzs.ACTION_PON");
                        startActivity(intent);

//                        Toast.makeText(mContext, name[position], Toast.LENGTH_LONG).show();
//                Log.d("dianji","denglu");
                        break;
                    case 1:
//                        Toast.makeText(mContext, name[position], Toast.LENGTH_LONG).show();
//                Log.d("dianji","denglu");
                        break;
                    case 2:
//                        Toast.makeText(mContext, name[position], Toast.LENGTH_LONG).show();
//                Log.d("dianji","denglu");
                        break;

                }
            }
        });
    }

}