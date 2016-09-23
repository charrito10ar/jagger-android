package com.appsforworld.triviacountry;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appsforworld.triviacountry.dao.QuestionDAO;
import com.appsforworld.triviacountry.model.Question;
import com.appsforworld.triviacountry.utilities.Constants;
import com.appsforworld.triviacountry.utilities.Utilities;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    private CircularProgressBar totalCircularProgressBar;
    private CircularProgressBar correctCircularProgressBar;
    private CircularProgressBar incorrectCircularProgressBar;
    private TextView totalProgressText;
    private TextView correctProgressText;
    private TextView incorrectProgressText;
    private int actualProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.totalCircularProgressBar = (CircularProgressBar) findViewById(R.id.progress);
        this.totalProgressText = (TextView) findViewById(R.id.progress_text);

        QuestionDAO questionDAO = new QuestionDAO(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);


        boolean loadDB = prefs.getBoolean("loadDB", false);
        if(!loadDB) {
            try {
                JSONObject obj = new JSONObject(Utilities.loadJSONFromAsset(getApplicationContext()));
                JSONArray m_jArry = obj.getJSONArray("list");


                for (int i = 0; i < m_jArry.length(); i++) {
                    JSONObject jsonObjectQuestion = m_jArry.getJSONObject(i);

                    Question question = new Question();
                    question.setCategory(jsonObjectQuestion.getString("category"));
                    question.setType(jsonObjectQuestion.getString("type"));
                    question.setCorrect(jsonObjectQuestion.getInt("correct_answer"));

                    JSONArray jsonArrayTranslations = jsonObjectQuestion.getJSONArray("translations");
                    for (int j = 0; j < jsonArrayTranslations.length(); j++) {
                        JSONObject jsonObjectTranslation = (JSONObject) jsonArrayTranslations.get(j);
                        String language = jsonObjectTranslation.getString("language");
                        if (language.equals("ES")) {
                            question.setText(jsonObjectTranslation.getString("text"));
                            question.setResponses(jsonObjectTranslation.getString("answers"));
                        }
                    }
                    question.setCountry("AR");
                    question.setView(0);
                    questionDAO.insertRow(question);
                    Log.d("Insert", "" + i);

                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("loadDB", true);
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        this.initProgress();

    }

    private void initProgress(){
        this.actualProgress = 50;
        long refreshInterval = Constants.ANIMATION_DURATION_PROGRESS / this.actualProgress;

        this.totalCircularProgressBar.setProgress(0); // or before totalCircularProgressBar
        this.totalCircularProgressBar.setProgressWithAnimation(actualProgress, Constants.ANIMATION_DURATION_PROGRESS);

        new CountDownTimer(Constants.ANIMATION_DURATION_PROGRESS, refreshInterval) {
            int partialProgress = 0;
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("CountDown", "onTick: " + partialProgress);
                partialProgress = partialProgress + 1;
                totalProgressText.setText(partialProgress + "%");
            }

            @Override
            public void onFinish() {
                totalProgressText.setText(actualProgress + "%");
                Log.d("CountDown", "onFinish: " + actualProgress);
            }
        }.start();
    }

    public void play(View view){
        Intent intent = new Intent(getApplicationContext(), SpinActivity.class);
        startActivity(intent);

    }

}
