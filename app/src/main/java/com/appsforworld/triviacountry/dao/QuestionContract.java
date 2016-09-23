package com.appsforworld.triviacountry.dao;

import android.provider.BaseColumns;

/**
 * Created by marcelo on 20/09/16.
 */
public abstract class QuestionContract implements BaseColumns {
    public static final String TABLE_NAME ="questions";

    public static final String TEXT = "text";
    public static final String RESPONSES = "responses";
    public static final String COUNTRY = "country";
    public static final String CATEGORY = "category";
    public static final String CORRECT = "correct";
    public static final String TYPE = "type";
    public static final String VIEW = "view";

}

