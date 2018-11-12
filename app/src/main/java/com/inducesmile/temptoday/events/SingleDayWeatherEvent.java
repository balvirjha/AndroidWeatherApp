package com.inducesmile.temptoday.events;

import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDayWeatherResponse;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class SingleDayWeatherEvent extends BaseEvent {
    public static final OnLoadingError FAILED
            = new OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnLoaded extends OnDone<SingleDayWeatherResponse> {
        public OnLoaded(SingleDayWeatherResponse myProfileResponse) {
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
