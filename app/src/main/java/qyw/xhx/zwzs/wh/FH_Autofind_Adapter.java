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
public class FH_Autofind_Adapter extends ListViewAdapter<FH_Autofind> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public FH_Autofind_Adapter(Context context, List<FH_Autofind> datas) {
        super(context, datas, R.layout.fh_pon_autofind_item);
    }
    @Override
    public void convert(ViewHolder holder, FH_Autofind fh_autofind) {
        ((TextView) holder.getView(R.id.slot)).setText(fh_autofind.getSLOT());
        ((TextView) holder.getView(R.id.pon)).setText(fh_autofind.getPON());
        ((TextView) holder.getView(R.id.phyid)).setText(fh_autofind.getPHYID());
    }
}