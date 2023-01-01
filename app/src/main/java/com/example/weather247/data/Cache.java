package com.example.weather247.data;
import android.content.Context;
import android.content.SharedPreferences;


public class Cache {

    private static final String My_Preference_Name = "com.example.weather247";
    private static final String Pref_userLocation_Key = "pref_userLocation_key";
    private static final String Pref_RecentlySearchedLocalTime_Key = "pref_recentlySearchedLocalTime_key";

    //RecentlySearchedLocalTime
    public static String loadRecentlySearchedLocalTime(Context context){
        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_RecentlySearchedLocalTime_Key, "00:00");
    }

    public static void saveRecentlySearchedLocalTime(Context context, String recentlySearchedLocalTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_RecentlySearchedLocalTime_Key, recentlySearchedLocalTime);
        editor.apply();

    }

    //userlocation
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