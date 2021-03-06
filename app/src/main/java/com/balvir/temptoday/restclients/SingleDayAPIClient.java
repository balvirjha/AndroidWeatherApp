package com.balvir.temptoday.restclients;

import android.util.Log;

import com.balvir.temptoday.common.OpenWeatherClient;
import com.balvir.temptoday.common.SharedPrefUtil;
import com.balvir.temptoday.common.TempTodayApplication;
import com.balvir.temptoday.events.SingleDayWeatherEvent;
import com.balvir.temptoday.helpers.Helper;
import com.balvir.temptoday.modals.singledayweathermodal.SingleDayWeatherResponse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class SingleDayAPIClient implements Callback<SingleDayWeatherResponse> {
    private static final String TAG = SingleDayAPIClient.class.getSimpleName();

    public void getSingleDayWeatherResponse(String lat, String lon) {
        OpenWeatherClient.getOpenWeaterAPI().getSingleDayWeatherResponse(lat, lon, Helper.getApiKey(), "metric").enqueue(this);
        Log.d(TAG, "getSingleDayWeatherResponse api called");
    }

    @Override
    public void onResponse(Call<SingleDayWeatherResponse> call, Response<SingleDayWeatherResponse> response) {
        if (response.isSuccessful()) {
            Log.d(TAG, "Response Code: " + response.code());
            SharedPrefUtil.getInstance(TempTodayApplication.getInstance()).saveFirstRun(true);
            EventBus.getDefault().post(new SingleDayWeatherEvent.OnLoaded(response.body()));
        }

    }

    @Override
    public void onFailure(Call<SingleDayWeatherResponse> call, Throwable t) {
        Log.d(TAG, t.getMessage());
        EventBus.getDefault().post(new SingleDayWeatherEvent.OnLoadingError(t.getMessage(), -1));
    }
}
