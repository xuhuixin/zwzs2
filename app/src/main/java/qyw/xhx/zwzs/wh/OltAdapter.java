package qyw.xhx.zwzs.wh;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import qyw.xhx.zwzs.ListViewAdapter;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.ViewHolder;
import qyw.xhx.zwzs.wh.Olt;

/**
 * Created by xhx on 2018/9/27.
 */
public class OltAdapter extends ListViewAdapter<Olt> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public OltAdapter(Context context, List<Olt> datas) {
        super(context, datas, R.layout.pon_item);
    }
    @Override
    public void convert(ViewHolder holder, Olt olt) {
        ((TextView) holder.getView(R.id.olt_name)).setText(olt.getUSER_LABEL());
        ((TextView) holder.getView(R.id.olt_address)).setText(olt.getDEV_IPADDR());
        ((TextView) holder.getView(R.id.olt_cj)).setText(olt.getVENDOR());
//        ((TextView) holder.getView(R.id.fhs)).setText(county.getFHS());
//        ((TextView) holder.getView(R.id.fgqs)).setText(county.getFGQS());
    }
}