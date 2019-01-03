package qyw.xhx.zwzs.wh.zhongduan;

import java.util.List;

//{"result":"0","describe":"成功","data":[{"cityName"
public class Zh_all {
    private String result; //注意大小写
    private String describe;
    private List<Data> data;
    public Zh_all(String result, String describe, List data){
        this.result=result;
        this.describe=describe;
        this.data=data;
    }
    public String getResult() {
        return result;
    }
    public String getDescribe() {
        return describe;
    }
    public List<Data> getData() {
        return data;
    }

    public static class Data{
        public String cityName; //注意大小写
        public String onu;
        public String sn;
        public String account;
        public String stb;
        public String mac;
        public String vendor;
        public String devType;
        public String godownName;
        public String statusTrip;
        public String statusUsable;
        public String inTime;
        public String firmWare;
        public String osType;
        public String osVersion;
        public String getCityName() {
            return cityName;
        }
        public String getSn() {
            return sn;
        }
        public String getAccount() {
            return account;
        }
        public String getVendor() {
            return vendor;
        }
        public String getDevType() {
            return devType;
        }
        public String getGodownName() {
            return godownName;
        }
        public String getStatusTrip() {
            return statusTrip;
        }
        public String getStatusUsable() {
            return statusUsable;
        }
        public String getInTime() {
            return inTime;
        }
    }
}
