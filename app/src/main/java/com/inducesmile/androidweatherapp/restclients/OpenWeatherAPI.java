package com.inducesmile.androidweatherapp.restclients;

import com.inducesmile.androidweatherapp.modals.SingleDayWeatherResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OpenWeatherAPI {

    /*@Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );*/

    @Headers("Accept: application/json")
    @GET("weather")
    Call<SingleDayWeatherResponse> getSingleDayWeatherResponse(@Query("lat") String lat,
                                                               @Query("lon") String lon,
                                                               @Query("APPID") String appid,
                                                               @Query("units") String units);

    /*@Headers("Accept: application/json")
    @GET("/user/subscriptions")
    Observable<List<UserReposWatching>> getWatchingRepos(@Query("access_token") String access_token);

    @Headers("Accept: application/json")
    @GET("/user/starred")
    Observable<List<UserStarredRepo>> getStarredRepos(@Query("access_token") String access_token);*/
}
