package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 04-03-2016.
 */
public class RegisterUserResponse {
    @SerializedName("errorcode")
    public int errorcode;

    @SerializedName("message")
    public String message;

    @SerializedName("userid")
    public int userid;

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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
