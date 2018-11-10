package com.inducesmile.temptoday.common;

import android.app.Application;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class TempTodayApplication extends Application {

    private static TempTodayApplication sInstance;

    public static TempTodayApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

}
