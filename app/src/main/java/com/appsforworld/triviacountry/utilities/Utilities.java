package com.appsforworld.triviacountry.utilities;

import android.content.Context;

import com.appsforworld.triviacountry.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcelo on 17/9/16.
 */
public class Utilities {

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.rusia_01);
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
