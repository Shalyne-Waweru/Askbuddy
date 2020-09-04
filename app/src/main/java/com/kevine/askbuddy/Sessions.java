package com.kevine.askbuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.kevine.askbuddy.login.LoginActivity;


import java.util.ArrayList;
import java.util.List;

public class Sessions {
    public static SharedPreferences.Editor editor;
    // Shared Preferences
    public SharedPreferences pref;
    public String isLogin;

    private static final String IS_LOGIN = "IsLoggedIn";
    // Context
    Context _context;


    public Sessions(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences("rongaiprefs", Context.MODE_PRIVATE);
        editor = pref.edit();
        isLogin = getUserId();
    }

    public static void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    /**
     * @param key
     * @param value
     */
    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setStringArray(String key, List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(",");
        }
        editor.putString(key, sb.toString());
        editor.commit();
    }

    public List<String> getStringArray(String key) {
        List<String> list = new ArrayList<>();
        String[] array = getString(key).split(",");
        int k = array.length;
        for (int i = 0; i < k; i++) {
            list.add(array[i]);
        }
        return list;
    }

    public void setLoginStatus() {
        editor.putBoolean(Constants.KEY_LOGIN_STATUS, true);
    }

    public String getString(String key) {
        return pref.getString(key, null);
    }

    public void setInt(String key, int i) {
        editor.putInt(key, i);
        editor.commit();
    }

    public int getInt(String key) {
        return pref.getInt(key, 0);

    }

    public void clearData(String key) {
        editor.remove(key);
    }


    //set userid
    public void setUserId(String id) {
        setString(Constants.KEY_USERID, id);
    }

    //get userid
    public String getUserId() {
        return getString(Constants.KEY_USERID);
    }


    public void createLoginSession(){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // commit changes
        editor.commit();
    }
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }else {
            Intent x =new Intent(_context,MainActivity.class);
            _context.startActivity(x);
        }

    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }


}
