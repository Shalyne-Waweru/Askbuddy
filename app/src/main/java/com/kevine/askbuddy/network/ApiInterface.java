package com.kevine.askbuddy.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded //annotation that used with POST type request
    @POST("loginUser.php") //api endpoint
    Call<String> loginApp(@Field("u_email") String email,
                          @Field("u_password") String password);

    @FormUrlEncoded //annotation that used with POST type request
    @POST("registerUser.php") //api endpoint
    Call<String> registerApp(@Field("u_bio") String bio,
                             @Field("u_email") String email,
                             @Field("u_password") String password,
                             @Field("u_imageurl") String imageurl,
                             @Field("u_name") String name,
                             @Field("u_username") String username);

    @FormUrlEncoded //annotation that used with POST type request
    @POST("createPost.php") //api endpoint
    Call<String> postApp(@Field("p_description") String description,
                          @Field("p_imageurl") String imageurl,
                         @Field("u_id") Integer id,
                         @Field("p_topic") String topic);

}
