package qyw.xhx.zwzs.wh;
//{"ZH_LABEL":"18853187757","FINISH_STATE":"已开通","TIME_STAMP":"2014-11-02T15:52:47",
//        "HOLD_POS_ID":"-1567944232","ZH_LABEL1":"济南重汽翡翠郡南区9号楼1单元4层-POS001-1：8",
//        "HOLD_POS_PORT_ID":"-1567942591","DUANKOU":"01"}
public class Coverpos {
    private String DUANKOU; //注意大小写
    private String ZH_LABEL;
    private String FINISH_STATE;
    private String TIME_STAMP;
    public Coverpos(String DUANKOU, String ZH_LABEL, String FINISH_STATE, String TIME_STAMP){
        this.DUANKOU=DUANKOU;
        this.ZH_LABEL=ZH_LABEL;
        this.FINISH_STATE=FINISH_STATE;
        this.TIME_STAMP=TIME_STAMP;
     }
    public String getDUANKOU() {
        return DUANKOU;
    }
    public void setDUANKOU(String DUANKOU){
        this.DUANKOU=DUANKOU;
    }
    public String getZH_LABEL() {
        return ZH_LABEL;
    }
    public void setZH_LABEL(String ZH_LABEL){
        this.ZH_LABEL=ZH_LABEL;
    }
    public String getFINISH_STATE() {
        return FINISH_STATE;
    }
    public void setFINISH_STATE(String FINISH_STATE){
        this.FINISH_STATE=FINISH_STATE;
    }
    public String getTIME_STAMP() {
        return TIME_STAMP;
    }
    public void setTIME_STAMP(String TIME_STAMP){
        this.TIME_STAMP=TIME_STAMP;
    }

}
