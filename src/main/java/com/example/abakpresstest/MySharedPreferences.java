package com.example.abakpresstest;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String DATA_DOWNLOADED = "dataDownloaded";
    public static final boolean DEFAULT_DATA_DOWNLOADED = false;
    private static final String PREFS_NAME = "coma.example.abakpresstest.Prefs";

    public static boolean isDataDownloaded(Context context){
        return context.getSharedPreferences(PREFS_NAME, 0).getBoolean(DATA_DOWNLOADED, DEFAULT_DATA_DOWNLOADED);
    }

    public static void setDataDownloaded(Context context, boolean value){
        SharedPreferences.Editor ed = context.getSharedPreferences(PREFS_NAME, 0).edit();
        ed.putBoolean(DATA_DOWNLOADED, value);
        ed.commit();
    }
}
