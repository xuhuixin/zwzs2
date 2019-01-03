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
public class Ggl_Adapter extends ListViewAdapter<Ggl> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public Ggl_Adapter(Context context, List<Ggl> datas) {
        super(context, datas, R.layout.ggl_ans_item);
    }
    @Override
    public void convert(ViewHolder holder, Ggl ggl) {
        ((TextView) holder.getView(R.id.zh_label)).setText(ggl.getZH());
        ((TextView) holder.getView(R.id.xh)).setText(ggl.getXH());
        ((TextView) holder.getView(R.id.yy)).setText(ggl.getYY());
        ((TextView) holder.getView(R.id.xxsj)).setText(ggl.getXXSJ());
        ((TextView) holder.getView(R.id.sxsj)).setText(ggl.getSXSJ());
        ((TextView) holder.getView(R.id.ggl)).setText(ggl.getGGL());
        ((TextView) holder.getView(R.id.run_state)).setText(ggl.getRUN_STATE());
    }
}