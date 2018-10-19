package qyw.xhx.zwzs.zy;

import android.content.Context;
import android.widget.TextView;

import qyw.xhx.zwzs.ListViewAdapter;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.ViewHolder;

import java.util.List;

/**
 * Created by xhx on 2018/9/27.
 */
public class CountyAdapter extends ListViewAdapter<County> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public CountyAdapter(Context context, List<County> datas) {
        super(context, datas, R.layout.county_item);
    }
    @Override
    public void convert(ViewHolder holder, County county) {
        ((TextView) holder.getView(R.id.countyname)).setText(county.getCOUNTY_NAME());
        ((TextView) holder.getView(R.id.xqs)).setText(county.getXQS());
        ((TextView) holder.getView(R.id.ls)).setText(county.getLS());
        ((TextView) holder.getView(R.id.fhs)).setText(county.getFHS());
        ((TextView) holder.getView(R.id.fgqs)).setText(county.getFGQS());
    }
}