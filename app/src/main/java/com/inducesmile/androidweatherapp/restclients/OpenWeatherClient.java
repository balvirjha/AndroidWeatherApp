package com.inducesmile.androidweatherapp.restclients;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherClient {
    private static OpenWeatherAPI openWeatherAPI, openWeatherAuthenticatorAPI;
    private static OkHttpClient okHttpClient;

    public static synchronized OpenWeatherAPI getOpenWeaterAPI(Cache cache) {
        if (openWeatherAPI == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .build();
            return new Retrofit.Builder().client(okHttpClient).baseUrl("http://api.openweathermap.org/data/2.5/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).build().create(OpenWeatherAPI.class);
        } else {
            return openWeatherAPI;
        }
    }

}
