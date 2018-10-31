package qyw.xhx.zwzs.wh;

public class Dkm_new {
    private String FLOWID;
    private String FLOW_NAME;
    private String BAND_ACCOUNT;
    private String FORM_STATE;
    private String END_TIME;
    private String ZONE_NAME;

    public Dkm_new(String FLOWID,String FLOW_NAME,String BAND_ACCOUNT,String FORM_STATE,String END_TIME,String ZONE_NAME){
        this.FLOWID=FLOWID;
        this.FLOW_NAME=FLOW_NAME;
        this.BAND_ACCOUNT=BAND_ACCOUNT;
        this.FORM_STATE=FORM_STATE;
        this.END_TIME=END_TIME;
        this.ZONE_NAME=ZONE_NAME;
    }
    public String getFLOWID(){return FLOWID;}
    public String getFLOW_NAME(){return FLOW_NAME;}
    public String getBAND_ACCOUNT(){return BAND_ACCOUNT;}
    public String getFORM_STATE(){return FORM_STATE;}
    public String getEND_TIME(){return END_TIME;}
    public String getZONE_NAME(){return ZONE_NAME;}
}
