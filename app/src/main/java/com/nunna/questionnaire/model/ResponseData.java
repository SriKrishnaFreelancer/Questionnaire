package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cvluser on 28-07-2015.
 */
public class ResponseData
{
    @SerializedName("Status")
    private String status;
    @SerializedName("Message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
