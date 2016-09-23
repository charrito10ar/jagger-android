package com.appsforworld.triviacountry.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marcelo on 20/09/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper sInstance = null;
    public static final String DATABASE_NAME = "VolcanoSchools.db";
    public static final int DATABASE_VERSION = 1;

    public static final String STRING_TYPE = "text";
    public static final String INT_TYPE = "integer";


    public static final String CREATE_TABLE_LOCATIONS =
            "create table "+ QuestionContract.TABLE_NAME +"(" +
                    QuestionContract._ID +" "+ INT_TYPE +" primary key autoincrement," +
                    QuestionContract.TEXT+" "+ STRING_TYPE+" not null," +
                    QuestionContract.RESPONSES+" "+ STRING_TYPE+" not null," +
                    QuestionContract.CATEGORY +" "+ STRING_TYPE+" not null," +
                    QuestionContract.COUNTRY +" "+ STRING_TYPE+" not null," +
                    QuestionContract.TYPE +" "+ STRING_TYPE +" not null," +
                    QuestionContract.VIEW +" "+ INT_TYPE +" not null," +
                    QuestionContract.CORRECT +" "+ INT_TYPE +" not null)";


    private DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}