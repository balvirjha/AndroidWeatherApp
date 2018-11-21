package com.balvir.temptoday.common;


import com.balvir.temptoday.helpers.Helper;
import com.balvir.temptoday.modals.cityweathermodal.CityWeatherResult;
import com.balvir.temptoday.modals.json.Forecast;
import com.balvir.temptoday.modals.singledayweathermodal.SingleDayWeatherResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public interface OpenWeatherAPI {

    @Headers("Accept: application/json")
    @GET(Helper.SINGLEDAY_WEATHERDATA_ENDPOINT)
    Call<SingleDayWeatherResponse> getSingleDayWeatherResponse(@Query("lat") String lat,
                                                               @Query("lon") String lon,
                                                               @Query("APPID") String appid,
                                                               @Query("units") String units);

    @Headers("Accept: application/json")
    @GET(Helper.FIVEDAY_WEATHERDATA_ENDPOINT)
    Call<Forecast> getFiveDayWeatherResponse(@Query("q") String cityName,
                                             @Query("APPID") String appid,
                                             @Query("units") String units);


    @Headers("Accept: application/json")
    @GET(Helper.CITYNAME_WEATHERDATA_ENDPOINT)
    Observable<CityWeatherResult> getWeatherDataByCityName(@Query("q") String cityName,
                                                           @Query("APPID") String appid);
}
