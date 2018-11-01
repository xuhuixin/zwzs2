package qyw.xhx.zwzs.wh;
//[{"USER_LABEL":"NA-0-1-5","INT_ID":1034868983.0,"RATE":"1250M","NENAME":"822630916","PORT_TYPE":"GPON_OLT","FGQSL":"6"}
public class Oltport {
    private String USER_LABEL; //注意大小写
    private String INT_ID;
    private String RATE;
    private String NENAME;
    private String PORT_TYPE;
    private String FGQSL;
    public Oltport(String USER_LABEL, String INT_ID, String RATE, String NENAME,String PORT_TYPE,String FGQSL){
        this.USER_LABEL=USER_LABEL;
        this.INT_ID=INT_ID;
        this.RATE=RATE;
        this.NENAME=NENAME;
        this.PORT_TYPE=PORT_TYPE;
        this.FGQSL=FGQSL;
     }
    public String getUSER_LABEL() {
        return USER_LABEL;
    }
    public void setUSER_LABEL(String USER_LABEL){
        this.USER_LABEL=USER_LABEL;
    }
    public String getINT_ID() {
        return INT_ID;
    }
    public void setINT_ID(String INT_ID){
        this.INT_ID=INT_ID;
    }
    public String getRATE() {
        return RATE;
    }
    public void setRATE(String RATE){
        this.RATE=RATE;
    }
    public String getNENAME() {
        return NENAME;
    }
    public void setNENAME(String NENAME){
        this.NENAME=NENAME;
    }
    public String getPORT_TYPE() {
        return PORT_TYPE;
    }
    public void setPORT_TYPE(String PORT_TYPE){
        this.PORT_TYPE=PORT_TYPE;
    }
    public String getFGQSL() {
        return FGQSL;
    }
    public void setFGQSL(String FGQSL){
        this.FGQSL=FGQSL;
    }

}
