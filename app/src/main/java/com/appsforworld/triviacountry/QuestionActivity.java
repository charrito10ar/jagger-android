package com.appsforworld.triviacountry;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsforworld.triviacountry.model.Question;
import com.appsforworld.triviacountry.overwrite.PrimaryTextView;
import com.appsforworld.triviacountry.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        this.toolbar = (RelativeLayout) findViewById(R.id.question_toolbar);
        this.categoryName = (PrimaryTextView) findViewById(R.id.category_name);
        this.imageCategory = (ImageView) findViewById(R.id.ic_category_question);
        this.question = getIntent().getParcelableExtra("QUESTION");

        this.questionText = (TextView) findViewById(R.id.question_text);
        this.answerOne = (TextView) findViewById(R.id.answer_one);
        this.answerTwo = (TextView) findViewById(R.id.answer_two);
        this.answerThree = (TextView) findViewById(R.id.answer_three);
        this.answerFour = (TextView) findViewById(R.id.answer_four);

        this.cardAnswerOne = (CardView) findViewById(R.id.card_answer_one);
        this.cardAnswerTwo = (CardView) findViewById(R.id.card_answer_two);
        this.cardAnswerThree = (CardView) findViewById(R.id.card_answer_three);
        this.cardAnswerFour = (CardView) findViewById(R.id.card_answer_four);

        this.setQuestion();
        this.setLookAndFeel();
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
                if (this.question.getCorrect() == 0){
                    this.cardAnswerOne.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                    this.answerOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }else {
                    this.cardAnswerOne.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
                    this.answerOne.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.card_answer_two:
                if (this.question.getCorrect() == 1){
                    this.cardAnswerTwo.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                    this.answerTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }else {
                    Toast.makeText(getApplicationContext(), "Incorrecta", Toast.LENGTH_SHORT).show();
                    this.cardAnswerTwo.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
                    this.answerTwo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.card_answer_three:
                if (this.question.getCorrect() == 2){
                    this.cardAnswerThree.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                    this.answerThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }else {
                    this.cardAnswerThree.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
                    this.answerThree.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.card_answer_four:
                if (this.question.getCorrect() == 3){
                    this.cardAnswerFour.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSci));
                    this.answerFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }else {
                    this.cardAnswerFour.setCardBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorArt));
                    this.answerFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
        }
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
