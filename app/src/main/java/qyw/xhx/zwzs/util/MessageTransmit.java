package qyw.xhx.zwzs.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;

import qyw.xhx.zwzs.MainActivity;
import qyw.xhx.zwzs.Main_view;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MessageTransmit extends Handler implements Runnable{
//    private WeakReference<Activity> mActivity;
    private static final String TAG = "TAG";
    private static final String SOCKET_IP = "122.80.61.118";
    private static final int SOCKET_PORT = 9050;


    private OutputStream mWriter = null;
    private BufferedReader mReader = null;
    private Socket mSocket;
    private Handler mHandler;

    @Override
    public void run() {
        mSocket = new Socket();
        try {
            mSocket.connect(new InetSocketAddress(SOCKET_IP, SOCKET_PORT), 3000);
            mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            mWriter = mSocket.getOutputStream();
            // 启动一条子线程来读取服务器的返回数据
//            new RecvThread().start();
            Looper.prepare();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void handleMessage(Message msg) {
        Log.d(TAG, "handleMessage: "+msg.obj);
        // 换行符相当于回车键，表示我写好了发出去吧
        String send_msg = msg.obj.toString()+"\n";
        try {
            mWriter.write(send_msg.getBytes("utf8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 定义消息接收子线程，App从后台服务器接收消息
    public class RecvThread extends Thread {
        @Override
        public void run() {
            try {
                String content = null;
//                String content1=null;
                while ((content = mReader.readLine()) != null) {
                    // 读取到来自服务器的数据
                    Message msg = Message.obtain();
//                    String fhsj="我要验证";
                    msg.obj = content;
//                    MainActivity.mHandler.sendMessage(msg);
                    Log.d("接收到服务器login消息",content);
//                    Message fhsjMessage = Message.obtain();
//                    fhsjMessage.obj=fhsj;
//                    handleMessage(fhsjMessage);
//
//                    content1 = mReader.readLine();
//                    Message msg1 = Message.obtain();
//                    msg1.obj = content;
//                    Log.d("服务器验证通过信息",content1);
//                    mReader.close();
//                    MainActivity.mHandler.sendMessage(msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}