package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 04-03-2016.
 */
public class QuestionnaireListRequest {
    @SerializedName("userid")
    public int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
