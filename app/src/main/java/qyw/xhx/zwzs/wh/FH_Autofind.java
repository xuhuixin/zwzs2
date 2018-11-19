package qyw.xhx.zwzs.wh;
//{"Pon_number":"1","Pon_kou":"0\/3\/10","Ont_sn":"48575443CEC8AE9B (HWTC-CEC8AE9B)",
// "Autofind_time":"2018-11-16 13:50:53+08:00"}
public class FH_Autofind {
    private String SLOT; //注意大小写
    private String PON;
    private String PHYID;

    public FH_Autofind(String SLOT, String PON, String PHYID){
        this.SLOT=SLOT;
        this.PON=PON;
        this.PHYID=PHYID;
     }

    public String getSLOT() {
        return SLOT;
    }
    public void setSLOT(String SLOT){
        this.SLOT=SLOT;
    }
    public String getPON() {
        return PON;
    }
    public void setPON(String PON){
        this.PON=PON;
    }
    public String getPHYID() {
        return PHYID;
    }
    public void setPHYID(String PHYID){
        this.PHYID=PHYID;
    }


}
