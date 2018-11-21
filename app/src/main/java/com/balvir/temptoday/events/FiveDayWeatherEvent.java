package com.balvir.temptoday.events;

import com.balvir.temptoday.modals.json.Forecast;

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

    public static class OnLoadingError extends OnFailed {
        public OnLoadingError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }
}
