package com.inducesmile.androidweatherapp.presenter;

import android.location.Location;

import com.inducesmile.androidweatherapp.interfaces.IWeatherContract;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class WeatherPresenter implements IWeatherContract.Presenter {

    private IWeatherContract.View mWeatherView;
    private IWeatherContract.Interactor mWeatherInteractor;

    public WeatherPresenter(IWeatherContract.View view) {
        mWeatherView = view;
        mWeatherView.setPresenter(this);
    }

    @Override
    public void setInteractor(IWeatherContract.Interactor interactor) {
        mWeatherInteractor = interactor;
    }

    @Override
    public void start() {
        mWeatherInteractor.start();
    }

    @Override
    public void getSingledayWeatherresponse(String lat, String lon) {
        mWeatherInteractor.getSingledayWeatherresponse(lat, lon);
    }

    @Override
    public void stop() {
        mWeatherInteractor.stop();
    }
}
