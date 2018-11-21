package com.balvir.temptoday.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by BalvirJha on 10-11-2018.
 */

@Entity(tableName = "fivedayweatherobject")
public class WeatherObject {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int _id;

    private String dayOfWeek;

    private int weatherIcon;

    private String weatherResult;

    private String weatherResultSmall;

    public WeatherObject(String dayOfWeek, int weatherIcon, String weatherResult, String weatherResultSmall) {
        this.dayOfWeek = dayOfWeek;
        this.weatherIcon = weatherIcon;
        this.weatherResult = weatherResult;
        this.weatherResultSmall = weatherResultSmall;
    }

    @NonNull
    public int get_id() {
        return _id;
    }

    public void set_id(@NonNull int _id) {
        this._id = _id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public String getWeatherResult() {
        return weatherResult;
    }

    public void setWeatherResult(String weatherResult) {
        this.weatherResult = weatherResult;
    }

    public String getWeatherResultSmall() {
        return weatherResultSmall;
    }
}
