package qyw.xhx.zwzs;

import android.app.Application;

public class MyApplication extends Application {
    private String number = "1111111555555";


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
