package com.inducesmile.temptoday.events;

/**
 * Created by Balvir Jha on 11/19/18.
 */
public class FiveDayDataInsertionEvent extends BaseEvent {
    public static final FiveDayDataInsertionEvent.OnInsertingDataError FAILED
            = new FiveDayDataInsertionEvent.OnInsertingDataError(UNHANDLED_MSG, UNHANDLED_CODE);

    public static class OnInsertingDataSuccess extends OnDone<String> {
        public OnInsertingDataSuccess(String status) {
            super(status);
        }
    }

    public static class OnInsertingDataError extends OnFailed {
        public OnInsertingDataError(String errorMessage, int code) {
            super(errorMessage, code);
        }
    }

}
