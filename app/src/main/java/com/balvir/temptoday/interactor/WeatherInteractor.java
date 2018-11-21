package com.balvir.temptoday.interactor;

import android.util.Log;

import com.balvir.temptoday.R;
import com.balvir.temptoday.common.SharedPrefUtil;
import com.balvir.temptoday.common.TempTodayApplication;
import com.balvir.temptoday.common.Utils;
import com.balvir.temptoday.entity.WeatherObject;
import com.balvir.temptoday.events.FiveDayDataFetchingEvent;
import com.balvir.temptoday.events.FiveDayDataInsertionEvent;
import com.balvir.temptoday.events.FiveDayWeatherEvent;
import com.balvir.temptoday.events.SigleDayDataInsertEvent;
import com.balvir.temptoday.events.SingleDayDataFetchEvent;
import com.balvir.temptoday.events.SingleDayWeatherEvent;
import com.balvir.temptoday.helpers.Helper;
import com.balvir.temptoday.interfaces.IWeatherContract;
import com.balvir.temptoday.modals.json.FiveWeathers;
import com.balvir.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;
import com.balvir.temptoday.modals.singledayweathermodal.SingleDayWeatherResponse;
import com.balvir.temptoday.repositories.FiveDayDataRepositories;
import com.balvir.temptoday.repositories.SIngleDayDataRepositories;
import com.balvir.temptoday.workers.FiveDayDataApiDownloader;
import com.balvir.temptoday.workers.SingleDayAPIDownloader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
        WorkManager.getInstance().enqueue(new PeriodicWorkRequest.Builder(SingleDayAPIDownloader.class, 3, TimeUnit.HOURS).
                setConstraints(new Constraints.Builder().setRequiresBatteryNotLow(true).build()).
                build());
    }

    @Override
    public void getFivedayWeatherResponse(String cityName) {
        WorkManager.getInstance().enqueue(new PeriodicWorkRequest.Builder(FiveDayDataApiDownloader.class, 24, TimeUnit.HOURS).
                setConstraints(new Constraints.Builder().setRequiresBatteryNotLow(true).build()).
                build());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onSuccessFiveWeatherData(FiveDayWeatherEvent.OnLoaded onLoaded) {
        List<FiveWeathers> weatherInfo = onLoaded.getResponse().getList();
        List<WeatherObject> weatherObjects = new ArrayList<>();
        if (null != weatherInfo) {
            for (int i = 0; i < weatherInfo.size(); i++) {
                String time = weatherInfo.get(i).getDt_txt();
                String shortDay = Utils.convertTimeToDay(time);

                String temp = String.valueOf(new Double(Math.round(Math.floor(Double.parseDouble(String.valueOf(weatherInfo.get(i).getMain().getTemp()))))));
                String tempMin = String.valueOf(new Double(Math.round(Math.floor(Double.parseDouble(String.valueOf(weatherInfo.get(i).getMain().getTemp_min()))))));
                weatherObjects.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
            }
            new FiveDayDataRepositories().insert(weatherObjects);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void insertingDBFiveDayDataSuccess(FiveDayDataInsertionEvent.OnInsertingDataSuccess onInsertingDataSuccess) {
        fetchAllFiveDayData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void insertingDBFiveDayDataError(FiveDayDataInsertionEvent.OnInsertingDataError onInsertingDataError) {
        Log.d(TAG, "Error occured while inserting five day data: " + onInsertingDataError.getErrorMessage());
    }

    @Override
    public void fetchAllFiveDayData() {
        new FiveDayDataRepositories().fetchAllFiveDayData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void fetchingAllFiveDayDataSuccess(FiveDayDataFetchingEvent.OnFetchingDataSuccess onFetchingDataSuccess) {
        mPresenter.fetchingAllFiveDayDataSuccess(onFetchingDataSuccess.getResponse());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void fetchingAllFiveDayDataError(FiveDayDataFetchingEvent.OnFetchingDataError onFetchingDataError) {
        mPresenter.fetchingAllFiveDayDataError(onFetchingDataError.getErrorMessage());
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
