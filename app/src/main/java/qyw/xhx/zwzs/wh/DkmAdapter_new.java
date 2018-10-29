package qyw.xhx.zwzs.wh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import java.util.List;

public class DkmAdapter_new extends ArrayAdapter<Dkm_new> {
    private int resouseId;

    public DkmAdapter_new(Context context, int textViewResourceId, List<Dkm_new> objects){
        super(context,textViewResourceId,objects);
        resouseId=textViewResourceId;
//        @Override
//        public View getView(int position,View convertView, ViewGroup parent){
//            Dkm_new dkm_new =getItem(position);
//            View view = LayoutInflater.from(getContext()).inflate(resouseId,parent,false)
        }


}
