package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Sri Krishna on 06-03-2016.
 */
public class QuestionPollResponse {
    @SerializedName("errorcode")
    public int errorcode;

    @SerializedName("message")
    public String message;

    public HashMap<String, Integer> getResult() {
        return result;
    }

    public void setResult(HashMap<String, Integer> result) {
        this.result = result;
    }

    @SerializedName("result")
    public HashMap<String,Integer> result;


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
}
