package qyw.xhx.zwzs.wh;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import qyw.xhx.zwzs.ListViewAdapter;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.ViewHolder;

/**
 * Created by xhx on 2018/9/27.
 */
public class HouseAdapter extends ListViewAdapter<House> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public HouseAdapter(Context context, List<House> datas) {
        super(context, datas, R.layout.house_item);
    }
    @Override
    public void convert(ViewHolder holder, House house) {
        ((TextView) holder.getView(R.id.full_addr)).setText(house.getFULL_ADDR());
        ((TextView) holder.getView(R.id.sfzy)).setText(house.getSFZY());

    }
}