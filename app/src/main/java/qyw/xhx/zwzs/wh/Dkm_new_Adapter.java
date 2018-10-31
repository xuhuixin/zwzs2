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
public class Dkm_new_Adapter extends ListViewAdapter<Dkm_new> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public Dkm_new_Adapter(Context context, List<Dkm_new> datas) {
        super(context, datas, R.layout.dkm_item_new);
    }
    @Override
    public void convert(ViewHolder holder, Dkm_new dkm_new) {
        ((TextView) holder.getView(R.id.flow_name)).setText(dkm_new.getFLOW_NAME());
        ((TextView) holder.getView(R.id.form_state)).setText(dkm_new.getFORM_STATE());
        ((TextView) holder.getView(R.id.end_time)).setText(dkm_new.getEND_TIME());
        ((TextView) holder.getView(R.id.zone_name)).setText(dkm_new.getZONE_NAME());
        ((TextView) holder.getView(R.id.band_account)).setText(dkm_new.getBAND_ACCOUNT());
    }
}