package com.balvir.temptoday.events;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class SingleDayDataUpdateEvent extends BaseEvent {
    public static final SingleDayDataUpdateEvent.OnLoadingError FAILED
            = new SingleDayDataUpdateEvent.OnLoadingError(UNHANDLED_MSG, UNHANDLED_CODE);

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
