package com.nunna.questionnaire.services;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 02-02-2016.
 */
public class RestError {
    @SerializedName("error")
    private String error;
    @SerializedName("error_description")
    private String errorMessage;

    public RestError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
