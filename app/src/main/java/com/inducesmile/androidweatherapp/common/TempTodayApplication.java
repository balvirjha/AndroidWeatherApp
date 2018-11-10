package com.inducesmile.androidweatherapp.common;

import android.app.Application;

public class TempTodayApplication extends Application {

    private static TempTodayApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static TempTodayApplication getInstance() {
        return sInstance;
    }

}
