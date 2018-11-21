package com.balvir.temptoday.events;

import com.balvir.temptoday.modals.cityweathermodal.CityListDataModal;

import java.util.List;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class CityListDatabaseFetchEvent extends BaseEvent {
    public static final CityListDatabaseFetchEvent.OnLoadingError FAILED
            = new CityListDatabaseFetchEvent.OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnLoaded extends BaseEvent.OnDone<List<CityListDataModal>> {
        public OnLoaded(List<CityListDataModal> cityWeatherResults) {
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
