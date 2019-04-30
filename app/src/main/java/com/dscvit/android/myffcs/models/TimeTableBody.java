package com.dscvit.android.myffcs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableBody {

    @SerializedName("timetable")
    @Expose
    private List<Integer> timetable = null;

    /**
     * No args constructor for use in serialization
     */
    public TimeTableBody() {
    }

    /**
     * @param timetable
     */
    public TimeTableBody(List<Integer> timetable) {
        super();
        this.timetable = timetable;
    }

    public List<Integer> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<Integer> timetable) {
        this.timetable = timetable;
    }

}