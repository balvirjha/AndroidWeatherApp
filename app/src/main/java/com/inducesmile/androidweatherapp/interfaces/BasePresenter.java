package com.inducesmile.androidweatherapp.interfaces;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public interface BasePresenter<T> {

    void setInteractor(T interactor);

    void start();

    void stop();
}
