package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sri Krishna on 04-03-2016.
 */
public class StateResponse {
    @SerializedName("errorcode")
    public int errorcode;

    @SerializedName("message")
    public String message;

    @SerializedName("states")
    public ArrayList<StateDO> stateDOArrayList;

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

    public ArrayList<StateDO> getStateDOArrayList() {
        return stateDOArrayList;
    }

    public void setStateDOArrayList(ArrayList<StateDO> stateDOArrayList) {
        this.stateDOArrayList = stateDOArrayList;
    }
}
