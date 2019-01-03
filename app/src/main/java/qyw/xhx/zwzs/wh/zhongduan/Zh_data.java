package qyw.xhx.zwzs.wh.zhongduan;
//[{"cityName":"济南","onu":null,"sn":"ZTEGC9DC2AE5","stb":null,"mac":null,"vendor":"中兴",
// "devType":"中兴ZXHN F663N","godownName":"?济阳-济北1社区网格","statusTrip":"出库",
// "statusUsable":"在用","inTime":"2018-03-23 14:50:16","firmWare":null,"osType":null,"osVersion":null},{"cityName":"济南","onu":null,"sn":"00E400AA3A12","stb":null,"mac":null,"vendor":"中移终端公司","devType":"中移终端公司-CM201-1","godownName":"?济阳-济北2社区网格","statusTrip":"出库","statusUsable":"在用","inTime":"2018-03-21 18:36:09","firmWare":null,"osType":null,"osVersion":null}]
public class Zh_data {
    private String cityName; //注意大小写
    private String sn;
    private String account;
    private String vendor;
    private String devType;
    private String godownName;
    private String statusTrip;
    private String statusUsable;
    private String inTime;
    public Zh_data(String cityName, String sn,String account, String vendor,String devType,
                   String godownName,String statusTrip,String statusUsable,String inTime){
        this.cityName=cityName;
        this.sn=sn;
        this.account=account;
        this.vendor=vendor;
        this.devType=devType;
        this.godownName=godownName;
        this.statusTrip=statusTrip;
        this.statusUsable=statusUsable;
        this.inTime=inTime;
    }
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName){
        this.cityName=cityName;
    }
    public String getSn() {
        return sn;
    }
    public void setSn(String sn){
        this.sn=sn;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account){
        this.account=account;
    }
    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor){
        this.vendor=vendor;
    }
    public String getDevType() {
        return devType;
    }
    public void setDevType(String devType){
        this.devType=devType;
    }
    public String getGodownName() {
        return godownName;
    }
    public void setGodownName(String godownName){
        this.godownName=godownName;
    }
    public String getStatusTrip() {
        return statusTrip;
    }
    public void setStatusTrip(String statusTrip){
        this.statusTrip=statusTrip;
    }
    public String getStatusUsable() {
        return statusUsable;
    }
    public void setStatusUsable(String statusUsable){
        this.statusUsable=statusUsable;
    }
    public String getInTime() {
        return inTime;
    }
    public void setInTime(String inTime){
        this.inTime=inTime;
    }
}
