package qyw.xhx.zwzs.zy;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import qyw.xhx.zwzs.ListViewAdapter;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.ViewHolder;
import qyw.xhx.zwzs.zy.Ont_Down;

/**
 * Created by xhx on 2018/9/27.
 */
public class Ont_Down_Adapter extends ListViewAdapter<Ont_Down> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public Ont_Down_Adapter(Context context, List<Ont_Down> datas) {
        super(context, datas, R.layout.ont_down_item);
    }
    @Override
    public void convert(ViewHolder holder, Ont_Down ont_down) {
        ((TextView) holder.getView(R.id.zh_label)).setText(ont_down.getCOVER_DEVICE());
        ((TextView) holder.getView(R.id.ahthor_value)).setText(ont_down.getAHTHOR_VALUE());
        ((TextView) holder.getView(R.id.band_account)).setText(ont_down.getBAND_ACCOUNT());
        ((TextView) holder.getView(R.id.state)).setText(ont_down.getSTATE());
    }
}