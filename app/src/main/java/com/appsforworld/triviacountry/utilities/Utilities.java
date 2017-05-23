package com.appsforworld.triviacountry.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.appsforworld.triviacountry.R;
import com.appsforworld.triviacountry.model.Question;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcelo on 17/9/16.
 */
public class Utilities {


    public static void saveStringPreference(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = SP.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.commit();
    }

    public static String getStringPreference(Context context, String preferenceName) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        return SP.getString(preferenceName, "null");
    }

    public static void saveIntPreference(Context context, String preferenceName, int preferenceValue) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = SP.edit();
        editor.putInt(preferenceName, preferenceValue);
        editor.commit();
    }

    public static int getIntPreference(Context context, String preferenceName) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        return SP.getInt(preferenceName, 0);
    }

    public static void saveBooleanPreference(Context context, String preferenceName, boolean b) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = SP.edit();
        editor.putBoolean(preferenceName, b);
        editor.commit();
    }

    public static boolean getBooleanPreference(Context context, String preferenceName) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        return SP.getBoolean(preferenceName, false);
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.list_1);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getCategoryString(int category){

        switch (category){
            case Constants.ART_CATEGORY_MACHINE:
                return Constants.ART_CATEGORY;
            case Constants.ENT_CATEGORY_MACHINE:
                return Constants.ENT_CATEGORY;
            case Constants.GEO_CATEGORY_MACHINE:
                return Constants.GEO_CATEGORY;
            case Constants.HIS_CATEGORY_MACHINE:
                return Constants.HIS_CATEGORY;
            case Constants.SCI_CATEGORY_MACHINE:
                return Constants.SCI_CATEGORY;
            default:
                return Constants.SPO_CATEGORY;
        }
    }
}
