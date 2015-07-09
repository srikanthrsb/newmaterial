package com.sptuts.newmaterial.utils;

/**
 * Created by SHRI on 7/10/2015.
 */
public class Logger {
    private static final Boolean isDebug = false; // Make it false in Prod

    private Logger(){ }

    public static void d(String TAG, String msg) {
        if(isDebug){
            android.util.Log.d(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if(isDebug){
            android.util.Log.i(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if(isDebug){
            android.util.Log.e(TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if(isDebug){
            android.util.Log.v(TAG, msg);
        }
    }
}