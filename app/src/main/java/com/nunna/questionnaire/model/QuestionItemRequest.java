package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cvlhyd on 06-03-2016.
 */
public class QuestionItemRequest {
    @SerializedName("userid")
    public int userid;

    @SerializedName("questionid")
    public int questionid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }
}
