package com.inducesmile.temptoday.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.inducesmile.temptoday.R;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class SharedPrefUtil {

    private static Context context;
    private static SharedPreferences sharedPref;

    private static SharedPrefUtil sharedPrefUtil = new SharedPrefUtil();

    public static synchronized SharedPrefUtil getInstance(Context cont) {
        context = cont;
        if (sharedPrefUtil == null) {
            sharedPrefUtil = new SharedPrefUtil();
        }
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(context.getResources().getString(R.string.sharedPrefName), Context.MODE_PRIVATE);
        }
        return sharedPrefUtil;
    }

    public void saveCurrentLocation(String lat, String lon) {
        sharedPref.edit().putString(context.getResources().getString(R.string.lattitude), lat).
                putString(context.getResources().getString(R.string.longitude), lon).
                commit();
    }

    public String getCurrentLocation() {

        return sharedPref.getString(context.getResources().getString(R.string.lattitude), "") + "-" +
                sharedPref.getString(context.getResources().getString(R.string.longitude), "");
    }

    public void saveCurrentCity(String cityName) {
        sharedPref.edit().putString(context.getResources().getString(R.string.cityname), cityName).
                commit();
    }

    public String getCurrentCity() {
        return sharedPref.getString(context.getResources().getString(R.string.cityname), "");
    }

    public void saveFirstRun(boolean isFirstRun) {
        sharedPref.edit().putBoolean(context.getResources().getString(R.string.isFirstRun), isFirstRun).
                commit();
    }

    public boolean isFirstRun() {
        return sharedPref.getBoolean(context.getResources().getString(R.string.isFirstRun), false);
    }
}
