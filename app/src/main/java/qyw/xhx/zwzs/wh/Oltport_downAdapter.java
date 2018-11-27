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
public class Oltport_downAdapter extends ListViewAdapter<Oltport> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public Oltport_downAdapter(Context context, List<Oltport> datas) {
        super(context, datas, R.layout.oltport_item);
    }
    @Override
    public void convert(ViewHolder holder, Oltport oltport) {
        ((TextView) holder.getView(R.id.zh_label)).setText(oltport.getUSER_LABEL());
        ((TextView) holder.getView(R.id.port_type)).setText(oltport.getPORT_TYPE());
        ((TextView) holder.getView(R.id.fgqsl)).setText(oltport.getFGQSL());
    }
}