package qyw.xhx.zwzs.zy;

public class Wangge {
    private String COUNTY_ID; //注意大小写
    private String RELATED_MAINGRID; //注意大小写
    private String TT_WG;
    public Wangge(){}
    public Wangge(String COUNTY_ID, String RELATED_MAINGRID, String TT_WG){
        this.COUNTY_ID=COUNTY_ID;
        this.RELATED_MAINGRID=RELATED_MAINGRID;
        this.TT_WG=TT_WG;
    }

    public String getCOUNTY_ID() {
        return COUNTY_ID;
    }
    public void setCOUNTY_ID(String COUNTY_ID){
        this.COUNTY_ID=COUNTY_ID;
    }
    public String getRELATED_MAINGRID() {
        return RELATED_MAINGRID;
    }
    public void setRELATED_MAINGRID(String RELATED_MAINGRID){
        this.RELATED_MAINGRID=RELATED_MAINGRID;
    }
    public String getTT_WG() {
        return TT_WG;
    }
    public void setTT_WG(String TT_WG){
        this.TT_WG=TT_WG;
    }
}
