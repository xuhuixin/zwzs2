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
public class DkmAdapter extends ListViewAdapter<Dkm> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public DkmAdapter(Context context, List<Dkm> datas) {
        super(context, datas, R.layout.dkm_item);
    }
    @Override
    public void convert(ViewHolder holder, Dkm dkm) {
        ((TextView) holder.getView(R.id.zh_label)).setText(dkm.getZH_LABEL());
        ((TextView) holder.getView(R.id.cover_type)).setText(dkm.getCOVER_TYPE());
        ((TextView) holder.getView(R.id.cover_device)).setText(dkm.getCOVER_DEVICE());
        ((TextView) holder.getView(R.id.cover_port)).setText(dkm.getCOVER_PORT());
        ((TextView) holder.getView(R.id.flow_title)).setText(dkm.getFLOW_TITLE());
        ((TextView) holder.getView(R.id.end_time)).setText(dkm.getEND_TIME());
        ((TextView) holder.getView(R.id.ahthor_value)).setText(dkm.getAHTHOR_VALUE());
        ((TextView) holder.getView(R.id.grid_name)).setText(dkm.getGRID_NAME());
        ((TextView) holder.getView(R.id.full_addr)).setText(dkm.getFULL_ADDR());

    }
}