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
public class ZoneAdapter extends ListViewAdapter<Zone> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public ZoneAdapter(Context context, List<Zone> datas) {
        super(context, datas, R.layout.zone_item);
    }
    @Override
    public void convert(ViewHolder holder, Zone zone) {
        ((TextView) holder.getView(R.id.zonename)).setText(zone.getZONE_NAME());
//        ((TextView) holder.getView(R.id.)).setText(county.getXQS());ZONE_NAME 先不显示
        ((TextView) holder.getView(R.id.ls)).setText(zone.getLS());
        ((TextView) holder.getView(R.id.fhs)).setText(zone.getFHS());
        ((TextView) holder.getView(R.id.fgqs)).setText(zone.getFGQS());
    }
}