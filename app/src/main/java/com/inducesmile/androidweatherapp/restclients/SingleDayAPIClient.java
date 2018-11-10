package com.inducesmile.androidweatherapp.restclients;

import android.util.Log;

import com.inducesmile.androidweatherapp.helpers.Helper;
import com.inducesmile.androidweatherapp.common.TempTodayApplication;
import com.inducesmile.androidweatherapp.modals.SingleDayWeatherResponse;

import java.io.File;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleDayAPIClient implements Callback<SingleDayWeatherResponse> {
    private static final String TAG = SingleDayAPIClient.class.getSimpleName();

    public void getSingleDayWeatherResponse(String lat, String lon) {
        OpenWeatherClient.getOpenWeaterAPI(new Cache(
                new File(TempTodayApplication.getInstance().getCacheDir(), "responses"),
                10 * 1024 * 1024)).getSingleDayWeatherResponse(lat, lon, Helper.API_KEY, "metric").enqueue(this);
        Log.d(TAG, "getSingleDayWeatherResponse api called");
    }

    @Override
    public void onResponse(Call<SingleDayWeatherResponse> call, Response<SingleDayWeatherResponse> response) {
        if (response.isSuccessful()) {
            Log.d(TAG, "Response Code: " + response.code());
        }

    }

    @Override
    public void onFailure(Call<SingleDayWeatherResponse> call, Throwable t) {
        Log.d(TAG, t.getMessage());
    }
}
