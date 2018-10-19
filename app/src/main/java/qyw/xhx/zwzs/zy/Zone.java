package qyw.xhx.zwzs.zy;

public class Zone {
    private String ZONE_ID; //注意大小写
    private String ZONE_NAME; //注意大小写
    private String LS;
    private String FHS;
    private String FGQS;
    public Zone(){}
    public Zone(String ZONE_ID, String ZONE_NAME, String LS, String FHS, String FGQS){
        this.ZONE_ID=ZONE_ID;
        this.ZONE_NAME=ZONE_NAME;
        this.LS=LS;
        this.FHS=FHS;
        this.FGQS=FGQS;
    }

    public String getZONE_ID() {
        return ZONE_ID;
    }
    public void setZONE_ID(String ZONE_ID){
        this.ZONE_ID=ZONE_ID;
    }
    public String getZONE_NAME() {
        return ZONE_NAME;
    }
    public void setZONE_NAME(String ZONE_NAME){
        this.ZONE_NAME=ZONE_NAME;
    }
    public String getLS() {
        return LS;
    }
    public void setLS(String LS){
        this.LS=LS;
    }
    public String getFHS() {
        return FHS;
    }
    public void setFHS(String FHS){
        this.FHS=FHS;
    }
    public String getFGQS() {
        return FGQS;
    }
    public void setFGQS(String FGQS){
        this.FGQS=FGQS;
    }

}
