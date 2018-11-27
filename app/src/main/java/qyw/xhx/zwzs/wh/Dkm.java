package qyw.xhx.zwzs.wh;
//[{"ZH_LABEL":"13553179781","FULL_ADDR":"济南市天桥区无影山路街道西工商河路重汽翡翠郡南区9号楼1单元5层502室",
// "FLOWID":"1445908385969225","COVER_DEVICE":"济南重汽翡翠郡南区9号楼1单元4层-POS001-1：8","COVER_PORT":"07",
// "GRID_NAME":"家客铁通天桥-济洛网格装维组","COVER_TYPE":"FTTH","END_TIME":"2015-10-27T11:56:18",
// "FLOW_TITLE":"天桥区无影山路街道西工商河路重汽翡翠郡-家客业务移机",
//        "HOLD_POS_ID":"-1567944232","AHTHOR_VALUE":"SDJN03751124"}]
public class Dkm {
    private String ZH_LABEL; //注意大小写
    private String FULL_ADDR;
    private String FLOWID;
    private String COVER_DEVICE;
    private String COVER_PORT;
    private String GRID_NAME;
    private String COVER_TYPE;
    private String END_TIME;
    private String FLOW_TITLE;
    private String HOLD_POS_ID;
    private String AHTHOR_VALUE;
    private String HOUSE_ID;
    private String OLT_NAME;
    private String OLT_PON;


    public Dkm(String ZH_LABEL, String FULL_ADDR, String FLOWID, String COVER_DEVICE,
               String COVER_PORT,String GRID_NAME,String COVER_TYPE,String END_TIME,
               String FLOW_TITLE,String HOLD_POS_ID,String AHTHOR_VALUE,String HOUSE_ID,
               String OLT_NAME,String OLT_PON){
        this.ZH_LABEL=ZH_LABEL;
        this.FULL_ADDR=FULL_ADDR;
        this.FLOWID=FLOWID;
        this.COVER_DEVICE=COVER_DEVICE;
        this.COVER_PORT=COVER_PORT;
        this.GRID_NAME=GRID_NAME;
        this.COVER_TYPE=COVER_TYPE;
        this.END_TIME=END_TIME;
        this.FLOW_TITLE=FLOW_TITLE;
        this.HOLD_POS_ID=HOLD_POS_ID;
        this.AHTHOR_VALUE=AHTHOR_VALUE;
        this.HOUSE_ID=HOUSE_ID;
        this.OLT_NAME=OLT_NAME;
        this.OLT_PON=OLT_PON;
     }

    public String getZH_LABEL() {
        return ZH_LABEL;
    }
    public void setZH_LABEL(String ZH_LABEL){
        this.ZH_LABEL=ZH_LABEL;
    }
    public String getFULL_ADDR() {
        return FULL_ADDR;
    }
    public void setFULL_ADDR(String FULL_ADDR){
        this.FULL_ADDR=FULL_ADDR;
    }
    public String getFLOWID() {
        return FLOWID;
    }
    public void setFLOWID(String FLOWID){
        this.FLOWID=FLOWID;
    }
    public String getCOVER_DEVICE() {
        return COVER_DEVICE;
    }
    public void setCOVER_DEVICE(String COVER_DEVICE){
        this.COVER_DEVICE=COVER_DEVICE;
    }
    public String getCOVER_PORT() {
        return COVER_PORT;
    }
    public void setCOVER_PORT(String COVER_PORT){
        this.COVER_PORT=COVER_PORT;
    }
    public String getGRID_NAME() {
        return GRID_NAME;
    }
    public void setGRID_NAME(String GRID_NAME){
        this.GRID_NAME=GRID_NAME;
    }
    public String getCOVER_TYPE() {
        return COVER_TYPE;
    }
    public void setCOVER_TYPE(String COVER_TYPE){
        this.COVER_TYPE=COVER_TYPE;
    }
    public String getEND_TIME() {
        return END_TIME;
    }
    public void setEND_TIME(String END_TIME){
        this.END_TIME=END_TIME;
    }
    public String getFLOW_TITLE() {
        return FLOW_TITLE;
    }
    public void setFLOW_TITLE(String FLOW_TITLE){
        this.FLOW_TITLE=FLOW_TITLE;
    }
    public String getHOLD_POS_ID() {
        return HOLD_POS_ID;
    }
    public void setHOLD_POS_ID(String HOLD_POS_ID){
        this.HOLD_POS_ID=HOLD_POS_ID;
    }
    public String getAHTHOR_VALUE() {
        return AHTHOR_VALUE;
    }
    public void setAHTHOR_VALUE(String AHTHOR_VALUE){
        this.AHTHOR_VALUE=AHTHOR_VALUE;
    }
    public String getHOUSE_ID() {
        return HOUSE_ID;
    }
    public void setHOUSE_ID(String HOUSE_ID){
        this.HOUSE_ID=HOUSE_ID;
    }

    public String getOLT_NAME() {
        return OLT_NAME;
    }
    public void setOLT_NAME(String OLT_NAME){
        this.OLT_NAME=OLT_NAME;
    }
    public String getOLT_PON() {
        return OLT_PON;
    }
    public void setOLT_PON(String OLT_PON){
        this.OLT_PON=OLT_PON;
    }
}
