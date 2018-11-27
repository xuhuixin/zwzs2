package qyw.xhx.zwzs.wh;
//[{"port_state":"Offline","last_up_time":"2018-11-23 10:59:30+08:00",
// "last_down_time":"2018-11-23 11:19:10+08:00","Optical_Module_status":"Online"}]
public class Pon_down {
    private String port_state;
    private String last_up_time;
    private String last_down_time;
    private String olt_name;
    private String pon;
    private String Optical_Module_status;

    public Pon_down(String port_state, String last_up_time,String last_down_time,String olt_name,String pon,String Optical_Module_status){
        this.port_state=port_state;
        this.last_up_time=last_up_time;
        this.last_down_time=last_down_time;
        this.olt_name=olt_name;
        this.pon=pon;
        this.Optical_Module_status=Optical_Module_status;
     }

    public String getPort_state() {
        return port_state;
    }
    public void setPort_state(String port_state){
        this.port_state=port_state;
    }
    public String getLast_up_time() {
        return last_up_time;
    }
    public void setLast_up_time(String last_up_time){
        this.last_up_time=last_up_time;
    }
    public String getLast_down_time() {
        return last_down_time;
    }
    public void setLast_down_time(String last_down_time){
        this.last_down_time=last_down_time;
    }

    public String getOlt_name() {
        return olt_name;
    }
    public void setOlt_name(String olt_name){
        this.olt_name=olt_name;
    }
    public String getPon() {
        return pon;
    }
    public void setPon(String pon){
        this.pon=pon;
    }

    public String getOptical_Module_status() {
        return Optical_Module_status;
    }

    public void setOptical_Module_status(String Optical_Module_status){
        this.Optical_Module_status=Optical_Module_status;
    }

}
