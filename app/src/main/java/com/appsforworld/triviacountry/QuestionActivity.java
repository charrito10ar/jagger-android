package com.appsforworld.triviacountry;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsforworld.triviacountry.model.Question;
import com.appsforworld.triviacountry.overwrite.PrimaryTextView;
import com.appsforworld.triviacountry.overwrite.SecondaryLightTextView;
import com.appsforworld.triviacountry.overwrite.SecondaryRegularTextView;
import com.appsforworld.triviacountry.overwrite.StrokeTextView;
import com.appsforworld.triviacountry.utilities.Constants;
import com.appsforworld.triviacountry.utilities.Stats;
import com.appsforworld.triviacountry.utilities.Utilities;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;

public class QuestionActivity extends AppCompatActivity {
    private RelativeLayout toolbar;
    private TextView categoryName;
    private Question question;
    private ImageView imageCategory;

    private TextView questionText;

    private TextView answerOne;
    private TextView answerTwo;
    private TextView answerThree;
    private TextView answerFour;

    private CardView cardAnswerOne;
    private CardView cardAnswerTwo;
    private CardView cardAnswerThree;
    private CardView cardAnswerFour;

    private TextView timeRemainingText;
    private int pointsToPlay;
    private int pointsToTime;
    private int totalPoints;
    private long timeRemaining;
    private boolean jackpot;
    private boolean answerWin;
    private CountDownTimer countDownTimer;

    private RelativeLayout layoutResumeAndContinue;
    private TextView resumePoints;
    private TextView resumeBonusPoints;
    private TextView resumeTimePoints;

    private TextView resultTextView;
    private TextView resumeTotalPoints;

    private TextView resumeLevelPoints;
    private ProgressBar progressBarLevel;

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        this.toolbar = (RelativeLayout) findViewById(R.id.question_toolbar);
        this.categoryName = (PrimaryTextView) findViewById(R.id.category_name);
        this.imageCategory = (ImageView) findViewById(R.id.ic_category_question);
        this.question = getIntent().getParcelableExtra("QUESTION");
        this.pointsToPlay = getIntent().getIntExtra("POINTS-TO-PLAY", 0);
        this.jackpot = getIntent().getBooleanExtra("JACKPOT", false);

        this.questionText = (TextView) findViewById(R.id.question_text);
        this.answerOne = (TextView) findViewById(R.id.answer_one);
        this.answerTwo = (TextView) findViewById(R.id.answer_two);
        this.answerThree = (TextView) findViewById(R.id.answer_three);
        this.answerFour = (TextView) findViewById(R.id.answer_four);

        this.cardAnswerOne = (CardView) findViewById(R.id.card_answer_one);
        this.cardAnswerTwo = (CardView) findViewById(R.id.card_answer_two);
        this.cardAnswerThree = (CardView) findViewById(R.id.card_answer_three);
        this.cardAnswerFour = (CardView) findViewById(R.id.card_answer_four);

        this.timeRemainingText = (PrimaryTextView) findViewById(R.id.time_to_answer_text);
        this.resultTextView = (StrokeTextView) findViewById(R.id.question_result);

        this.layoutResumeAndContinue = (RelativeLayout) findViewById(R.id.layout_resume_and_continue);
        this.resumePoints = (SecondaryLightTextView) findViewById(R.id.text_answer_points);
        this.resumeTimePoints = (SecondaryLightTextView) findViewById(R.id.text_time_points);
        this.resumeBonusPoints = (SecondaryLightTextView) findViewById(R.id.text_bonus_points);

        this.resumeTotalPoints = (StrokeTextView) findViewById(R.id.text_total_points);
        this.progressBarLevel = (ProgressBar) findViewById(R.id.resume_progress_bar_level);

        this.resumeLevelPoints = (SecondaryRegularTextView) findViewById(R.id.resume_points_text);

        this.setQuestion();
        this.setLookAndFeel();
        this.initCountDownTimer();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.question_intertitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                continueGame();
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("2843E996E3E48320E27B16741947CF56")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void initCountDownTimer() {
        this.timeRemainingText.setText("20''");
        this.timeRemaining = 20;
        this.countDownTimer = new CountDownTimer(Constants.TIME_TO_ANSWER, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished/1000;
                timeRemainingText.setText(timeRemaining + "''");
            }

            @Override
            public void onFinish() {
                timeOut();
                timeRemainingText.setText(0 + "''");
            }
        };
        this.countDownTimer.start();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.answerWin = false;
        this.pointsToTime = 0;
        this.totalPoints = 0;
    }

    private void timeOut() {
        this.setClickableOff();
        this.resultTextView.setText(getResources().getString(R.string.time_out_result_text));
        this.resultTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorHis));
        this.resultTextView.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.Landing)
                .duration(800)
                .playOn(this.resultTextView);
        this.answerWin = false;
        this.showCorrectAnswer();
        this.showResumeAndContinue();
    }

    private void setQuestion() {
        this.questionText.setText(this.question.getText());
        try {
            JSONArray jsonArrayAnswers = new JSONArray(this.question.getResponses());
            this.answerOne.setText(jsonArrayAnswers.getString(0));
            this.answerTwo.setText(jsonArrayAnswers.getString(1));
            this.answerThree.setText(jsonArrayAnswers.getString(2));
            this.answerFour.setText(jsonArrayAnswers.getString(3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setLookAndFeel() {
        switch (question.getCategory()){
            case Constants.ART_CATEGORY:
                this.categoryName.setText(getResources().getString(R.string.category_name_art));
                this.imageCategory.setImageResource(R.drawable.art);
                this.toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
                this.setColorStatusBar(R.color.colorArt);
                break;
            case Constants.ENT_CATEGORY:
                this.categoryName.setText(getResources().getString(R.string.category_name_ent));
                this.imageCategory.setImageResource(R.drawable.entertainament);
                this.toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEnt));
                this.setColorStatusBar(R.color.colorEnt);
                break;
            case Constants.GEO_CATEGORY:
                this.categoryName.setText(getResources().getString(R.string.category_name_geo));
                this.imageCategory.setImageResource(R.drawable.geography);
                this.toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGeo));
                this.setColorStatusBar(R.color.colorGeo);
                break;
            case Constants.HIS_CATEGORY:
                this.categoryName.setText(getResources().getString(R.string.category_name_his));
                this.imageCategory.setImageResource(R.drawable.history);
                this.toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorHis));
                this.setColorStatusBar(R.color.colorHis);
                break;
            case Constants.SCI_CATEGORY:
                this.categoryName.setText(getResources().getString(R.string.category_name_sci));
                this.imageCategory.setImageResource(R.drawable.science);
                this.toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                this.setColorStatusBar(R.color.colorSci);
                break;
            case Constants.SPO_CATEGORY:
                this.categoryName.setText(getResources().getString(R.string.category_name_spo));
                this.imageCategory.setImageResource(R.drawable.sport);
                this.toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSpo));
                this.setColorStatusBar(R.color.colorSpo);
        }
    }

    public void setColorStatusBar(int colorID){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), colorID));
        }
    }

    public void clickAnswer(View view){
        switch (view.getId()){
            case R.id.card_answer_one:
                this.processAnswerSelect(this.cardAnswerOne, this.answerOne, 0);
                break;
            case R.id.card_answer_two:
               this.processAnswerSelect(this.cardAnswerTwo, this.answerTwo, 1);
                break;
            case R.id.card_answer_three:
                this.processAnswerSelect(this.cardAnswerThree, this.answerThree, 2);
                break;
            case R.id.card_answer_four:
                this.processAnswerSelect(this.cardAnswerFour, this.answerFour, 3);
        }
    }

    private void processAnswerSelect(CardView cardView, TextView textView, int answeredSelect) {
        this.countDownTimer.cancel();
        this.setClickableOff();
        if (this.question.getCorrect() == answeredSelect){
            this.answerWin = true;
            this.resultTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
            this.resultTextView.setText(getResources().getString(R.string.correct_result_text));
            cardView.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
            textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            this.calculatePointsToTime();
            Stats.incrementCorrectAnswered(getApplicationContext(), question);
            Stats.incrementPoints(getApplicationContext(), this.pointsToPlay);
            Stats.incrementPoints(getApplicationContext(), this.pointsToTime);
        }else {
            answerWin = false;
            this.resultTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
            this.resultTextView.setText(getResources().getString(R.string.incorrect_result_text));
            cardView.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
            textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            Stats.incrementIncorrectAnswered(getApplicationContext(), question);
            this.showCorrectAnswer();
        }

        this.showResultTextInQuestionCard();
        this.showResumeAndContinue();
    }

    private void showResultTextInQuestionCard(){
        new CountDownTimer(1800, 1600) {
            @Override
            public void onTick(long l) {
                resultTextView.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Landing)
                        .duration(800)
                        .playOn(resultTextView);
            }

            @Override
            public void onFinish() {
                YoYo.with(Techniques.TakingOff)
                        .duration(800)
                        .playOn(resultTextView);
            }
        }.start();
    }

    private void calculatePointsToTime() {
        if(this.timeRemaining >= 16){
            this.pointsToTime = 5;
        }else {
            if(this.timeRemaining >= 12){
                this.pointsToTime = 4;
            }else {
                if(this.timeRemaining >= 8){
                    this.pointsToTime = 3;
                }else {
                    if(this.timeRemaining >= 4){
                        this.pointsToTime = 2;
                    }else {
                        this.pointsToTime = 1;
                    }
                }
            }
        }
    }

    private void showResumeAndContinue() {
        int bonusToCorrectAnswer = 0;
        int bonusToTime = 0;
        int bonusPoints = 0;
        Utilities.saveBooleanPreference(getApplicationContext(), "QUITAR-VIDA", false);
        Stats.addTimeAnswer(getApplicationContext(), this.timeRemaining);

        if(this.answerWin){
            this.resumePoints.setText(getResources().getString(R.string.points_amount_text, pointsToPlay));
            this.resumeTimePoints.setText(getResources().getString(R.string.points_amount_text, pointsToTime));
            bonusToCorrectAnswer = pointsToPlay;
            bonusToTime = pointsToTime;
        }
        if(this.jackpot){
            bonusPoints = Constants.BONUS_POINTS;
            this.resumeBonusPoints.setText(getResources().getString(R.string.points_amount_text, bonusPoints));
        }

        this.totalPoints = bonusToCorrectAnswer + bonusToTime + bonusPoints;
        this.resumeTotalPoints.setText(getResources().getString(R.string.points_amount_text, this.totalPoints));

        int levelPoints = Stats.getLevelPoints(getApplicationContext());
        int deltaLevel = Stats.getDeltaLevel(getApplicationContext());
        this.progressBarLevel.setMax(deltaLevel);
        this.progressBarLevel.setProgress(levelPoints);
        this.resumeLevelPoints.setText(levelPoints + "/" + deltaLevel);
        this.hideAnswers();
    }

    private void hideAnswers() {
        new CountDownTimer(1400, 200) {
            boolean hasLeft = false;
            @Override
            public void onTick(long millisUntilFinished) {

                if((millisUntilFinished < 600) && !hasLeft) {
                    hasLeft = true;
                    YoYo.with(Techniques.SlideOutLeft)
                            .duration(1000)
                            .playOn(cardAnswerOne);
                    YoYo.with(Techniques.SlideOutRight)
                            .duration(1000)
                            .playOn(cardAnswerTwo);
                    YoYo.with(Techniques.SlideOutLeft)
                            .duration(1000)
                            .playOn(cardAnswerThree);
                    YoYo.with(Techniques.SlideOutRight)
                            .duration(1000)
                            .playOn(cardAnswerFour);
                }
            }

            @Override
            public void onFinish() {

                new CountDownTimer(900, 500) {
                    boolean hasLeft = false;
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(!hasLeft){
                            hasLeft = true;
                            layoutResumeAndContinue.setVisibility(View.VISIBLE);
                            YoYo.with(Techniques.FadeIn)
                                    .duration(600)
                                    .playOn(layoutResumeAndContinue);
                        }
                    }

                    @Override
                    public void onFinish() {
                        resumeTotalPoints.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeOutUp)//FadeOut
                                .duration(1000)
                                .playOn(resumeTotalPoints);
                    }
                }.start();
            }
        }.start();
    }

    private void showCorrectAnswer() {
        switch (question.getCorrect()){
            case 0:
                this.cardAnswerOne.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                this.answerOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                break;
            case 1:
                this.cardAnswerTwo.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                this.answerTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                break;
            case 2:
                this.cardAnswerThree.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                this.answerThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                break;
            case 3:
                this.cardAnswerFour.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                this.answerFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }
    }

    private void setClickableOff() {
        this.cardAnswerOne.setClickable(false);
        this.cardAnswerTwo.setClickable(false);
        this.cardAnswerThree.setClickable(false);
        this.cardAnswerFour.setClickable(false);
    }

    public void clickContinue(View view){
        if (mInterstitialAd.isLoaded() && !this.answerWin) {
            mInterstitialAd.show();
        } else {
            this.continueGame();
        }
    }

    private void continueGame() {
        Intent intent = new Intent(getApplicationContext(), SpinActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        //Toast.makeText(getApplicationContext(), "Tienes que contestar", Toast.LENGTH_SHORT).show();
    }

//    public static void setTaskBarColored(Activity context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//        {
//            Window w = context.getWindow();
//            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //status bar height
//            int statusBarHeight = Utilities.getStatusBarHeight(context);
//
//            View view = new View(context);
//            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            view.getLayoutParams().height = statusBarHeight;
//            ((ViewGroup) w.getDecorView()).addView(view);
//            view.setBackgroundColor(context.getResources().getColor(R.color.colorSpo));
//        }
//    }
}
