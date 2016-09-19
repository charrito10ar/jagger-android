package com.appsforworld.triviacountry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.appsforworld.triviacountry.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        try {
            JSONObject obj = new JSONObject(Utilities.loadJSONFromAsset(getApplicationContext()));
            JSONArray m_jArry = obj.getJSONArray("list");


            for (int i = 0; i < m_jArry.length(); i++) {

                Log.d("Details-->", m_jArry.get(i).toString());
                Log.d("Number-->", " " + i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
