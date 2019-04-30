package com.dscvit.android.myffcs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {

    @SerializedName("timetable")
    @Expose
    private List<Integer> timetable = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("lastUpdatedAt")
    @Expose
    private String lastUpdatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    /**
     * No args constructor for use in serialization
     */
    public UserResponse() {
    }

    /**
     * @param id            firebase id
     * @param v             idk
     * @param lastUpdatedAt l
     * @param createdAt     ;
     * @param userName      ;
     * @param timetable     ;
     */
    public UserResponse(List<Integer> timetable, String id, String userName, String createdAt, String lastUpdatedAt, Integer v) {
        super();
        this.timetable = timetable;
        this.id = id;
        this.userName = userName;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.v = v;
    }

    public List<Integer> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Integer> timetable) {
        this.timetable = timetable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(String lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}