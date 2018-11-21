package com.balvir.temptoday.helpers;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class Helper {

    public static final String MANAGER_LOCATION = "Manager Location";

    public static final String LOCATION_LIST = "Location List";

    public static final String LOCATION_ERROR_MESSAGE = "Input field must be filled";

    public static final String PREFS_TAG = "prefs";

    public static final String STORED_DATA_FIRST = "data_first";

    public static final String STORED_DATA_SECOND = "data_second";

    public static final String DATABASE_NAME = "weather_database";

    public static final String IS_DATA_PRESENT = "isData";

    public static final String LOCATION_PREFS = "location_prefs";

    public static final String SINGLEDAY_WEATHERDATA_ENDPOINT = "weather";

    public static final String FIVEDAY_WEATHERDATA_ENDPOINT = "forecast";

    public static final String CITYNAME_WEATHERDATA_ENDPOINT = "forecast";

    public static final String SUCCESS_STATUS = "success";

    public static final String FAILURE_STATUS = "failed";

    public static final int REQUEST_LOCATION = 200;

    private static final String API_KEY = "a363a2b2ee3e14ddb617d983afe213f0";

    private static final String BASE_API = "http://api.openweathermap.org/data/2.5/";

    public static final String IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work";

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getBaseAPi() {
        return BASE_API;
    }


    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
