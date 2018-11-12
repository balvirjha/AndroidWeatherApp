package com.inducesmile.temptoday.events;

import com.inducesmile.temptoday.modals.cityweathermodal.CityListDataModal;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;

import java.util.List;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class SingleDayDataFetchEvent extends BaseEvent {
    public static final SingleDayDataFetchEvent.OnLoadingError FAILED
            = new SingleDayDataFetchEvent.OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnLoaded extends BaseEvent.OnDone<SingleDatWeatherModal> {
        public OnLoaded(SingleDatWeatherModal singleDatWeatherModal) {
            super(singleDatWeatherModal);
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
