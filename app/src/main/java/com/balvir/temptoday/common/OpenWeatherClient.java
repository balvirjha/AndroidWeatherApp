package com.balvir.temptoday.common;


import com.balvir.temptoday.helpers.Helper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class OpenWeatherClient {
    private static OpenWeatherAPI openWeatherAPI;
    private static OkHttpClient okHttpClient;

    public static synchronized OpenWeatherAPI getOpenWeaterAPI() {
        if (openWeatherAPI == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .build();
            return new Retrofit.Builder().client(okHttpClient).baseUrl(Helper.getBaseAPi())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(OpenWeatherAPI.class);
        } else {
            return openWeatherAPI;
        }
    }

}
