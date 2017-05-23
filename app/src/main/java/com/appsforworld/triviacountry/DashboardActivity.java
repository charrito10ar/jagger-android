package com.appsforworld.triviacountry;

import android.animation.ValueAnimator;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appsforworld.triviacountry.dao.QuestionDAO;
import com.appsforworld.triviacountry.model.Question;
import com.appsforworld.triviacountry.overwrite.PrimaryTextView;
import com.appsforworld.triviacountry.overwrite.SecondaryLightTextView;
import com.appsforworld.triviacountry.overwrite.SecondaryMediumTextView;
import com.appsforworld.triviacountry.utilities.Constants;
import com.appsforworld.triviacountry.utilities.Stats;
import com.appsforworld.triviacountry.utilities.Utilities;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DecimalFormat;

public class DashboardActivity extends AppCompatActivity {

    private CircularProgressBar totalCircularProgressBar;
    private CircularProgressBar correctCircularProgressBar;
    private CircularProgressBar incorrectCircularProgressBar;
    private TextView totalProgressText;
    private TextView correctProgressText;
    private TextView incorrectProgressText;
    private float totalActualProgress;
    private float incorrectActualProgress;
    private float correctActualProgress;

    private TextView userLevel;
    private TextView userLevelAndDeltaPoints;
    private ProgressBar levelProgressBar;
    private QuestionDAO questionDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DashboardActivity", "onCreate");

        setContentView(R.layout.activity_dashboard);
        this.totalCircularProgressBar = (CircularProgressBar) findViewById(R.id.total_progress_circular);
        this.correctCircularProgressBar = (CircularProgressBar) findViewById(R.id.correct_progress_circular);
        this.incorrectCircularProgressBar = (CircularProgressBar) findViewById(R.id.incorrect_progress_circular);

        this.totalProgressText = (PrimaryTextView) findViewById(R.id.total_progress_text);
        this.correctProgressText = (PrimaryTextView) findViewById(R.id.correct_progress_text);
        this.incorrectProgressText = (PrimaryTextView) findViewById(R.id.incorrect_progress_text);

        this.userLevel = (SecondaryMediumTextView) findViewById(R.id.dashboard_level_text);
        this.userLevelAndDeltaPoints = (SecondaryLightTextView) findViewById(R.id.dashboard_points_text);

        this.levelProgressBar = (ProgressBar) findViewById(R.id.dashboard_progress_bar_level);

        this.loadAds();

        this.questionDAO = new QuestionDAO(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        boolean loadDB = prefs.getBoolean("loadDB", false);
        if(!loadDB) {
            this.loadLocalQuestion();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("loadDB", true);
            editor.commit();
        }else {
            if(questionDAO.getNotViewCount() < 100){
                TaskGetMoreQuestions taskGetMoreQuestions = new TaskGetMoreQuestions();
                taskGetMoreQuestions.execute();
            }
        }
    }

    private void loadLocalQuestion(){

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
                    if (language.equals(getResources().getString(R.string.language_code))) {
                        question.setText(jsonObjectTranslation.getString("text"));
                        question.setResponses(jsonObjectTranslation.getString("answers"));
                    }
                }
                question.setCountry(getResources().getString(R.string.country_code));
                question.setView(0);
                questionDAO.insertRow(question);
                Log.d("Insert", "" + i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Stats.incrementTimesAskQuestion(getApplicationContext());
    }

    private void loadAds() {
        MobileAds.initialize(getApplicationContext(), getResources().getString(R.string.admob_app_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
                addTestDevice("2843E996E3E48320E27B16741947CF56").
                build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onResume(){
        super.onResume();
        this.initProgress();
//        if(Utilities.getBooleanPreference(getApplicationContext(), "QUITAR-VIDA")){
//            Log.d("Quitar Vida", "TRUE");
//            Toast.makeText(getApplicationContext(), "Perdon pero te tuvimos que quitar una vida", Toast.LENGTH_SHORT).show();
//            Utilities.saveBooleanPreference(getApplicationContext(), "QUITAR-VIDA", false);
//        }
        this.logStats();
    }

    private void logStats(){
        Log.d("ART CORRECT %", String.valueOf(Stats.getPercentCorrectCategory(getApplicationContext(), Constants.ART_CATEGORY) * 100));
        Log.d("ENT CORRECT %", String.valueOf(Stats.getPercentCorrectCategory(getApplicationContext(), Constants.ENT_CATEGORY) * 100));
        Log.d("GEO CORRECT %", String.valueOf(Stats.getPercentCorrectCategory(getApplicationContext(), Constants.GEO_CATEGORY) * 100));
        Log.d("HIS CORRECT %", String.valueOf(Stats.getPercentCorrectCategory(getApplicationContext(), Constants.HIS_CATEGORY) * 100));
        Log.d("SCI CORRECT %", String.valueOf(Stats.getPercentCorrectCategory(getApplicationContext(), Constants.SCI_CATEGORY) * 100));
        Log.d("SPO CORRECT %", String.valueOf(Stats.getPercentCorrectCategory(getApplicationContext(), Constants.SPO_CATEGORY) * 100));

        Log.d("ART INCORRECT %", String.valueOf(Stats.getPercentIncorrectCategory(getApplicationContext(), Constants.ART_CATEGORY) * 100));
        Log.d("ENT INCORRECT %", String.valueOf(Stats.getPercentIncorrectCategory(getApplicationContext(), Constants.ENT_CATEGORY) * 100));
        Log.d("GEO INCORRECT %", String.valueOf(Stats.getPercentIncorrectCategory(getApplicationContext(), Constants.GEO_CATEGORY) * 100));
        Log.d("HIS INCORRECT %", String.valueOf(Stats.getPercentIncorrectCategory(getApplicationContext(), Constants.HIS_CATEGORY) * 100));
        Log.d("SCI INCORRECT %", String.valueOf(Stats.getPercentIncorrectCategory(getApplicationContext(), Constants.SCI_CATEGORY) * 100));
        Log.d("SPO INCORRECT %", String.valueOf(Stats.getPercentIncorrectCategory(getApplicationContext(), Constants.SPO_CATEGORY) * 100));

        Log.d("ART CORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_ART_CORRECT)));
        Log.d("ENT CORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_ENT_CORRECT)));
        Log.d("GEO CORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_GEO_CORRECT)));
        Log.d("HIS CORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_HIS_CORRECT)));
        Log.d("SCI CORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_SCI_CORRECT)));
        Log.d("SPO CORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_SPO_CORRECT)));

        Log.d("ART INCORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_ART_INCORRECT)));
        Log.d("ENT INCORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_ENT_INCORRECT)));
        Log.d("GEO INCORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_GEO_INCORRECT)));
        Log.d("HIS INCORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_HIS_INCORRECT)));
        Log.d("SCI INCORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_SCI_INCORRECT)));
        Log.d("SPO INCORRECT #", String.valueOf(Utilities.getIntPreference(getApplicationContext(), Constants.STAT_SPO_INCORRECT)));

        Log.d("TIME AVG ANSWER", String.valueOf(Stats.getAVGTimeAnswer(getApplicationContext())));
        Log.d("TOTAL QUESTIONS", String.valueOf(Stats.getTotalQuestion(getApplicationContext())));
    }

    private void initProgress(){
        this.initTotalProgress();
        this.initCorrectProgress();
        this.initIncorrectProgress();
        this.initStats();
    }

    private void initStats() {
        int userLevel = Stats.getUserLevel(getApplicationContext());
        this.userLevel.setText("LEVEL: " +  userLevel);
        int levelPoints = Stats.getLevelPoints(getApplicationContext());
        int deltaLevel = Stats.getDeltaLevel(getApplicationContext());
        this.levelProgressBar.setMax(deltaLevel);
        this.userLevelAndDeltaPoints.setText(getResources().getString(R.string.dashboard_points, levelPoints, deltaLevel));
        this.levelProgressBar.setProgress(levelPoints);
    }

    private void initTotalProgress(){
        this.totalActualProgress = Stats.getPercentAnswered(getApplicationContext());

        if(this.totalActualProgress > 0){
            this.totalCircularProgressBar.setProgress(0); // or before totalCircularProgressBar
            this.totalCircularProgressBar.setProgressWithAnimation((float) totalActualProgress, Constants.ANIMATION_DURATION_PROGRESS);

            this.animateFloatNumberTextView(0, this.totalActualProgress, totalProgressText);
        }
    }

    private void initIncorrectProgress() {
        this.incorrectActualProgress = Stats.getPercentIncorrectAnswered(getApplicationContext());
        if(this.incorrectActualProgress > 0){
            this.incorrectCircularProgressBar.setProgress(0); // or before totalCircularProgressBar
            this.incorrectCircularProgressBar.setProgressWithAnimation((float)incorrectActualProgress, Constants.ANIMATION_DURATION_PROGRESS);
            this.animateIntNumberTextView(0, this.incorrectActualProgress, incorrectProgressText);
        }
    }

    private void initCorrectProgress() {
        this.correctActualProgress = Stats.getPercentCorrectAnswered(getApplicationContext());
        if(this.correctActualProgress > 0){
            this.correctCircularProgressBar.setProgress(0); // or before totalCircularProgressBar
            this.correctCircularProgressBar.setProgressWithAnimation((float) correctActualProgress, Constants.ANIMATION_DURATION_PROGRESS);
            this.animateIntNumberTextView(0, this.correctActualProgress, correctProgressText);
        }
    }

    public void play(View view){
        Intent intent = new Intent(getApplicationContext(), SpinActivity.class);
        startActivity(intent);
    }

    private void animateFloatNumberTextView(float initialValue, float finalValue, final TextView  textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(Constants.ANIMATION_DURATION_PROGRESS);
        final DecimalFormat decimalFormat = new DecimalFormat("#.#");
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(decimalFormat.format(valueAnimator.getAnimatedValue()) + "%");
            }
        });
        valueAnimator.start();
    }

    private void animateIntNumberTextView(float initialValue, float finalValue, final TextView  textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(Constants.ANIMATION_DURATION_PROGRESS);
        final DecimalFormat decimalFormat = new DecimalFormat("#");
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(decimalFormat.format(valueAnimator.getAnimatedValue()) + "%");
            }
        });
        valueAnimator.start();
    }


    private class TaskGetMoreQuestions extends AsyncTask<Void, Void, String> {
        private String state = "ok";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection connection;

            String responseApi = new String();

            try {
                String stringURL = "https://trivia-uk.firebaseapp.com/" + getResources().getString(R.string.country_code) + "/list_" + Stats.getTimesAskQuestions(getApplicationContext()) + ".json";
                URL url = new URL(stringURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setReadTimeout(Constants.INT_TIME_OUT);
                connection.setConnectTimeout(Constants.INT_TIME_OUT);
                connection.setRequestMethod("POST");

                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.flush();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    responseApi = responseApi + line;
                }
                in.close();
                out.close();

            } catch (SocketTimeoutException e){
                e.printStackTrace();
                state = "time_out";

            } catch (IOException e) {
                e.printStackTrace();
                state = "error";
            }

            return responseApi;
        }

        @Override
        protected void onPostExecute(String param) {
            super.onPostExecute(param);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(param);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (state.equals("ok")) {
                QuestionDAO questionDAO = new QuestionDAO(getApplicationContext());
                try {
                    assert jsonObject != null;

                    JSONArray jsonArrayQuestion = jsonObject.getJSONArray("list");
                    for(int i=0; i< jsonArrayQuestion.length(); i++){
                        JSONObject jsonObjectQuestion = (JSONObject) jsonArrayQuestion.get(i);
                        Question question = new Question();
                        question.setCategory(jsonObjectQuestion.getString("category"));
                        question.setType(jsonObjectQuestion.getString("type"));
                        question.setCorrect(jsonObjectQuestion.getInt("correct_answer"));

                        JSONArray jsonArrayTranslations = jsonObjectQuestion.getJSONArray("translations");
                        for (int j = 0; j < jsonArrayTranslations.length(); j++) {
                            JSONObject jsonObjectTranslation = (JSONObject) jsonArrayTranslations.get(j);
                            String language = jsonObjectTranslation.getString("language");
                            if (language.equals("DA")) {
                                question.setText(jsonObjectTranslation.getString("text"));
                                question.setResponses(jsonObjectTranslation.getString("answers"));
                            }
                        }
                        question.setCountry("DK");
                        question.setView(0);
                        questionDAO.insertRow(question);
                        Log.d("Insert", "" + i);
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
                Stats.incrementTimesAskQuestion(getApplicationContext());
                questionDAO.deleteAllView();
               // makeNotification();
            } else {
                if(state.equals("error")){

                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();

                }else{
                    if(state.equals("time_out")){
                        Toast.makeText(getApplicationContext(), "TIME OUT", Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplicationContext(), "ERROR DESCONOCIDO", Toast.LENGTH_LONG).show();
                    }

                }

            }
        }
    }

    private void makeNotification(){
        android.support.v4.app.NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_spin)
                        .setContentTitle("¡Nuevas preguntas!")
                        .setContentText("Se han agregado 250 nuevas!");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, DashboardActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(DashboardActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());
    }
}
