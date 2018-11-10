package com.inducesmile.temptoday.restclients;


import com.inducesmile.temptoday.helpers.Helper;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class OpenWeatherClient {
    private static OpenWeatherAPI openWeatherAPI, openWeatherAuthenticatorAPI;
    private static OkHttpClient okHttpClient;

    public static synchronized OpenWeatherAPI getOpenWeaterAPI(Cache cache) {
        if (openWeatherAPI == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .build();
            return new Retrofit.Builder().client(okHttpClient).baseUrl(Helper.getBaseAPi())
                    .addConverterFactory(GsonConverterFactory.create()).build().create(OpenWeatherAPI.class);
        } else {
            return openWeatherAPI;
        }
    }

}
