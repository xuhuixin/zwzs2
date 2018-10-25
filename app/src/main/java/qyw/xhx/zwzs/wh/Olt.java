package qyw.xhx.zwzs.wh;

public class Olt {
    private String USER_LABEL; //注意大小写
    private String EQUIP_ROOM;
    private String DEV_IPADDR;
    private String VENDOR;

    public Olt(){}
    public Olt(String USER_LABEL, String EQUIP_ROOM, String DEV_IPADDR, String VENDOR){
        this.USER_LABEL=USER_LABEL;
        this.EQUIP_ROOM=EQUIP_ROOM;
        this.DEV_IPADDR=DEV_IPADDR;
        this.VENDOR=VENDOR;

    }

    public String getUSER_LABEL() {
        return USER_LABEL;
    }
    public void setUSER_LABEL(String USER_LABEL){
        this.USER_LABEL=USER_LABEL;
    }
    public String getEQUIP_ROOM() {
        return EQUIP_ROOM;
    }
    public void setEQUIP_ROOM(String EQUIP_ROOM){
        this.EQUIP_ROOM=EQUIP_ROOM;
    }
    public String getDEV_IPADDR() {
        return DEV_IPADDR;
    }
    public void setDEV_IPADDR(String DEV_IPADDR){
        this.DEV_IPADDR=DEV_IPADDR;
    }
    public String getVENDOR() {
        return VENDOR;
    }
    public void setVENDOR(String VENDOR){
        this.VENDOR=VENDOR;
    }


}
