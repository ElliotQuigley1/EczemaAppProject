package com.example.calendarattempt4;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class SaveSharedPreference {

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    // Sets parent account username to system file
    public static void setUserName(Context context, String username) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("username", username);
        editor.commit();
    }

    // Sets parent account password to system file
    public static String getUserName(Context context) {
        return getSharedPreferences(context).getString("username", "");
    }

    // Gets parent account username on system file
    public static void setPassword(Context context, String password) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("password", password);
        editor.commit();
    }

    // Gets parent account password on system file
    public static String getPassword(Context context) {
        return getSharedPreferences(context).getString("password", "");
    }
}