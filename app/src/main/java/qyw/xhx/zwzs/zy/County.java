package qyw.xhx.zwzs.zy;

public class County {
    private String COUNTY_ID; //注意大小写
    private String COUNTY_NAME;
    private String XQS;
    private String LS;
    private String FHS;
    private String FGQS;
    public County(){}
    public County(String COUNTY_ID,String COUNTY_NAME,String XQS,String LS,String FHS,String FGQS){
        this.COUNTY_ID=COUNTY_ID;
        this.COUNTY_NAME=COUNTY_NAME;
        this.XQS=XQS;
        this.LS=LS;
        this.FHS=FHS;
        this.FGQS=FGQS;
    }

    public String getCOUNTY_ID() {
        return COUNTY_ID;
    }
    public void setCOUNTY_ID(String COUNTY_ID){
        this.COUNTY_ID=COUNTY_ID;
    }
    public String getCOUNTY_NAME() {
        return COUNTY_NAME;
    }
    public void setCOUNTY_NAME(String COUNTY_NAME){
        this.COUNTY_NAME=COUNTY_NAME;
    }
    public String getXQS() {
        return XQS;
    }
    public void setXQS(String XQS){
        this.XQS=XQS;
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
