package com.appsforworld.triviacountry.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.appsforworld.triviacountry.model.Question;

/**
 * Created by marcelo on 24/09/16.
 */
public class Stats {

    public static void incrementCorrectAnswered(Context context, Question question) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        int actualCorrectAnswered = SP.getInt(Constants.STAT_CORRECT_ANSWERED, 0);
        int actualTotalAnswered = SP.getInt(Constants.STAT_TOTAL_ANSWERED, 0);
        SharedPreferences.Editor editor = SP.edit();
        editor.putInt(Constants.STAT_CORRECT_ANSWERED, actualCorrectAnswered + 1);
        editor.putInt(Constants.STAT_TOTAL_ANSWERED, actualTotalAnswered + 1);
        incrementCorrectCategory(context, question.getCategory());
        editor.commit();
    }

    public static void incrementIncorrectAnswered(Context context, Question question) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        int actualCorrectAnswered = SP.getInt(Constants.STAT_INCORRECT_ANSWERED, 0);
        int actualTotalAnswered = SP.getInt(Constants.STAT_TOTAL_ANSWERED, 0);
        SharedPreferences.Editor editor = SP.edit();
        editor.putInt(Constants.STAT_INCORRECT_ANSWERED, actualCorrectAnswered + 1);
        editor.putInt(Constants.STAT_TOTAL_ANSWERED, actualTotalAnswered + 1);
        incrementIncorrectCategory(context, question.getCategory());
        editor.commit();
    }

    public static void incrementTimesAskQuestion(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        int currentTimesAsk = SP.getInt(Constants.STAT_TIMES_ASK_QUESTIONS_API, 0);
        SharedPreferences.Editor editor = SP.edit();
        editor.putInt(Constants.STAT_TIMES_ASK_QUESTIONS_API, currentTimesAsk  + 1);
        editor.commit();
    }

    public static int getTimesAskQuestions(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        return SP.getInt(Constants.STAT_TIMES_ASK_QUESTIONS_API, 1);
    }


    public static int getTotalQuestion(Context context){
        return getTimesAskQuestions(context) * 250;
    }

    public static int getTotalAnswered(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        return SP.getInt(Constants.STAT_TOTAL_ANSWERED, 0);
    }

    public static float getPercentAnswered(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        float totalAnswered = SP.getInt(Constants.STAT_TOTAL_ANSWERED, 0);
        float percent = (totalAnswered / getTotalQuestion(context)) * 100;
        return percent;
    }

    public static float getPercentCorrectAnswered(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        float correctas = SP.getInt(Constants.STAT_CORRECT_ANSWERED, 0);
        float total = SP.getInt(Constants.STAT_TOTAL_ANSWERED, 1);
        return (correctas / total)  * 100;
    }

    public static float getPercentIncorrectAnswered(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        float incorrectas = SP.getInt(Constants.STAT_INCORRECT_ANSWERED, 0);
        float total = SP.getInt(Constants.STAT_TOTAL_ANSWERED, 1);
        return (incorrectas / total) * 100;
    }

    public static void incrementCorrectCategory(Context context, String category){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = SP.edit();
        switch (category) {
            case Constants.ART_CATEGORY:
                int actualCorrectART = SP.getInt(Constants.STAT_ART_CORRECT, 0);
                editor.putInt(Constants.STAT_ART_CORRECT, actualCorrectART + 1);
                break;
            case Constants.ENT_CATEGORY:
                int actualCorrectENT = SP.getInt(Constants.STAT_ENT_CORRECT, 0);
                editor.putInt(Constants.STAT_ENT_CORRECT, actualCorrectENT + 1);
                break;
            case Constants.GEO_CATEGORY:
                int actualCorrectGEO = SP.getInt(Constants.STAT_GEO_CORRECT, 0);
                editor.putInt(Constants.STAT_GEO_CORRECT, actualCorrectGEO + 1);
                break;
            case Constants.HIS_CATEGORY:
                int actualCorrectHIS = SP.getInt(Constants.STAT_HIS_CORRECT, 0);
                editor.putInt(Constants.STAT_HIS_CORRECT, actualCorrectHIS + 1);
                break;
            case Constants.SCI_CATEGORY:
                int actualCorrectSCI = SP.getInt(Constants.STAT_SCI_CORRECT, 0);
                editor.putInt(Constants.STAT_SCI_CORRECT, actualCorrectSCI + 1);
                break;
            case Constants.SPO_CATEGORY:
                int actualCorrectSPO = SP.getInt(Constants.STAT_SPO_CORRECT, 0);
                editor.putInt(Constants.STAT_SPO_CORRECT, actualCorrectSPO + 1);
        }
        editor.commit();
    }

    public static void incrementIncorrectCategory(Context context, String category){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = SP.edit();
        switch (category) {
            case Constants.ART_CATEGORY:
                int actualIncorrectART = SP.getInt(Constants.STAT_ART_INCORRECT, 0);
                editor.putInt(Constants.STAT_ART_INCORRECT, actualIncorrectART + 1);
                break;
            case Constants.ENT_CATEGORY:
                int actualIncorrectENT = SP.getInt(Constants.STAT_ENT_INCORRECT, 0);
                editor.putInt(Constants.STAT_ENT_INCORRECT, actualIncorrectENT + 1);
                break;
            case Constants.GEO_CATEGORY:
                int actualIncorrectGEO = SP.getInt(Constants.STAT_GEO_INCORRECT, 0);
                editor.putInt(Constants.STAT_GEO_INCORRECT, actualIncorrectGEO + 1);
                break;
            case Constants.HIS_CATEGORY:
                int actualIncorrectHIS = SP.getInt(Constants.STAT_HIS_INCORRECT, 0);
                editor.putInt(Constants.STAT_HIS_INCORRECT, actualIncorrectHIS + 1);
                break;
            case Constants.SCI_CATEGORY:
                int actualIncorrectSCI = SP.getInt(Constants.STAT_SCI_INCORRECT, 0);
                editor.putInt(Constants.STAT_SCI_INCORRECT, actualIncorrectSCI + 1);
                break;
            case Constants.SPO_CATEGORY:
                int actualIncorrectSPO = SP.getInt(Constants.STAT_SPO_INCORRECT, 0);
                editor.putInt(Constants.STAT_SPO_INCORRECT, actualIncorrectSPO + 1);
        }
        editor.commit();
    }

    public static float getPercentCorrectCategory(Context context, String category){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        float totalCorrect = SP.getInt(Constants.STAT_CORRECT_ANSWERED, 1);
        switch (category){
            case Constants.ART_CATEGORY:
                float correctsART = SP.getInt(Constants.STAT_ART_CORRECT, 0);
                return correctsART / totalCorrect;
            case Constants.ENT_CATEGORY:
                float correctsENT = SP.getInt(Constants.STAT_ENT_CORRECT, 0);
                return correctsENT / totalCorrect;
            case Constants.GEO_CATEGORY:
                float correctsGEO = SP.getInt(Constants.STAT_GEO_CORRECT, 0);
                return correctsGEO / totalCorrect;
            case Constants.HIS_CATEGORY:
                float correctsHIS = SP.getInt(Constants.STAT_HIS_CORRECT, 0);
                return correctsHIS / totalCorrect;
            case Constants.SCI_CATEGORY:
                float correctsSCI = SP.getInt(Constants.STAT_SCI_CORRECT, 0);
                return correctsSCI / totalCorrect;
            case Constants.SPO_CATEGORY:
                float correctsSPO = SP.getInt(Constants.STAT_SPO_CORRECT, 0);
                return correctsSPO / totalCorrect;
        }
        return 0;
    }

    public static float getPercentIncorrectCategory(Context context, String category){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        float totalCorrect = SP.getInt(Constants.STAT_INCORRECT_ANSWERED, 1);
        switch (category){
            case Constants.ART_CATEGORY:
                float incorrectART = SP.getInt(Constants.STAT_ART_INCORRECT, 0);
                return incorrectART / totalCorrect;
            case Constants.ENT_CATEGORY:
                float incorrectENT = SP.getInt(Constants.STAT_ENT_INCORRECT, 0);
                return incorrectENT / totalCorrect;
            case Constants.GEO_CATEGORY:
                float incorrectGEO = SP.getInt(Constants.STAT_GEO_INCORRECT, 0);
                return incorrectGEO / totalCorrect;
            case Constants.HIS_CATEGORY:
                float incorrectHIS = SP.getInt(Constants.STAT_HIS_INCORRECT, 0);
                return incorrectHIS / totalCorrect;
            case Constants.SCI_CATEGORY:
                float incorrectSCI = SP.getInt(Constants.STAT_SCI_INCORRECT, 0);
                return incorrectSCI / totalCorrect;
            case Constants.SPO_CATEGORY:
                float incorrectSPO = SP.getInt(Constants.STAT_SPO_INCORRECT, 0);
                return incorrectSPO / totalCorrect;
        }
        return 0;
    }

    public static void addTimeAnswer(Context context, long answerTime){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = SP.edit();
        long currentStockTimeAnswer =  SP.getLong(Constants.STAT_STOCK_TIME_ANSWER, 0);
        editor.putLong(Constants.STAT_STOCK_TIME_ANSWER, currentStockTimeAnswer + answerTime);
        editor.commit();
    }

    public static float getAVGTimeAnswer(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        long currentStockTimeAnswer =  SP.getLong(Constants.STAT_STOCK_TIME_ANSWER, 0);
        float totalAnswer = SP.getInt(Constants.STAT_TOTAL_ANSWERED, 1);
        return currentStockTimeAnswer / totalAnswer;
    }


    public static void incrementPoints(Context context, int pointsToPlay) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = SP.edit();
        int actualPoints = SP.getInt(Constants.STAT_USER_POINTS, 0);
        int newPoints = pointsToPlay + actualPoints;
        editor.putInt(Constants.STAT_USER_POINTS, newPoints);
        int userLevel = getUserLevel(context);
        if(newPoints >= Constants.STAT_LEVELS[userLevel]){
            editor.putInt(Constants.STAT_USER_LEVEL, userLevel + 1);
        }
        editor.commit();
    }

    public static int getUserLevel(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        int userLevel = SP.getInt(Constants.STAT_USER_LEVEL, 1);
        return userLevel;
    }

    public static int getDeltaLevel(Context context){
        int userLevel = getUserLevel(context) - 1;
        int deltaLevel = Constants.STAT_LEVELS[userLevel + 1] - Constants.STAT_LEVELS[userLevel];
        return deltaLevel;
    }

    public static int getLevelPoints(Context context){
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        int userPoints = SP.getInt(Constants.STAT_USER_POINTS, 0);
        int userLevel = getUserLevel(context) - 1;
        int goalPoints = Constants.STAT_LEVELS[userLevel];
        int levelPoints =  userPoints - goalPoints;
        return levelPoints;
    }

}
