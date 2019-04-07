package com.dscvit.android.myffcs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ClassroomResponse {

    @NonNull
    @PrimaryKey
    @SerializedName("CODE")
    @Expose
    private String code;
    @SerializedName("TITLE")
    @Expose
    private String title;
    @SerializedName("TYPE")
    @Expose
    private String type;
    @SerializedName("CREDITS")
    @Expose
    private String credits;
    @SerializedName("VENUE")
    @Expose
    private String venue;
    @SerializedName("SLOT")
    @Expose
    private String slot;
    @SerializedName("FACULTY")
    @Expose
    private String faculty;

    /**
     * No args constructor for use in serialization
     */
    public ClassroomResponse() {
    }

    /**
     * @param title
     * @param faculty
     * @param venue
     * @param code
     * @param credits
     * @param slot
     * @param type
     */
    public ClassroomResponse(String code, String title, String type, String credits, String venue, String slot, String faculty) {
        super();
        this.code = code;
        this.title = title;
        this.type = type;
        this.credits = credits;
        this.venue = venue;
        this.slot = slot;
        this.faculty = faculty;
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
}