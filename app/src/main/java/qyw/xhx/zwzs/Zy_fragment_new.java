package qyw.xhx.zwzs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import qyw.xhx.zwzs.utils.Constant;
import qyw.xhx.zwzs.wh.Dkm1_view;

public class Zy_fragment_new extends Fragment {
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.zy_layout_new, container, false);
            }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mContext = getActivity();
        Button sweepButton = (Button) getActivity().findViewById(R.id.guangwang);
        sweepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_SAOMIAO");
                startActivity(intent);
            }
        });
        Button ponButton = (Button) getActivity().findViewById(R.id.ont_offline);
        ponButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_ONTOFFLINE");
                startActivity(intent);
            }
        });

    }


}