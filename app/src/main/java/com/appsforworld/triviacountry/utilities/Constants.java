package com.appsforworld.triviacountry.utilities;

/**
 * Created by marcelo on 20/09/16.
 */
public class Constants {
    public static final int TIME_TO_ANSWER = 20000;
    public static final int TIME_SPIN = 2500;
    public static final int ONE_SECOND = 1000;

    public static final String ART_CATEGORY = "ARTS";
    public static final String ENT_CATEGORY = "ENTERTAINMENT";
    public static final String GEO_CATEGORY = "GEOGRAPHY";
    public static final String HIS_CATEGORY = "HISTORY";
    public static final String SCI_CATEGORY = "SCIENCE";
    public static final String SPO_CATEGORY = "SPORTS";

    public static final int ART_CATEGORY_MACHINE = 0;
    public static final int ENT_CATEGORY_MACHINE = 1;
    public static final int GEO_CATEGORY_MACHINE = 2;
    public static final int HIS_CATEGORY_MACHINE = 3;
    public static final int SCI_CATEGORY_MACHINE = 4;
    public static final int SPO_CATEGORY_MACHINE = 5;

    public static final int ANIMATION_DURATION_PROGRESS = 3000;

    /******************************** STATS ***********************************/
    public static final String STAT_USER_POINTS = "USER-POINTS";
    public static final String STAT_USER_LEVEL = "USER-LEVEL";
    //Hasta nivel 50
    public static final int[] STAT_LEVELS = {0 , 11, 33, 66, 110, 165, 231, 308, 396, 495, 605, 726, 858, 1001, 1155, 1320, 1496, 1683, 1881, 2090, 2310, 2541, 2783,
            3036, 3300, 3575, 3861, 4158, 4466, 4785, 5115, 5456, 5808, 6171, 6545, 6930, 7326, 7733, 8151, 8580, 9020, 9471, 9933, 10406, 10890, 11385, 11891, 12408,
            12936, 13475, 14025};

    public static final int BONUS_POINTS = 3;

    public static final String STAT_TOTAL_QUESTIONS = "TOTAL-QUESTIONS";
    public static final String STAT_TOTAL_ANSWERED = "TOTAL-ANSWERED";
    public static final String STAT_CORRECT_ANSWERED = "CORRECT-ANSWERED";
    public static final String STAT_INCORRECT_ANSWERED = "INCORRECT-ANSWERED";
    public static final String STAT_TIMES_ASK_QUESTIONS_API = "TIMES-ASK-QUESTIONS";

    public static final String STAT_STOCK_TIME_ANSWER = "STOCK-TIME-ANSWER";


    public static final String STAT_ART_INCORRECT = "INCORRECT-ART";
    public static final String STAT_ENT_INCORRECT = "INCORRECT-ENT";
    public static final String STAT_GEO_INCORRECT = "INCORRECT-GEO";
    public static final String STAT_HIS_INCORRECT = "INCORRECT-HIS";
    public static final String STAT_SCI_INCORRECT = "INCORRECT-SCI";
    public static final String STAT_SPO_INCORRECT = "INCORRECT-SPO";

    public static final String STAT_ART_CORRECT = "CORRECT-ART";
    public static final String STAT_ENT_CORRECT = "CORRECT-ENT";
    public static final String STAT_GEO_CORRECT = "CORRECT-GEO";
    public static final String STAT_HIS_CORRECT = "CORRECT-HIS";
    public static final String STAT_SCI_CORRECT = "CORRECT-SCI";
    public static final String STAT_SPO_CORRECT = "CORRECT-SPO";

    /******************************** FIN STATS ***********************************/
    public static final int INT_TIME_OUT = 10000;
}
