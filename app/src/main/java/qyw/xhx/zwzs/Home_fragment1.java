package qyw.xhx.zwzs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Home_fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zy_main, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /** 房号按钮及点击**/
        Button button_zy = (Button) getActivity().findViewById(R.id.zy_fh);
        button_zy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**打开区县class，intent 需在androidMainfest.xml中注册**/
                Intent intent =new Intent("com.qyw.xhx.mytest.ACTION_COUNTY");
                startActivity(intent);
//                Toast.makeText(getActivity(), "zy Clicked", Toast.LENGTH_LONG).show();
            }
        });

        /** 设备按钮及点击**/
        Button button_sb = (Button) getActivity().findViewById(R.id.zy_sb);
        button_sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "sb Clicked", Toast.LENGTH_LONG).show();
            }
        });
        /** 用户按钮及点击**/
        Button button_yh = (Button) getActivity().findViewById(R.id.zy_yh);
        button_yh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "yh Clicked", Toast.LENGTH_LONG).show();
            }
        });
    }

}
