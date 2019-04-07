package com.dscvit.android.myffcs.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiModel {
    @GET("classroom/search/faculty/{keyword}")
    Call<List<ClassroomResponse>> getCoursesByFaculty(@Path("keyword") String facultyName);

    @GET("classroom/search/code/{keyword}")
    Call<List<ClassroomResponse>> getCoursesByCode(@Path("keyword") String courseCode);

    @GET("classroom/search/title/{keyword}")
    Call<List<ClassroomResponse>> getCoursesByCourseName(@Path("keyword") String courseTitle);

    @GET("classroom/{keyword}")
    Call<ClassroomResponse> getCoursesByCourseId(@Path("keyword") int courseId);
}
