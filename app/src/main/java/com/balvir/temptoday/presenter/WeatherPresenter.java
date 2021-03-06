package com.balvir.temptoday.presenter;

import android.Manifest;
import android.app.Service;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.balvir.temptoday.common.SharedPrefUtil;
import com.balvir.temptoday.common.TempTodayApplication;
import com.balvir.temptoday.entity.WeatherObject;
import com.balvir.temptoday.helpers.Helper;
import com.balvir.temptoday.interfaces.IWeatherContract;
import com.balvir.temptoday.modals.json.Forecast;
import com.balvir.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;
import com.balvir.temptoday.modals.singledayweathermodal.SingleDayWeatherResponse;
import com.balvir.temptoday.views.WeatherActivity;

import java.util.List;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class WeatherPresenter implements IWeatherContract.Presenter, LocationListener {

    private static final String TAG = WeatherPresenter.class.getSimpleName();
    private IWeatherContract.View mWeatherView;
    private IWeatherContract.Interactor mWeatherInteractor;

    private Location location;

    private LocationManager locationManager;

    public WeatherPresenter(IWeatherContract.View view) {
        mWeatherView = view;
        mWeatherView.setPresenter(this);
    }

    @Override
    public void setInteractor(IWeatherContract.Interactor interactor) {
        mWeatherInteractor = interactor;
    }

    @Override
    public void start() {
        mWeatherInteractor.start();
    }

    @Override
    public void getFiveDayWeatherresponse(String cityName) {
        mWeatherInteractor.getFivedayWeatherResponse(cityName);
    }

    @Override
    public void setFiveDayWeatherData(Forecast fiveDayWeatherData) {
        mWeatherView.setFiveDayWeatherData(fiveDayWeatherData);
    }

    @Override
    public void setSingleWeatherData(SingleDayWeatherResponse singleWeatherData) {
        mWeatherView.saveSingleWeatherData(singleWeatherData);
    }

    @Override
    public void getSingledayWeatherresponse(String lat, String lon) {
        mWeatherInteractor.getSingledayWeatherResponse(lat, lon);
    }

    @Override
    public void initiateLocationFetch() {
        locationManager = (LocationManager) ((WeatherActivity) mWeatherView).getSystemService(Service.LOCATION_SERVICE);
    }

    @Override
    public boolean lcheckLocationPermission() {
        return (ActivityCompat.checkSelfPermission(((WeatherActivity) mWeatherView), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(((WeatherActivity) mWeatherView), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void requestLocationPermission() {
        ActivityCompat.requestPermissions(((WeatherActivity) mWeatherView),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, Helper.REQUEST_LOCATION);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        SharedPrefUtil.getInstance(TempTodayApplication.getInstance()).saveCurrentLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        if (!SharedPrefUtil.getInstance(TempTodayApplication.getInstance()).isFirstRun()) {
            callSingleDayWeatherDataAPI();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            mWeatherView.showGPSDisabledAlertToUser();
        }
    }

    @Override
    public void requestLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);

            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (!SharedPrefUtil.getInstance(TempTodayApplication.getInstance()).isFirstRun()) {
                    callSingleDayWeatherDataAPI();
                    //SharedPrefUtil.getInstance(TempTodayApplication.getInstance()).saveFirstRun(false);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void callSingleDayWeatherDataAPI() {
        if (location != null) {
            mWeatherView.callSingleDayWeatherAPI(location);
        }
    }

    @Override
    public void insertSingleDayData(SingleDatWeatherModal datWeatherModal) {
        mWeatherInteractor.insertSingleDayData(datWeatherModal);
    }

    @Override
    public void onSuccessSingleDayData(String status) {
        Log.d(TAG, "inserting single data data successfull");
    }

    @Override
    public void onErrorSingleDayData(String status) {
        Log.d(TAG, "error while inserting single data");
    }

    @Override
    public void fetchAllSingleDayData() {
        mWeatherInteractor.fetchAllSingleDayData();
    }

    @Override
    public void onSucessOfFetchingSingleDayData(SingleDatWeatherModal singleDatWeatherModal) {
        mWeatherView.displaySingleWeatherData(singleDatWeatherModal);
    }

    @Override
    public void onFailureOfFetchingSingleDayData(String status) {
        Log.d(TAG, "error while getting single data");
    }

    @Override
    public void fetchAllFiveDayData() {
        mWeatherInteractor.fetchAllFiveDayData();
    }

    @Override
    public void fetchingAllFiveDayDataSuccess(List<WeatherObject> weatherObjectList) {
        mWeatherView.fetchingAllFiveDayDataSuccess(weatherObjectList);
    }

    @Override
    public void fetchingAllFiveDayDataError(String status) {
        Log.d(TAG, "error while fetching all five day data: " + status);
    }

    @Override
    public void stop() {
        mWeatherInteractor.stop();
    }
}
