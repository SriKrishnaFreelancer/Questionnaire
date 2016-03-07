package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 05-03-2016.
 */
public class ConfirmSMSResponse {
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

    public int getOTP() {
        return OTP;
    }

    public void setOTP(int OTP) {
        this.OTP = OTP;
    }

    @SerializedName("errorcode")
    public int errorcode;

    @SerializedName("message")
    public String message;

    @SerializedName("OTP")
    public int OTP;
}
