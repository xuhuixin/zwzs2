package qyw.xhx.zwzs;

import android.app.Application;

public class MyApplication extends Application {
    private String number = "1111111555555";
    private String city="jinan";
    private String city_id="531";
    private String server_url="13213132";
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getServer_url() {
        return server_url;
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }
}
