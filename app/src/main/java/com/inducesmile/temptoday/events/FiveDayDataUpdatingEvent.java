package com.inducesmile.temptoday.events;

/**
 * Created by Balvir Jha on 11/19/18.
 */
public class FiveDayDataUpdatingEvent extends BaseEvent {
    public static final FiveDayDataUpdatingEvent.OnUpdatingDataError FAILED
            = new FiveDayDataUpdatingEvent.OnUpdatingDataError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnUpdatingDataError extends OnFailed {
        public OnUpdatingDataError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }

    public static class OnUpdatingDataSuccess extends OnDone<String> {
        public OnUpdatingDataSuccess(String status) {
            super(status);
        }
    }
}
