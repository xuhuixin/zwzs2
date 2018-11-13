package qyw.xhx.zwzs.zy;

//{"AHTHOR_VALUE":"SDJN12151367","ID":"0","IP":"172.31.1.195",
//        "PON":"NA-0-2-5","STATE":"online","SJM":"135531797811542004216"}
public class Ont_Down {
    private String IP; //注意大小写
    private String PON; //注意大小写
    private String AHTHOR_VALUE;
    private String BAND_ACCOUNT;
    private String STATE;
    private String COVER_DEVICE;
    private String SJM;
    public Ont_Down(){}
    public Ont_Down(String IP,String PON, String AHTHOR_VALUE, String BAND_ACCOUNT, String STATE, String COVER_DEVICE, String SJM){
        this.IP=IP;
        this.PON=PON;
        this.AHTHOR_VALUE=AHTHOR_VALUE;
        this.BAND_ACCOUNT=BAND_ACCOUNT;
        this.STATE=STATE;
        this.COVER_DEVICE=COVER_DEVICE;
        this.SJM=SJM;
    }

    public String getIP() {
        return IP;
    }
    public void setIP(String IP){
        this.IP=IP;
    }
    public String getPON() {
        return PON;
    }
    public void setPON(String PON){
        this.PON=PON;
    }
    public String getAHTHOR_VALUE() {
        return AHTHOR_VALUE;
    }
    public void setAHTHOR_VALUE(String AHTHOR_VALUE){
        this.AHTHOR_VALUE=AHTHOR_VALUE;
    }
    public String getBAND_ACCOUNT() {
        return BAND_ACCOUNT;
    }
    public void setBAND_ACCOUNT(String BAND_ACCOUNT){
        this.BAND_ACCOUNT=BAND_ACCOUNT;
    }
    public String getSTATE() {
        return STATE;
    }
    public void setSTATE(String STATE){
        this.STATE=STATE;
    }
    public String getCOVER_DEVICE() {
        return COVER_DEVICE;
    }
    public void setCOVER_DEVICE(String COVER_DEVICE){
        this.COVER_DEVICE=COVER_DEVICE;
    }
    public String getSJM() {
        return SJM;
    }
    public void setSJM(String SJM){
        this.SJM=SJM;
    }

}
