package qyw.xhx.zwzs.wh;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import qyw.xhx.zwzs.ListViewAdapter;
import qyw.xhx.zwzs.R;
import qyw.xhx.zwzs.ViewHolder;
import qyw.xhx.zwzs.wh.zhongduan.Zh_data;

/**
 * Created by xhx on 2018/9/27.
 */
public class Zdzy_new_Adapter extends ListViewAdapter<Zh_data> {
    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public Zdzy_new_Adapter(Context context, List<Zh_data> datas) {
        super(context, datas, R.layout.zh_data_item);
    }
    @Override
    public void convert(ViewHolder holder, Zh_data zh_data) {
        ((TextView) holder.getView(R.id.cityName)).setText(zh_data.getCityName());
        ((TextView) holder.getView(R.id.sn)).setText(zh_data.getSn());
        ((TextView) holder.getView(R.id.account)).setText(zh_data.getAccount());
        ((TextView) holder.getView(R.id.vendor)).setText(zh_data.getVendor());
        ((TextView) holder.getView(R.id.devType)).setText(zh_data.getDevType());
        ((TextView) holder.getView(R.id.godownName)).setText(zh_data.getGodownName());
        ((TextView) holder.getView(R.id.statusTrip)).setText(zh_data.getStatusTrip());
        ((TextView) holder.getView(R.id.statusUsable)).setText(zh_data.getStatusUsable());
        ((TextView) holder.getView(R.id.inTime)).setText(zh_data.getInTime());
    }
}