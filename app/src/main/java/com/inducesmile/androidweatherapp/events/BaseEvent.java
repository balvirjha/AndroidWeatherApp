package com.inducesmile.androidweatherapp.events;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class BaseEvent {
    public static final String UNHANDLED_MSG = "UNHANDLED_MSG";
    public static final int UNHANDLED_CODE = -1;

    public BaseEvent() {
        //nothing to handle
    }

    /**
     * Signal for On API call started.
     *
     * @param <T>
     */
    protected static class OnStart<T> {
        private T t;

        public OnStart(T t) {
            this.t = t;
        }

        public T getRequest() {
            return t;
        }
    }

    /**
     * Signal for On API call successfully completed.
     *
     * @param <T>
     */
    protected static class OnDone<T> {

        private T t;

        public OnDone(T t) {
            this.t = t;
        }

        public T getResponse() {
            return t;
        }

    }

    /**
     * Signal for On API call failed.
     */
    protected static class OnFailed {

        private String mErrorMessage;
        private int mCode;

        public OnFailed(String errorMessage, int code) {
            mErrorMessage = errorMessage;
            mCode = code;
        }

        public String getErrorMessage() {
            return mErrorMessage;
        }

        public int getCode() {
            return mCode;
        }

    }


}
