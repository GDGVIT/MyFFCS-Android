package com.dscvit.android.myffcs.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiModel {

    @GET("classroom/search")
    Call<List<ClassroomResponse>> searchCourses(@Query("code") String courseCode, @Query("faculty") String faculty, @Query("title") String courseTitle, @Query("slot") String slot);

    @FormUrlEncoded
    @POST("user/{keyword}/add-timetable")
    Call<String> addTimeTable(@Path("keyword") String userId, @Field("timetable") int[] courseIds);

    @FormUrlEncoded
    @POST("user/add")
    Call<String> addUser(@Field("uid") String userId, @Field("userName") String userName);

    @GET("myffcs.json")
    Call<List<ClassroomResponse>> getAllCourses();
}
