package qyw.xhx.zwzs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Home_fragment2 extends Fragment {
    private MyApplication myApplication;//初始化全局变量
    static WebView mWeb;
    private View mContentView;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private String key;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication) getActivity().getApplication();
//            int test = ((MyApplication) getActivity().getApplication()).();
        Log.d("testfrag", "" + myApplication.getNumber());
        key=md5("SD"+myApplication.getNumber());
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    webViewGoBack();
                }
                break;
            }
        }

    };

    private void webViewGoBack() {
        mWeb.goBack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mContentView = inflater.inflate(R.layout.layout_home_fragment2, null);
        mWeb = (WebView) mContentView.findViewById(R.id.webview);


        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        mWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWeb.getSettings().setSupportZoom(false);  //支持放大缩小
        mWeb.getSettings().setBuiltInZoomControls(true);
        //屏幕自适应
        mWeb.getSettings().setUseWideViewPort(true);
        mWeb.getSettings().setLoadWithOverviewMode(true);
        //加密函数

        Log.d("KEY",key);
        mWeb.loadUrl("http://122.80.61.118:9011/zwzs_new/yyhf.asp?sjhm="+myApplication.getNumber()+"&key="+key);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWeb.getSettings().setSaveFormData(true);// 保存表单数据
        mWeb.setWebViewClient(new WebViewClient());
        String cacheDirPath = getActivity().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME; //缓存路径

//        mWeb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //缓存模式
        mWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //无缓存模式
        mWeb.getSettings().setAppCachePath(cacheDirPath); //设置缓存路径
        mWeb.getSettings().setAppCacheEnabled(false); //开启缓存功能

        //不加，单击超连接，启动系统的浏览器，加了之后在我们自己的APP中显示网页。
//        settings.setJavaScriptEnabled(true);
        mWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading
                    (WebView view, String url) {
                Log.i("用户单击超连接", url);
                //判断用户单击的是那个超连接
                String tag="tada:tel";
                if (url.contains(tag))
                {
                    String mobile=url.substring(url.lastIndexOf("/")+1);
                    Log.d("tel",mobile);
                    Uri uri=Uri.parse("tel:"+mobile);
                    Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                    startActivity(intent);
                    //这个超连接,java已经处理了，webview不要处理了
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);



//                return super.shouldOverrideUrlLoading(view, url);
            }
        });




        mWeb.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWeb.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }

        });
        return mContentView;
    }
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
