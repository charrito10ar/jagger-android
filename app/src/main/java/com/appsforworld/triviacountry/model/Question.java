package com.appsforworld.triviacountry.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by marcelo on 17/9/16.
 */
public class Question implements Parcelable{
    private int id;
    private String text;
    private String country;
    private String category;
    private int correct;
    private String type;
    private int view;
    private String responses;
    private int likePercentage;

    protected Question(Parcel in) {
        id = in.readInt();
        text = in.readString();
        country = in.readString();
        category = in.readString();
        correct = in.readInt();
        type = in.readString();
        view = in.readInt();
        responses = in.readString();
        likePercentage = in.readInt();
    }

    public Question(){

    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponses() {
        return responses;
    }

    public void setResponses(String responses) {
        this.responses = responses;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikePercentage() {
        return likePercentage;
    }

    public void setLikePercentage(int likePercentage) {
        this.likePercentage = likePercentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeString(country);
        dest.writeString(category);
        dest.writeInt(correct);
        dest.writeString(type);
        dest.writeInt(view);
        dest.writeString(responses);
        dest.writeInt(likePercentage);
    }
}
