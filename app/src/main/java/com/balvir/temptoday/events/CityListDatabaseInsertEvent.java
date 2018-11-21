package com.balvir.temptoday.events;

import com.balvir.temptoday.modals.cityweathermodal.CityListDataModal;

import java.util.List;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class CityListDatabaseInsertEvent extends BaseEvent {
    public static final CityListDatabaseInsertEvent.OnLoadingError FAILED
            = new CityListDatabaseInsertEvent.OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnLoaded extends BaseEvent.OnDone<String> {
        public OnLoaded(String status) {
            super(status);
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
