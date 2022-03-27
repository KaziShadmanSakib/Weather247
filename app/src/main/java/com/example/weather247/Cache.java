package com.example.weather247;
import android.content.Context;
import android.content.SharedPreferences;


public class Cache {

    private static final String My_Preference_Name = "com.example.weather247";
    private static final String Pref_userLocation_Key = "pref_userLocation_key";
    private static final String Pref_currentTemperature_Key = "pref_currentTemperature_key";
    private static final String Pref_currentCondition_Key = "pref_currentCondition_key";
    private static final String Pref_currentIcon_Key = "pref_currentIcon_key";

    public static String loadCurrentIcon(Context context){
        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_currentIcon_Key, "http://cdn.weatherapi.com/weather/64x64/day/113.png");
    }

    public static void saveCurrentIcon(Context context, String currentIcon){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_currentIcon_Key, currentIcon);
        editor.commit();

    }

    public static String loadCurrentCondition(Context context){
        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_currentCondition_Key, "Sunny");
    }

    public static void saveCurrentCondition(Context context, String currentCondition){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_currentCondition_Key, currentCondition);
        editor.commit();

    }

    public static String loadCurrentTemperature(Context context){
        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_currentTemperature_Key, "30");
    }

    public static void saveCurrentTemperature(Context context, String currentTemperature){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_currentTemperature_Key, currentTemperature);
        editor.commit();

    }


    public static String loadUserLocation(Context context){
        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_userLocation_Key, "Dhaka");
    }

    public static void saveUserLocation(Context context, String userLocation){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_userLocation_Key, userLocation);
        editor.apply();

    }

}