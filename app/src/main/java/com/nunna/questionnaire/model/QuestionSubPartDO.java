package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 06-03-2016.
 */
public class QuestionSubPartDO {
    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getCustom_pos() {
        return custom_pos;
    }

    public void setCustom_pos(int custom_pos) {
        this.custom_pos = custom_pos;
    }

    public String getCustom_text() {
        return custom_text;
    }

    public void setCustom_text(String custom_text) {
        this.custom_text = custom_text;
    }

    public int getRating_numstars() {
        return rating_numstars;
    }

    public void setRating_numstars(int rating_numstars) {
        this.rating_numstars = rating_numstars;
    }

    public int getButton_pos() {
        return button_pos;
    }

    public void setButton_pos(int button_pos) {
        this.button_pos = button_pos;
    }

    public String getButtonlabel() {
        return buttonlabel;
    }

    public void setButtonlabel(String buttonlabel) {
        this.buttonlabel = buttonlabel;
    }

    @SerializedName("question_id")
    public int question_id;
    @SerializedName("custom_pos")
    public int custom_pos;
    @SerializedName("custom_text")
    public String custom_text;
    @SerializedName("rating_numstars")
    public int rating_numstars;
    @SerializedName("button_pos")
    public int button_pos;
    @SerializedName("buttonlabel")
    public String buttonlabel;
}