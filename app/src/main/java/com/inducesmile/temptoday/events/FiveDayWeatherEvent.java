package com.inducesmile.temptoday.events;

import com.inducesmile.temptoday.modals.json.Forecast;

/**
 * Created by Balvir Jha on 11/10/18.
 */
public class FiveDayWeatherEvent extends BaseEvent {
    public static final FiveDayWeatherEvent.OnLoadingError FAILED
            = new FiveDayWeatherEvent.OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnLoaded extends OnDone<Forecast> {
        public OnLoaded(Forecast myProfileResponse) {
            super(myProfileResponse);
        }
    }

    public static class OnLoadingStart extends OnStart<String> {
        public OnLoadingStart(String message) {
            super(message);
        }
    }

    public static class OnLoadingError extends OnFailed {
        public OnLoadingError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }
}
