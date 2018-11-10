package com.inducesmile.temptoday.modals.json;


public class Weather {

    WeatherResults weatherResults;

    public Weather(WeatherResults weatherResults) {
        this.weatherResults = weatherResults;
    }

    public WeatherResults getWeatherResults() {
        return weatherResults;
    }
}
