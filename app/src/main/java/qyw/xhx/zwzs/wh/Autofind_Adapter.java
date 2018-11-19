package qyw.xhx.zwzs.wh;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import qyw.xhx.zwzs.ListViewAdapter;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.ViewHolder;
import qyw.xhx.zwzs.wh.Autofind;

/**
 * Created by xhx on 2018/9/27.
 */
public class Autofind_Adapter extends ListViewAdapter<Autofind> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public Autofind_Adapter(Context context, List<Autofind> datas) {
        super(context, datas, R.layout.pon_autofind_item);
    }
    @Override
    public void convert(ViewHolder holder, Autofind autofind) {
        ((TextView) holder.getView(R.id.pon_number)).setText(autofind.getPon_number());
        ((TextView) holder.getView(R.id.pon_kou)).setText(autofind.getPon_kou());
        ((TextView) holder.getView(R.id.ont_sn)).setText(autofind.getOnt_sn());
        ((TextView) holder.getView(R.id.autofind_time)).setText(autofind.getAutofind_time());
    }
}