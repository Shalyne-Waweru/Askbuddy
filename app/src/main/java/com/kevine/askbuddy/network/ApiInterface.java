package com.kevine.askbuddy.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("loginUser.php")
    Call<String> loginApp(@Field("u_email") String email,
                          @Field("u_password") String password);

}
