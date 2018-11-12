package com.inducesmile.temptoday.interactor;

import android.util.Log;

import com.inducesmile.temptoday.common.SharedPrefUtil;
import com.inducesmile.temptoday.common.TempTodayApplication;
import com.inducesmile.temptoday.events.FiveDayWeatherEvent;
import com.inducesmile.temptoday.events.SigleDayDataInsertEvent;
import com.inducesmile.temptoday.events.SingleDayDataFetchEvent;
import com.inducesmile.temptoday.events.SingleDayWeatherEvent;
import com.inducesmile.temptoday.helpers.Helper;
import com.inducesmile.temptoday.interfaces.IWeatherContract;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDayWeatherResponse;
import com.inducesmile.temptoday.repositories.SIngleDayDataRepositories;
import com.inducesmile.temptoday.restclients.FiveDayAPIClient;
import com.inducesmile.temptoday.workers.SingleDayAPIDownloader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class WeatherInteractor implements IWeatherContract.Interactor {

    private static final String TAG = WeatherInteractor.class.getSimpleName();
    private IWeatherContract.Presenter mPresenter;

    public WeatherInteractor(IWeatherContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setInteractor(this);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onSuccessSingleWeatherData(SingleDayWeatherEvent.OnLoaded onLoaded) {
        SingleDayWeatherResponse singleDayWeatherResponse = onLoaded.getResponse();
        SingleDatWeatherModal singleDatWeatherModal = new SingleDatWeatherModal(singleDayWeatherResponse.getName(),
                singleDayWeatherResponse.getWind().getSpeed(),
                new Double(singleDayWeatherResponse.getMain().getHumidity()),
                new SimpleDateFormat("HH:mm").format(new java.util.Date(new Long(singleDayWeatherResponse.getSys().getSunrise()) * 1000)),
                new SimpleDateFormat("HH:mm").format(new java.util.Date(new Long(singleDayWeatherResponse.getSys().getSunset()) * 1000)),
                new Double(Math.round(Math.floor(Double.parseDouble(String.valueOf(singleDayWeatherResponse.getMain().getTemp()))))),
                Helper.capitalizeFirstLetter(singleDayWeatherResponse.getWeather().get(0).getDescription()));
        SharedPrefUtil.getInstance(TempTodayApplication.getInstance()).saveCurrentCity(singleDayWeatherResponse.getName());
        insertSingleDayData(singleDatWeatherModal);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onErrorSingleWeatherData(SingleDayWeatherEvent.OnLoadingError onLoadingError) {
        Log.d(TAG, "Error occured while fetchin single day data: " + onLoadingError.getErrorMessage());
    }

    @Override
    public void getSingledayWeatherResponse(String lat, String lon) {
        WorkManager.getInstance().enqueue(new PeriodicWorkRequest.Builder(SingleDayAPIDownloader.class, 15, TimeUnit.MINUTES).
                setConstraints(new Constraints.Builder().setRequiresBatteryNotLow(true).build()).
                build());
        //new SingleDayAPIClient().getSingleDayWeatherResponse(lat, lon);
    }

    @Override
    public void getFivedayWeatherResponse(String cityName) {
        new FiveDayAPIClient().getFiveDayWeatherResponse(cityName);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onSuccessFiveWeatherData(FiveDayWeatherEvent.OnLoaded onLoaded) {
        mPresenter.setFiveDayWeatherData(onLoaded.getResponse());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onErrorFiveWeatherData(FiveDayWeatherEvent.OnLoadingError onLoadingError) {
        Log.d(TAG, "Error occured while fetchin five day data: " + onLoadingError.getErrorMessage());
    }

    @Override
    public void insertSingleDayData(SingleDatWeatherModal datWeatherModal) {
        new SIngleDayDataRepositories().insert(datWeatherModal);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onSuccessSingleDayData(SigleDayDataInsertEvent.OnLoaded onLoaded) {
        fetchAllSingleDayData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onErrorSingleDayData(SigleDayDataInsertEvent.OnLoadingError onLoaded) {
        mPresenter.onErrorSingleDayData(onLoaded.getErrorMessage());
    }

    @Override
    public void fetchAllSingleDayData() {
        new SIngleDayDataRepositories().fetchAllSingleDayData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onSucessOfFetchingSingleDayData(SingleDayDataFetchEvent.OnLoaded onLoaded) {
        mPresenter.onSucessOfFetchingSingleDayData(onLoaded.getResponse());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onFailureOfFetchingSingleDayData(SingleDayDataFetchEvent.OnLoadingError onLoadingError) {
        Log.d(TAG, "Error occured while fetchin single day data: " + onLoadingError.getErrorMessage());
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
