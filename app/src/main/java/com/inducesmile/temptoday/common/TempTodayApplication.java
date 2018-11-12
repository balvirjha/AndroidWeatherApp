package com.inducesmile.temptoday.common;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.inducesmile.temptoday.helpers.Helper;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class TempTodayApplication extends Application {

    private static TempTodayApplication sInstance;
    private static volatile WeatherDataBase INSTANCE;

    public static TempTodayApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static WeatherDataBase getDatabase() {
        if (INSTANCE == null) {
            synchronized (WeatherDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(sInstance,
                            WeatherDataBase.class, Helper.DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
