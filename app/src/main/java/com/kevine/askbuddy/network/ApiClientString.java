package com.kevine.askbuddy.network;


import com.kevine.askbuddy.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClientString {

    public static final String BASE_URL = "http://gatesystem.rongaiws.com/askbuddyapi/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        int KEY_TIMEOUT = 240;
        builder.readTimeout(KEY_TIMEOUT, TimeUnit.SECONDS).connectTimeout(KEY_TIMEOUT, TimeUnit.SECONDS).writeTimeout(KEY_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.networkInterceptors().add(httpLoggingInterceptor);
        }

        builder.retryOnConnectionFailure(true);
        OkHttpClient okHttpClient = builder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

}