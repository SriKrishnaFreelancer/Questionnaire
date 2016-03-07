package com.nunna.questionnaire.model;

/**
 * Created by Sri Krishna on 02-02-2016.
 */
public class UserDetails {
    private String userId;
    private String username;

    private String ofJid;

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    private String bearerToken;

    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getOfJid() {
        return ofJid;
    }

    public void setOfJid(String ofJid) {
        this.ofJid = ofJid;
    }
}
