package com.example.weather247.data;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.weather247.ui.cards.locationcard.LocationCardModel;

import java.util.ArrayList;


public class Cache {

    private static final String My_Preference_Name = "com.example.weather247";
    private static final String Pref_userLocation_Key = "pref_userLocation_key";

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