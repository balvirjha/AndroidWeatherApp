package com.balvir.temptoday.modals.singledayweathermodal;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Balvir Jha on 11/12/18.
 */

@Entity(tableName = "singledaydatamodal")
public class SingleDatWeatherModal {

    @PrimaryKey
    @NonNull
    String cityName;
    Double windSpeed;
    Double humidity;
    String sunrise;
    String sunset;
    Double temp;
    String weatherDescription;

    public SingleDatWeatherModal(@NonNull String cityName, Double windSpeed,
                                 Double humidity, String sunrise, String sunset,
                                 Double temp, String weatherDescription) {
        this.cityName = cityName;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.weatherDescription = weatherDescription;
    }

    @NonNull
    public String getCityName() {
        return cityName;
    }

    public void setCityName(@NonNull String cityName) {
        this.cityName = cityName;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }
}
