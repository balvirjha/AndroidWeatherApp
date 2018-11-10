package com.inducesmile.temptoday.interfaces;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public interface IBasePresenter<T> {

    void setInteractor(T interactor);

    void start();

    void stop();
}
