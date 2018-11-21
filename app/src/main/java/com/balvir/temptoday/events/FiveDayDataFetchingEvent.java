package com.balvir.temptoday.events;

import com.balvir.temptoday.entity.WeatherObject;

import java.util.List;

/**
 * Created by Balvir Jha on 11/19/18.
 */
public class FiveDayDataFetchingEvent extends BaseEvent {
    public static final FiveDayDataFetchingEvent.OnFetchingDataError FAILED
            = new FiveDayDataFetchingEvent.OnFetchingDataError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnFetchingDataSuccess extends OnDone<List<WeatherObject>> {
        public OnFetchingDataSuccess(List<WeatherObject> weatherObjectList) {
            super(weatherObjectList);
        }
    }

    public static class OnFetchingDataError extends OnFailed {
        public OnFetchingDataError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }

}
