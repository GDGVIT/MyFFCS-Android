package com.dscvit.android.myffcs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ClassroomResponse {

    @ColumnInfo
    @SerializedName("CODE")
    @Expose
    private String code;

    @ColumnInfo
    @SerializedName("TITLE")
    @Expose
    private String title;

    @ColumnInfo
    @SerializedName("TYPE")
    @Expose
    private String type;

    @ColumnInfo
    @SerializedName("CREDITS")
    @Expose
    private String credits;

    @ColumnInfo
    @SerializedName("VENUE")
    @Expose
    private String venue;

    @ColumnInfo
    @SerializedName("SLOT")
    @Expose
    private String slot;

    @ColumnInfo
    @SerializedName("FACULTY")
    @Expose
    private String faculty;

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    /**
     * No args constructor for use in serialization
     */
    public ClassroomResponse() {
    }

    /**
     * @param title Course title
     * @param faculty Faculty name
     * @param venue Course venue
     * @param code Course code
     * @param credits Number of credits the course is worth
     * @param slot Slots the course is during
     * @param type Course type (Theory, Lab, Project)
     * @param id Unique identifier for the course
     */
    public ClassroomResponse(String code, String title, String type, String credits, String venue, String slot, String faculty, int id) {
        super();
        this.code = code;
        this.title = title;
        this.type = type;
        this.credits = credits;
        this.venue = venue;
        this.slot = slot;
        this.faculty = faculty;
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}