package com.inducesmile.androidweatherapp.interfaces;

import android.location.Location;

public interface IWeatherContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter<Interactor> {
        void getSingledayWeatherresponse(String lat, String lon);
    }

    interface Interactor extends BaseInteractor {
        void getSingledayWeatherresponse(String lat, String lon);
    }
}
