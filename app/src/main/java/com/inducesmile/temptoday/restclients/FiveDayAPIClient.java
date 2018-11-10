package com.inducesmile.temptoday.restclients;

import android.util.Log;

import com.inducesmile.temptoday.common.TempTodayApplication;
import com.inducesmile.temptoday.events.FiveDayWeatherEvent;
import com.inducesmile.temptoday.helpers.Helper;
import com.inducesmile.temptoday.modals.json.Forecast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Balvir Jha on 11/10/18.
 */

public class FiveDayAPIClient implements Callback<Forecast> {
    private static final String TAG = FiveDayAPIClient.class.getSimpleName();

    public void getFiveDayWeatherResponse(String cityName) {
        OpenWeatherClient.getOpenWeaterAPI(new Cache(
                new File(TempTodayApplication.getInstance().getCacheDir(), "responses"),
                10 * 1024 * 1024)).getFiveDayWeatherResponse(cityName, Helper.getApiKey(), "metric").enqueue(this);
        Log.d(TAG, "getSingleDayWeatherResponse api called");
    }

    @Override
    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
        if (response.isSuccessful()) {
            Log.d(TAG, "Response Code: " + response.code());
            EventBus.getDefault().post(new FiveDayWeatherEvent.OnLoaded(response.body()));
        }

    }

    @Override
    public void onFailure(Call<Forecast> call, Throwable t) {
        Log.d(TAG, t.getMessage());
        EventBus.getDefault().post(new FiveDayWeatherEvent.OnLoadingError(t.getMessage(), -1));
    }
}