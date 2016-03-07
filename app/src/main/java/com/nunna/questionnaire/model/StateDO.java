package com.nunna.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by cvlhyd on 05-03-2016.
 */
public class StateDO implements Serializable
{
    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}