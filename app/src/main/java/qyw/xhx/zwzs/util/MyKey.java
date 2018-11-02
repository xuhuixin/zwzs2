package qyw.xhx.zwzs.util;

import android.content.Context;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import qyw.xhx.zwzs.util.DateUtil;
import qyw.xhx.zwzs.util.Md5Utils;
public class MyKey {
    public static String key() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String t=format.format(new Date());
        String key=Md5Utils.md5("khsl"+t);
        return key;
    }



}
