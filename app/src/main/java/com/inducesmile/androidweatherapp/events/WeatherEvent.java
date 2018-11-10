package com.inducesmile.androidweatherapp.events;

import com.inducesmile.androidweatherapp.modals.SingleDayWeatherResponse;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class WeatherEvent extends BaseEvent {
    public static final OnLoadingError FAILED
            = new OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    //Success
    public static class OnLoaded extends OnDone<SingleDayWeatherResponse> {
        public OnLoaded(SingleDayWeatherResponse myProfileResponse) {
            super(myProfileResponse);
        }
    }

    //On started
    public static class OnLoadingStart extends OnStart<String> {
        public OnLoadingStart(String message) {
            super(message);
        }
    }

    //Failed
    public static class OnLoadingError extends OnFailed {
        public OnLoadingError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }
}
