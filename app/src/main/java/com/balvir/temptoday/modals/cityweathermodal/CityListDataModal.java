package com.balvir.temptoday.modals.cityweathermodal;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Balvir Jha on 11/12/18.
 */

@Entity(tableName = "citydatamodal")
public class CityListDataModal {
    @PrimaryKey
    @NonNull
    private String city;
    private Double temp;
    private Double wind;
    private Double humidity;
    private String date;

    public CityListDataModal(@NonNull String city, Double temp, Double wind, Double humidity, String date) {
        this.city = city;
        this.temp = temp;
        this.wind = wind;
        this.humidity = humidity;
        this.date = date;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getWind() {
        return wind;
    }

    public void setWind(Double wind) {
        this.wind = wind;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
