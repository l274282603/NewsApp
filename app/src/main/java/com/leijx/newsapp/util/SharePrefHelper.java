package com.leijx.newsapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.leijx.newsapp.App;

/**
 * Created by leijx on 2017/11/1.
 */

public class SharePrefHelper {
    private static SharePrefHelper sharePrefHelper;
    private static SharedPreferences sharedPreferences;

    public static SharePrefHelper getInstance(){
        if(sharePrefHelper == null){
            sharePrefHelper = new SharePrefHelper();
            sharedPreferences = App.getsAppContext().getSharedPreferences("config",Context.MODE_PRIVATE);
        }
        return sharePrefHelper;
    }


    public String getString(String key){
        return sharedPreferences.getString(key,"");
    }

    public void putString(String key, String value){
        Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }

    public void putInt(String key, int value){
        Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public boolean getboolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    public void putboolean(String key, boolean value){
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
}
