package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sri Krishna on 04-03-2016.
 */
public class QuestionDO implements Serializable
{
    @SerializedName("id")
    public String id;
    @SerializedName("poll_id")
    public int poll_id;
    @SerializedName("caption")
    public String caption;
    @SerializedName("questiontype_id")
    public String questiontype_id;
    @SerializedName("title_media_type")
    public String title_media_type;
    @SerializedName("title_media_id")
    public String title_media_id;
    @SerializedName("question")
    public String question;
    @SerializedName("question_bgcolor")
    public String question_bgcolor;
    @SerializedName("question_textcolor")
    public String question_textcolor;
    @SerializedName("question_bgimage_id")
    public String question_bgimage_id;
    @SerializedName("created_user")
    public String created_user;
    @SerializedName("posted_user")
    public String posted_user;
    @SerializedName("posted_date")
    public String posted_date;
    @SerializedName("dplayout")
    public String dplayout;
    @SerializedName("closedate")
    public String closedate;
    @SerializedName("dplayout_id")
    public String dplayout_id;
    @SerializedName("resultout")
    public String resultout;
    @SerializedName("status")
    public String status;
    @SerializedName("resource_question_id")
    public String resource_question_id;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("pollname")
    public String pollname;
    @SerializedName("pollstartdate")
    public String pollstartdate;
    @SerializedName("pollenddate")
    public String pollenddate;
    @SerializedName("questiontype")
    public String questiontype;
    @SerializedName("mediatype")
    public String mediatype;
    @SerializedName("filepath")
    public String filepath;
    @SerializedName("filename")
    public String filename;
    @SerializedName("questenddt")
    public String questenddt;
    @SerializedName("question_id")
    public int question_id;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("image")
    public String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(int poll_id) {
        this.poll_id = poll_id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getQuestiontype_id() {
        return questiontype_id;
    }

    public void setQuestiontype_id(String questiontype_id) {
        this.questiontype_id = questiontype_id;
    }

    public String getTitle_media_type() {
        return title_media_type;
    }

    public void setTitle_media_type(String title_media_type) {
        this.title_media_type = title_media_type;
    }

    public String getTitle_media_id() {
        return title_media_id;
    }

    public void setTitle_media_id(String title_media_id) {
        this.title_media_id = title_media_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion_bgcolor() {
        return question_bgcolor;
    }

    public void setQuestion_bgcolor(String question_bgcolor) {
        this.question_bgcolor = question_bgcolor;
    }

    public String getQuestion_textcolor() {
        return question_textcolor;
    }

    public void setQuestion_textcolor(String question_textcolor) {
        this.question_textcolor = question_textcolor;
    }

    public String getQuestion_bgimage_id() {
        return question_bgimage_id;
    }

    public void setQuestion_bgimage_id(String question_bgimage_id) {
        this.question_bgimage_id = question_bgimage_id;
    }

    public String getCreated_user() {
        return created_user;
    }

    public void setCreated_user(String created_user) {
        this.created_user = created_user;
    }

    public String getPosted_user() {
        return posted_user;
    }

    public void setPosted_user(String posted_user) {
        this.posted_user = posted_user;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

    public String getDplayout() {
        return dplayout;
    }

    public void setDplayout(String dplayout) {
        this.dplayout = dplayout;
    }

    public String getClosedate() {
        return closedate;
    }

    public void setClosedate(String closedate) {
        this.closedate = closedate;
    }

    public String getDplayout_id() {
        return dplayout_id;
    }

    public void setDplayout_id(String dplayout_id) {
        this.dplayout_id = dplayout_id;
    }

    public String getResultout() {
        return resultout;
    }

    public void setResultout(String resultout) {
        this.resultout = resultout;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResource_question_id() {
        return resource_question_id;
    }

    public void setResource_question_id(String resource_question_id) {
        this.resource_question_id = resource_question_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPollname() {
        return pollname;
    }

    public void setPollname(String pollname) {
        this.pollname = pollname;
    }

    public String getPollstartdate() {
        return pollstartdate;
    }

    public void setPollstartdate(String pollstartdate) {
        this.pollstartdate = pollstartdate;
    }

    public String getPollenddate() {
        return pollenddate;
    }

    public void setPollenddate(String pollenddate) {
        this.pollenddate = pollenddate;
    }

    public String getQuestiontype() {
        return questiontype;
    }

    public void setQuestiontype(String questiontype) {
        this.questiontype = questiontype;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getQuestenddt() {
        return questenddt;
    }

    public void setQuestenddt(String questenddt) {
        this.questenddt = questenddt;
    }
}