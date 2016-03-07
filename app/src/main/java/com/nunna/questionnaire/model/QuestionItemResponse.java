package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sri Krishna on 06-03-2016.
 */
public class QuestionItemResponse {
    @SerializedName("errorcode")
    public int errorcode;

    @SerializedName("message")
    public String message;

    @SerializedName("question")
    public ArrayList<QuestionSubPartDO> questionSubPartDOs;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<QuestionSubPartDO> getQuestionSubPartDOs() {
        return questionSubPartDOs;
    }

    public void setQuestionSubPartDOs(ArrayList<QuestionSubPartDO> questionSubPartDOs) {
        this.questionSubPartDOs = questionSubPartDOs;
    }
}
