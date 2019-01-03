package qyw.xhx.zwzs.wh.zhongduan;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import qyw.xhx.zwzs.wh.zhongduan.Zh_all;
import qyw.xhx.zwzs.wh.Zdzy_new_view;

public class Zh_utility {
    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Zh_all handleZhResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            String zh_data = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(zh_data, Zh_all.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
