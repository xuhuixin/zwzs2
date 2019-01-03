package qyw.xhx.zwzs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Wh_fragment_new extends Fragment {
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.wh_layout_new, container, false);
            }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mContext = getActivity();
        Button pon_chaxun = (Button) getActivity().findViewById(R.id.pon_chaxun);
        pon_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_PON");
                startActivity(intent);
            }
        });
        Button pon_search = (Button) getActivity().findViewById(R.id.pon_search);
        pon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_PON_SEARCH");
                startActivity(intent);
            }
        });
        Button ggl = (Button) getActivity().findViewById(R.id.ggl);
        ggl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_GGL");
                startActivity(intent);
            }
        });

        Button dkm_chaxun = (Button) getActivity().findViewById(R.id.duankou);
        dkm_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_DKM");
                startActivity(intent);
            }
        });
        Button feibiao_chaxun = (Button) getActivity().findViewById(R.id.feibiao);
        feibiao_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_FEIBIAO");
                startActivity(intent);
            }
        });
        Button jqm_chaxun = (Button) getActivity().findViewById(R.id.jqm);
        jqm_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_JQM");
                startActivity(intent);
//                startActivity(new Intent(getActivity(), PayMoneyActivity.class));
            }
        });
        Button zhongduan_chaxun = (Button) getActivity().findViewById(R.id.zdzy);
        zhongduan_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent("qyw.xhx.zwzs.ACTION_ZDZY");
                startActivity(intent);
            }
        });

    }


}