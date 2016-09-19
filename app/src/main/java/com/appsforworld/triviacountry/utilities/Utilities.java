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
}
