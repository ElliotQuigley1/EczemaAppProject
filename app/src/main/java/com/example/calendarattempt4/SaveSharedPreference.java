package com.example.calendarattempt4;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SaveSharedPreference {

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserName(Context context, String username)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("username", username);
        editor.commit();
    }

    public static String getUserName(Context context)
    {
        return getSharedPreferences(context).getString("username", "");
    }

    public static void setPassword(Context context, String password)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("password", password);
        editor.commit();
    }

    public static String getPassword(Context context)
    {
        return getSharedPreferences(context).getString("password", "");
    }
}