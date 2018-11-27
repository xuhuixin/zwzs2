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
public class Pon_down_Adapter extends ListViewAdapter<Pon_down> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public Pon_down_Adapter(Context context, List<Pon_down> datas) {
        super(context, datas, R.layout.pon_down_item_new);
    }
    @Override
    public void convert(ViewHolder holder, Pon_down pon_down) {
        ((TextView) holder.getView(R.id.olt_mc)).setText(pon_down.getOlt_name());
        ((TextView) holder.getView(R.id.pon)).setText(pon_down.getPon());
        ((TextView) holder.getView(R.id.port_state)).setText(pon_down.getPort_state());
        ((TextView) holder.getView(R.id.last_up_time)).setText(pon_down.getLast_up_time());
        ((TextView) holder.getView(R.id.last_down_time)).setText(pon_down.getLast_down_time());
        ((TextView) holder.getView(R.id.Optical_Module_status)).setText(pon_down.getOptical_Module_status());
    }
}