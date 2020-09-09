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
    Call<String> createPost(@Field("p_description") String description,
                          @Field("p_imageurl") String imageurl,
                         @Field("u_id") String id,
                         @Field("p_topic") String topic);

    @FormUrlEncoded //annotation that used with POST type request
    @POST("fetchPost.php") //api endpoint
    Call<String> fetchPostByUserID(@Field("u_id") String u_id);

    @FormUrlEncoded //annotation that used with POST type request
    @POST("updatePost.php") //api endpoint
    Call<String> updatePost(@Field("p_id") String p_id,
                            @Field("p_description") String p_description);

    @FormUrlEncoded //annotation that used with POST type request
    @POST("deletePost.php") //api endpoint
    Call<String> deletePost(@Field("p_id") String p_id);

    @FormUrlEncoded //annotation that used with POST type request
    @POST("createComment.php") //api endpoint
    Call<String> createComment(@Field("p_id") String p_id,
                                   @Field("u_id") String u_id,
                                   @Field("comment") String comment);
}
