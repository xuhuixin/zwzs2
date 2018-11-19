package qyw.xhx.zwzs.wh;
//{"Pon_number":"1","Pon_kou":"0\/3\/10","Ont_sn":"48575443CEC8AE9B (HWTC-CEC8AE9B)",
// "Autofind_time":"2018-11-16 13:50:53+08:00"}
public class Autofind {
    private String Pon_number; //注意大小写
    private String Pon_kou;
    private String Ont_sn;
    private String Autofind_time;

    public Autofind(String Pon_number, String Pon_kou, String Ont_sn, String Autofind_time){
        this.Pon_number=Pon_number;
        this.Pon_kou=Pon_kou;
        this.Ont_sn=Ont_sn;
        this.Autofind_time=Autofind_time;
     }

    public String getPon_number() {
        return Pon_number;
    }
    public void setPon_number(String Pon_number){
        this.Pon_number=Pon_number;
    }
    public String getPon_kou() {
        return Pon_kou;
    }
    public void setPon_kou(String Pon_kou){
        this.Pon_kou=Pon_kou;
    }
    public String getOnt_sn() {
        return Ont_sn;
    }
    public void setOnt_sn(String Ont_sn){
        this.Ont_sn=Ont_sn;
    }
    public String getAutofind_time() {
        return Autofind_time;
    }
    public void setAutofind_time(String Autofind_time){
        this.Autofind_time=Autofind_time;
    }

}
