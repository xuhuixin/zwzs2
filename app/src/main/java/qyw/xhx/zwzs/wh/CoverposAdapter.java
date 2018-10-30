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
public class CoverposAdapter extends ListViewAdapter<Coverpos> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public CoverposAdapter(Context context, List<Coverpos> datas) {
        super(context, datas, R.layout.duankou_item);
    }
    @Override
    public void convert(ViewHolder holder, Coverpos coverpos) {
        ((TextView) holder.getView(R.id.duankou)).setText(coverpos.getDUANKOU());
        ((TextView) holder.getView(R.id.zh_label)).setText(coverpos.getZH_LABEL());
        ((TextView) holder.getView(R.id.finish_state)).setText(coverpos.getFINISH_STATE());
        ((TextView) holder.getView(R.id.time_stamp)).setText(coverpos.getTIME_STAMP());
    }
}