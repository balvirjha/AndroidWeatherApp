package com.balvir.temptoday.events;

import com.balvir.temptoday.modals.cityweathermodal.CityWeatherResult;

import java.util.List;

/**
 * Created by Balvir Jha on 11/11/18.
 */
public class CityNameWeatherEvent extends BaseEvent {
    public static final CityNameWeatherEvent.OnLoadingError FAILED
            = new CityNameWeatherEvent.OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnLoaded extends BaseEvent.OnDone<List<CityWeatherResult> > {
        public OnLoaded(List<CityWeatherResult> cityWeatherResults) {
            super(cityWeatherResults);
        }
    }

    public static class OnLoadingStart extends BaseEvent.OnStart<String> {
        public OnLoadingStart(String message) {
            super(message);
        }
    }

    public static class OnLoadingError extends BaseEvent.OnFailed {
        public OnLoadingError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }
}
