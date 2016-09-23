package com.appsforworld.triviacountry.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appsforworld.triviacountry.model.Question;
import com.appsforworld.triviacountry.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelo on 20/09/16.
 */
public class QuestionDAO {


    private DBHelper openHelper;
    private SQLiteDatabase database;
    private Context context;

    public QuestionDAO(Context context) {
        this.openHelper = DBHelper.getInstance(context);
        this.database = openHelper.getWritableDatabase();
        this.context = context;
    }

    public void insertRow(Question question){

        ContentValues values = new ContentValues();

        values.put(QuestionContract.TEXT, question.getText());
        values.put(QuestionContract.RESPONSES, question.getResponses());
        values.put(QuestionContract.CATEGORY, question.getCategory());
        values.put(QuestionContract.COUNTRY, question.getCountry());
        values.put(QuestionContract.TYPE, question.getType());
        values.put(QuestionContract.CORRECT, question.getCorrect());
        values.put(QuestionContract.VIEW, question.getView());

        this.database.insert(QuestionContract.TABLE_NAME, null, values);
    }

    public List<Question> selectFullTable(){
        List<Question> questionList = new ArrayList<>();
        Cursor fila = database.rawQuery("select * from " + QuestionContract.TABLE_NAME, null);
        Question question = null;
        if (fila.getCount() > 0){
            if (fila.moveToFirst()) {
                do {
                    question = new Question();
                    questionList.add(question);
                } while (fila.moveToNext());
            }
        }
        fila.close();
        return questionList;
    }

    public Question selectByCategory(String category){
        Question question = new Question();
        Cursor fila = database.rawQuery("select * from " + QuestionContract.TABLE_NAME +
                " where " + QuestionContract.CATEGORY + " = '" + category + "' ORDER BY RANDOM() LIMIT 1", null);
        if (fila.getCount() > 0){
            if (fila.moveToFirst()) {
                do {
                    question.setText(fila.getString(1));
                    question.setResponses(fila.getString(2));
                    question.setCategory(fila.getString(3));
                    question.setCountry(fila.getString(4));
                    question.setCorrect(fila.getInt(7));
                    //question.setType(fila.getString(5));

                } while (fila.moveToNext());
            }
        }
        fila.close();
        return question;
    }

}
