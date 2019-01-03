package qyw.xhx.zwzs.wh;
//zh,ahthor_value,pon,xh,yy,xxsj,sxsj,ggl
public class Ggl {
    private String ZH;
    private String AHTHOR_VALUE;
    private String PON;
    private String XH;
    private String YY;
    private String XXSJ;
    private String SXSJ;
    private String GGL;
    private String RUN_STATE;

    public Ggl(String ZH, String AHTHOR_VALUE, String PON, String XH, String YY,
               String XXSJ,String SXSJ,String GGL,String RUN_STATE){
        this.ZH=ZH;
        this.AHTHOR_VALUE=AHTHOR_VALUE;
        this.PON=PON;
        this.XH=XH;
        this.YY=YY;
        this.XXSJ=XXSJ;
        this.SXSJ=SXSJ;
        this.GGL=GGL;
        this.RUN_STATE=RUN_STATE;
     }

    public String getZH() {
        return ZH;
    }
    public void setZH(String ZH){
        this.ZH=ZH;
    }
    public String getAHTHOR_VALUE() {
        return AHTHOR_VALUE;
    }
    public void setAHTHOR_VALUE(String AHTHOR_VALUE){
        this.AHTHOR_VALUE=AHTHOR_VALUE;
    }
    public String getPON() {
        return PON;
    }
    public void setPON(String PON){
        this.PON=PON;
    }
    public String getXH() {
        return XH;
    }
    public void setXH(String XH){
        this.XH=XH;
    }
    public String getYY() {
        return YY;
    }
    public void setYY(String YY){
        this.YY=YY;
    }
    public String getXXSJ() {
        return XXSJ;
    }
    public void setXXSJ(String XXSJ){
        this.XXSJ=XXSJ;
    }
    public String getSXSJ() {
        return SXSJ;
    }
    public void setSXSJ(String SXSJ){
        this.SXSJ=SXSJ;
    }
    public String getGGL() {
        return GGL;
    }
    public void setGGL(String GGL){
        this.GGL=GGL;
    }
    public String getRUN_STATE() {
        return RUN_STATE;
    }
    public void setRUN_STATE(String RUN_STATE){
        this.RUN_STATE=RUN_STATE;
    }

}
