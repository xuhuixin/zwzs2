package qyw.xhx.zwzs;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by gaomin on 2016/3/21.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{
    public   List<T> T;

    public MyBaseAdapter(List<T> t){
        T=t;
    }
    @Override
    public int getCount() {
        return T.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}


