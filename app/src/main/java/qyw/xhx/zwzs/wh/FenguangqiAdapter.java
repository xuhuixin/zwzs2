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
public class FenguangqiAdapter extends ListViewAdapter<Fenguangqi> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public FenguangqiAdapter(Context context, List<Fenguangqi> datas) {
        super(context, datas, R.layout.fenguangqi_item);
    }
    @Override
    public void convert(ViewHolder holder, Fenguangqi fenguangqi) {
        ((TextView) holder.getView(R.id.zh_label)).setText(fenguangqi.getZH_LABEL());
        ((TextView) holder.getView(R.id.int_id)).setText(fenguangqi.getINT_ID());

    }
}