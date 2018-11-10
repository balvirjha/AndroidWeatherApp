package com.inducesmile.androidweatherapp.interactor;

import com.inducesmile.androidweatherapp.interfaces.IWeatherContract;
import com.inducesmile.androidweatherapp.restclients.SingleDayAPIClient;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class WeatherInteractor implements IWeatherContract.Interactor {

    private IWeatherContract.Presenter mPresenter;

    public WeatherInteractor(IWeatherContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setInteractor(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getSingledayWeatherresponse(String lat, String lon) {
        new SingleDayAPIClient().getSingleDayWeatherResponse(lat, lon);
    }

    @Override
    public void stop() {

    }
}
