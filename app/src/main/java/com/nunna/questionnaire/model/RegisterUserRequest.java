package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 04-03-2016.
 */
public class RegisterUserRequest {
    @SerializedName("telnumber")
    public String telnumber;

    @SerializedName("stateid")
    public int stateid;

    @SerializedName("gender")
    public String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public int getStateid() {
        return stateid;
    }

    public void setStateid(int stateid) {
        this.stateid = stateid;
    }
}
