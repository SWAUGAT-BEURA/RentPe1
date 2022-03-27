package com.sanjeevani.rent.Api;



import com.sanjeevani.rent.Models.House;
import com.sanjeevani.rent.Models.LoginToken;
import com.sanjeevani.rent.Models.UserInfo;
import com.sanjeevani.rent.Models.UserRequest;
import com.sanjeevani.rent.Models.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("/apilogin/")
    Call<ArrayList<UserInfo>> getuserdetails();

    @POST("/apiregister/")
    Call<UserResponse> registerUser(@Body UserRequest userRequest);

    @FormUrlEncoded
    @POST("/apilogin/")
    Call<LoginToken> loginUser(@Field("username") String username, @Field("password") String password);

    @GET("/House_RentInfo")
    Call<ArrayList<House>> getHouses();






}