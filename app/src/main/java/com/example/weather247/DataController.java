package com.example.weather247;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataController {
    String currentTemperature;
    String currentCondition;
    String currentIcon;
    JSONObject urlResponseJson;
    Context context;

    public DataController(Context context, JSONObject urlResponseJson){
        this.urlResponseJson = urlResponseJson;
        this.context = context;
    }

    public void saveCurrentBasicData() {

        try {
            currentTemperature = urlResponseJson.getJSONObject("current").getString("temp_c");
            currentCondition = urlResponseJson.getJSONObject("current").getJSONObject("condition").getString("text");
            currentIcon = urlResponseJson.getJSONObject("current").getJSONObject("condition").getString("icon");
            currentIcon = "http:"+currentIcon;

            Cache.saveCurrentTemperature(context, currentTemperature);
            Cache.saveCurrentCondition(context, currentCondition);
            Cache.saveCurrentIcon(context, currentIcon);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
