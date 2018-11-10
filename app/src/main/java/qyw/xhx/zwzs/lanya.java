package qyw.xhx.zwzs;

//import com.wewin.printer_qinghai_yd_jk.R;
//import com.wewin.printer_shandong_yd_.MainActivity;

import Jack.WewinPrinterHelper.AsyncProgress;
import Jack.WewinPrinterHelper.Print;
import android.R.interpolator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class lanya extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    Button button1 = null;
    Button button2 = null;

    String xml = "";

    // key code
    Print p = null;
    // key code
    Handler handler = new Handler();
    // key code
    public boolean flag = true;

    ImageView imageView1 = null;

    String xmlStr1 = "<Data><Print><row><type>02F</type><code>12345678</code><text>济南市市中区箭如意苑25-1-ONU14口</text></row></Print></Data>";

    String xmlStr2 = "<Data><Print><row><type>25-75</type><code>12345678</code><text>济南市市中区箭如意苑25-1-ONU14口济南市市中区箭如意苑25-1-ONU14口</text></row></Print></Data>";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lanya_main);

        // key code
        p = new Print(lanya.this);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);


        imageView1 = (ImageView) findViewById(R.id.imageView1);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.button1:
                xml = xmlStr1;
                break;
            case R.id.button2:
                xml = xmlStr2;
                break;
        }

        imageView1
                .setImageBitmap(p.getBitmapForPreview(xml, lanya.this));

        // key code
        AsyncProgress.indeterminate(lanya.this, handler, "正在打印中，请稍候...",
                new Runnable() {

                    @Override
                    public void run() {

                        try {
                            while (flag) {
                                String result = p.LabelPrint(xml,
                                        lanya.this);
                                Toast.makeText(lanya.this, result, 1)
                                        .show();
                                flag = false;
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        flag = true;
                    }
                });
    }

    @Override
    protected void onPause() {
        p.bluetoothClose();
        super.onPause();
    }

}