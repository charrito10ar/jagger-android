package com.appsforworld.triviacountry;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsforworld.triviacountry.dao.QuestionDAO;
import com.appsforworld.triviacountry.model.Question;
import com.appsforworld.triviacountry.overwrite.PrimaryBubbleTextView;
import com.appsforworld.triviacountry.overwrite.SecondaryBoldTextView;
import com.appsforworld.triviacountry.overwrite.SecondaryLightTextView;
import com.appsforworld.triviacountry.overwrite.StrokeTextView;
import com.appsforworld.triviacountry.overwrite.PrimaryTextView;
import com.appsforworld.triviacountry.sound.SoundCompletion;
import com.appsforworld.triviacountry.utilities.Constants;
import com.appsforworld.triviacountry.utilities.Stats;
import com.appsforworld.triviacountry.utilities.Utilities;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import java.util.Random;

public class SpinActivity extends AppCompatActivity {

    private CardView firstSpinLayout;
    private CardView secondSpinLayout;
    private CardView thirdSpinLayout;

    private ImageView firstIc;
    private ImageView secondIc;
    private ImageView thirdIc;

    private int resultFirstSpin;
    private int resultSecondSpin;
    private int resultThirdSpin;
    private QuestionDAO questionDAO;

    private int pointsToPlay;
    private boolean jackpot;

    private boolean wasShot;
    private TextView levelText;
    private TextView titleSpin;
    private TextView subtitleSpin;
    private TextView bonusText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initMachine();
        setContentView(R.layout.activity_spin);

        this.firstSpinLayout = (CardView) findViewById(R.id.firstSpin);
        this.secondSpinLayout = (CardView) findViewById(R.id.secondSpin);
        this.thirdSpinLayout = (CardView) findViewById(R.id.thirdSpin);
        this.firstIc = (ImageView) findViewById(R.id.firstSpin_ic);
        this.secondIc = (ImageView) findViewById(R.id.secondSpin_ic);
        this.thirdIc = (ImageView) findViewById(R.id.thirdSpin_ic);

        this.levelText = (PrimaryTextView) findViewById(R.id.spin_activity_level);
        this.titleSpin = (PrimaryTextView) findViewById(R.id.spin_activity_title);
        this.subtitleSpin = (SecondaryBoldTextView) findViewById(R.id.spin_activity_subtitle);
        this.bonusText = (StrokeTextView) findViewById(R.id.bonus_text);

        this.questionDAO = new QuestionDAO(getApplicationContext());

        this.loadAds();
        this.initMachine();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.levelText.setText(getResources().getString(R.string.spin_activity_level, Stats.getUserLevel(getApplicationContext())));
    }

    private void loadAds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9789712484984548~2569383719");
        AdView mAdView = (AdView) findViewById(R.id.adViewSpin);
        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
                addTestDevice("2843E996E3E48320E27B16741947CF56").
                build();
        mAdView.loadAd(adRequest);
    }


    private void initMachine() {
        this.wasShot = false;
        this.jackpot = false;
    }

    public void spin(View view){
        if(!this.wasShot){

            final MediaPlayer mp = MediaPlayer.create(this, R.raw.stop_spin);
            mp.setOnCompletionListener(new SoundCompletion());
            mp.start();


            this.blockClickSpin();
            this.titleSpin.setText(getResources().getString(R.string.spin_activity_title_shoting));
            YoYo.with(Techniques.Flash)
                    .duration(Constants.TIME_SPIN)
                    .playOn(titleSpin);

            new CountDownTimer(Constants.TIME_SPIN, 100) {
                public void onFinish() {
                    Log.d("SPIN 1", "FIN");
                    final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.stop_spin_02);
                    mp.setOnCompletionListener(new SoundCompletion());
                    mp.start();

                }

                public void onTick(long millisUntilFinished) {

                    Random r = new Random();
                    int randomNumber = r.nextInt(6);
                    Log.d("SPIN 1", "COlor: " + randomNumber);
                    resultFirstSpin = randomNumber;
                    switch (randomNumber) {
                        case 0:
                            firstSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
                            firstIc.setImageResource(R.drawable.art);
                            break;
                        case 1:
                            firstSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEnt));
                            firstIc.setImageResource(R.drawable.entertainament);
                            break;
                        case 2:
                            firstSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGeo));
                            firstIc.setImageResource(R.drawable.geography);
                            break;
                        case 3:
                            firstSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorHis));
                            firstIc.setImageResource(R.drawable.history);
                            break;
                        case 4:
                            firstSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                            firstIc.setImageResource(R.drawable.science);
                            break;
                        case 5:
                            firstSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSpo));
                            firstIc.setImageResource(R.drawable.sport);
                    }
                }

            }.start();


            new CountDownTimer(Constants.TIME_SPIN + 300, 100) {
                public void onFinish() {
                    Log.d("SPIN 2", "FIN");
                    final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.stop_spin_02);
                    mp.setOnCompletionListener(new SoundCompletion());
                    mp.start();
                }

                public void onTick(long millisUntilFinished) {

                    Random r = new Random();
                    int randomNumber = r.nextInt(6);
                    resultSecondSpin = randomNumber;
                    Log.d("SPIN 2", "COlor: " + randomNumber);
                    switch (randomNumber) {
                        case 0:
                            secondSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
                            secondIc.setImageResource(R.drawable.art);
                            break;
                        case 1:
                            secondSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEnt));
                            secondIc.setImageResource(R.drawable.entertainament);
                            break;
                        case 2:
                            secondSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGeo));
                            secondIc.setImageResource(R.drawable.geography);
                            break;
                        case 3:
                            secondSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorHis));
                            secondIc.setImageResource(R.drawable.history);
                            break;
                        case 4:
                            secondSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                            secondIc.setImageResource(R.drawable.science);
                            break;
                        case 5:
                            secondSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSpo));
                            secondIc.setImageResource(R.drawable.sport);
                    }
                }

            }.start();


            new CountDownTimer(Constants.TIME_SPIN + 600, 100) {
                public void onFinish() {
                    Log.d("SPIN 3", "FIN");
                    final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.stop_spin_02);
                    mp.setOnCompletionListener(new SoundCompletion());
                    mp.start();
                    unblockClickSpin();
                    boolean oneTwo = resultFirstSpin == resultSecondSpin;
                    boolean oneThree = resultFirstSpin == resultThirdSpin;
                    boolean twoThree = resultSecondSpin == resultThirdSpin;

                    if(oneTwo || oneThree || twoThree){
                        if(resultFirstSpin == resultSecondSpin){
                            if(resultSecondSpin == resultThirdSpin){
                                pointsToPlay = 3;
                                jackpot = true;
                                Stats.incrementPoints(getApplicationContext(), Constants.BONUS_POINTS);
                                animateSpinBonus();
                            }else {
                                if (oneTwo){
                                    pointsToPlay = 2;
                                    animateFirstAndSecondSpin();
                                }else {
                                    if (oneThree){
                                        pointsToPlay = 2;
                                       animateFirstAndThirdSpin();
                                    }else {
                                        if (twoThree){
                                            pointsToPlay = 2;
                                            animateSecondAndThirdSpin();
                                        }
                                    }
                                }
                            }
                        }else {
                            if (oneThree){
                                pointsToPlay = 2;
                                animateFirstAndThirdSpin();
                            }else {
                                if (twoThree){
                                    pointsToPlay = 2;
                                    animateSecondAndThirdSpin();
                                }
                            }
                        }
                    }else {
                        pointsToPlay = 1;
                    }

                    titleSpin.setText(getResources().getString(R.string.spin_activity_title_post_shot));
                    subtitleSpin.setText(getResources().getString(R.string.spin_activity_subtitle_post_shot, pointsToPlay));
                    subtitleSpin.setVisibility(View.VISIBLE); //Tada StandUp Landing Flash
                    YoYo.with(Techniques.StandUp)
                            .duration(1200)
                            .playOn(subtitleSpin);
                }

                public void onTick(long millisUntilFinished) {

                    Random r = new Random();
                    int randomNumber = r.nextInt(6);
                    resultThirdSpin = randomNumber;
                    Log.d("SPIN 3", "COlor: " + randomNumber);
                    switch (randomNumber) {
                        case 0:
                            thirdSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
                            thirdIc.setImageResource(R.drawable.art);
                            break;
                        case 1:
                            thirdSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorEnt));
                            thirdIc.setImageResource(R.drawable.entertainament);
                            break;
                        case 2:
                            thirdSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGeo));
                            thirdIc.setImageResource(R.drawable.geography);
                            break;
                        case 3:
                            thirdSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorHis));
                            thirdIc.setImageResource(R.drawable.history);
                            break;
                        case 4:
                            thirdSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                            thirdIc.setImageResource(R.drawable.science);
                            break;
                        case 5:
                            thirdSpinLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSpo));
                            thirdIc.setImageResource(R.drawable.sport);
                    }
                }

            }.start();
            this.wasShot = true;
            Utilities.saveBooleanPreference(getApplicationContext(), "QUITAR-VIDA", true);
            Log.d("Quitar Vida", "TRUE");
        }else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.spin_is_done_error_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void blockClickSpin() {
        this.firstIc.setClickable(false);
        this.secondIc.setClickable(false);
        this.thirdIc.setClickable(false);
    }

    private void unblockClickSpin() {
        this.firstIc.setClickable(true);
        this.secondIc.setClickable(true);
        this.thirdIc.setClickable(true);
    }

    public void selectFirstSpin(View view){
        if(this.wasShot){
            Question question = this.questionDAO.selectByCategory(Utilities.getCategoryString(resultFirstSpin));
            this.questionDAO.updateView(question, 1);
            Intent intentQuestionActivity = new Intent(getApplicationContext(), QuestionActivity.class);
            intentQuestionActivity.putExtra("QUESTION", question);
            intentQuestionActivity.putExtra("POINTS-TO-PLAY", pointsToPlay);
            intentQuestionActivity.putExtra("JACKPOT", jackpot);
            startActivity(intentQuestionActivity);
        }else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning_to_spin), Toast.LENGTH_SHORT).show();
            this.animateAllSpinError();
        }
    }

    public void selectSecondSpin(View view){
        if(this.wasShot){
            Question question = this.questionDAO.selectByCategory(Utilities.getCategoryString(resultSecondSpin));
            this.questionDAO.updateView(question, 1);
            Intent intentQuestionActivity = new Intent(getApplicationContext(), QuestionActivity.class);
            intentQuestionActivity.putExtra("QUESTION", question);
            intentQuestionActivity.putExtra("POINTS-TO-PLAY", pointsToPlay);
            intentQuestionActivity.putExtra("JACKPOT", jackpot);
            intentQuestionActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentQuestionActivity);
        }else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning_to_spin), Toast.LENGTH_SHORT).show();
            this.animateAllSpinError();
        }
    }

    public void selectThirdSpin(View view){
        if(this.wasShot){
            Question question = this.questionDAO.selectByCategory(Utilities.getCategoryString(resultThirdSpin));
            this.questionDAO.updateView(question, 1);
            Intent intentQuestionActivity = new Intent(getApplicationContext(), QuestionActivity.class);
            intentQuestionActivity.putExtra("QUESTION", question);
            intentQuestionActivity.putExtra("POINTS-TO-PLAY", pointsToPlay);
            intentQuestionActivity.putExtra("JACKPOT", jackpot);
            startActivity(intentQuestionActivity);
        }else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning_to_spin), Toast.LENGTH_SHORT).show();
            this.animateAllSpinError();
        }
    }

    private void animateAllSpinError() {
        YoYo.with(Techniques.Swing)
                .duration(800)
                .playOn(firstIc);
        YoYo.with(Techniques.Swing)
                .duration(800)
                .playOn(secondIc);
        YoYo.with(Techniques.Swing)
                .duration(800)
                .playOn(thirdIc);
    }

    private void animateSpinBonus() {
        bonusText.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.Landing)
                .duration(800)
                .playOn(bonusText);

        YoYo.with(Techniques.Tada)
                .duration(800)
                .playOn(firstIc);
        YoYo.with(Techniques.Tada)
                .duration(800)
                .playOn(secondIc);
        YoYo.with(Techniques.Tada)
                .duration(800)
                .playOn(thirdIc);
    }

    private void animateFirstAndSecondSpin(){
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(firstIc);
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(secondIc);
    }

    private void animateFirstAndThirdSpin(){
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(firstIc);
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(thirdIc);
    }

    private void animateSecondAndThirdSpin(){
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(secondIc);
        YoYo.with(Techniques.Tada)
                .duration(1000)
                .playOn(thirdIc);
    }



    public void goBack(View view){
        if(this.wasShot){
            Toast.makeText(getApplicationContext(), "Si vuelves perderas una vida", Toast.LENGTH_SHORT).show();
        }else {
            onBackPressed();
        }
    }





}
