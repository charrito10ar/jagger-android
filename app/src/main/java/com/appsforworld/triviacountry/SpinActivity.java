package com.appsforworld.triviacountry;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.appsforworld.triviacountry.dao.QuestionDAO;
import com.appsforworld.triviacountry.model.Question;
import com.appsforworld.triviacountry.utilities.Constants;
import com.appsforworld.triviacountry.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;

public class SpinActivity extends AppCompatActivity {

    private int[] firstColumMachine = {Constants.ART_CATEGORY_MACHINE, Constants.ENT_CATEGORY_MACHINE, Constants.GEO_CATEGORY_MACHINE,
            Constants.HIS_CATEGORY_MACHINE, Constants.SCI_CATEGORY_MACHINE, Constants.SPO_CATEGORY_MACHINE};
    private int[] secondtColumMachine = {Constants.ART_CATEGORY_MACHINE, Constants.ENT_CATEGORY_MACHINE, Constants.GEO_CATEGORY_MACHINE,
            Constants.HIS_CATEGORY_MACHINE, Constants.SCI_CATEGORY_MACHINE, Constants.SPO_CATEGORY_MACHINE};
    private int[] thirdColumMachine = {Constants.ART_CATEGORY_MACHINE, Constants.ENT_CATEGORY_MACHINE, Constants.GEO_CATEGORY_MACHINE,
            Constants.HIS_CATEGORY_MACHINE, Constants.SCI_CATEGORY_MACHINE, Constants.SPO_CATEGORY_MACHINE};
    private FrameLayout firstSpinLayout;
    private FrameLayout secondSpinLayout;
    private FrameLayout thirdSpinLayout;
    private ImageView firstIc;
    private ImageView secondIc;
    private ImageView thirdIc;

    private int resultFirstSpin;
    private int resultSecondSpin;
    private int resultThirdSpin;
    private QuestionDAO questionDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initMachine();
        setContentView(R.layout.activity_spin);

        this.firstSpinLayout = (FrameLayout) findViewById(R.id.firstSpin);
        this.secondSpinLayout = (FrameLayout) findViewById(R.id.secondSpin);
        this.thirdSpinLayout = (FrameLayout) findViewById(R.id.thirdSpin);
        this.firstIc = (ImageView) findViewById(R.id.firstSpin_ic);
        this.secondIc = (ImageView) findViewById(R.id.secondSpin_ic);
        this.thirdIc = (ImageView) findViewById(R.id.thirdSpin_ic);

        this.questionDAO = new QuestionDAO(getApplicationContext());
    }

    private void initMachine() {

    }

    public void spin(View view){

            new CountDownTimer(3000, 100) {
                public void onFinish() {
                    Log.d("SPIN 1", "FIN");
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


        new CountDownTimer(3000, 100) {
            public void onFinish() {
                Log.d("SPIN 2", "FIN");
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


        new CountDownTimer(3000, 100) {
            public void onFinish() {
                Log.d("SPIN 3", "FIN");

                boolean oneTwo = resultFirstSpin == resultSecondSpin;
                boolean oneThree = resultFirstSpin == resultThirdSpin;
                boolean twoThree = resultSecondSpin == resultThirdSpin;

                if(oneTwo || oneThree || twoThree){
                    if(resultFirstSpin == resultSecondSpin){
                        if(resultSecondSpin == resultThirdSpin){
                            Toast.makeText(getApplicationContext(), "Acertaste TODO!!!", Toast.LENGTH_SHORT).show();
                        }else {
                            if (oneTwo){
                                Toast.makeText(getApplicationContext(), "Acertaste el UNO Y DOS!!!", Toast.LENGTH_SHORT).show();
                            }else {
                                if (oneThree){
                                    Toast.makeText(getApplicationContext(), "Acertaste el UNO Y TRES!!!", Toast.LENGTH_SHORT).show();
                                }else {
                                    if (twoThree){
                                        Toast.makeText(getApplicationContext(), "Acertaste el DOS Y TRES!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                    }else {
                        if (oneThree){
                            Toast.makeText(getApplicationContext(), "Acertaste el UNO Y TRES!!!", Toast.LENGTH_SHORT).show();
                        }else {
                            if (twoThree){
                                Toast.makeText(getApplicationContext(), "Acertaste el DOS Y TRES!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        }
                }else {
                    Toast.makeText(getApplicationContext(), "PERDISTE :(", Toast.LENGTH_SHORT).show();
                }
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

    }

    public void selectFirstSpin(View view){
        Question question = this.questionDAO.selectByCategory(Utilities.getCategoryString(resultFirstSpin));
        Intent intentQuestionActivity = new Intent(getApplicationContext(), QuestionActivity.class);
        intentQuestionActivity.putExtra("QUESTION", question);
        startActivity(intentQuestionActivity);
    }

    public void selectSecondSpin(View view){
        Question question = this.questionDAO.selectByCategory(Utilities.getCategoryString(resultSecondSpin));
        Intent intentQuestionActivity = new Intent(getApplicationContext(), QuestionActivity.class);
        intentQuestionActivity.putExtra("QUESTION", question);
        startActivity(intentQuestionActivity);

    }

    public void selectThirdSpin(View view){
        Question question = this.questionDAO.selectByCategory(Utilities.getCategoryString(resultThirdSpin));
        Intent intentQuestionActivity = new Intent(getApplicationContext(), QuestionActivity.class);
        intentQuestionActivity.putExtra("QUESTION", question);
        startActivity(intentQuestionActivity);
    }

    public void goBack(View view){
        onBackPressed();
    }


}
