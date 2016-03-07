package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sri Krishna on 04-03-2016.
 */
public class QuestionnaireResponse {
    @SerializedName("errorcode")
    public String errorcode;

    @SerializedName("message")
    public String message;

    @SerializedName("questionlist")
    public ArrayList<QuestionDO> questionList;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<QuestionDO> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<QuestionDO> questionList) {
        this.questionList = questionList;
    }
}
