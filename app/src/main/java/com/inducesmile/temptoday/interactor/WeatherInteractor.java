package com.inducesmile.temptoday.interactor;

import android.util.Log;

import com.inducesmile.temptoday.events.FiveDayWeatherEvent;
import com.inducesmile.temptoday.events.SingleDayWeatherEvent;
import com.inducesmile.temptoday.interfaces.IWeatherContract;
import com.inducesmile.temptoday.restclients.FiveDayAPIClient;
import com.inducesmile.temptoday.restclients.SingleDayAPIClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        mPresenter.setSingleWeatherData(onLoaded.getResponse());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onErrorSingleWeatherData(SingleDayWeatherEvent.OnLoadingError onLoadingError) {
        Log.d(TAG, "Error occured while fetchin single day data: " + onLoadingError.getErrorMessage());
    }

    @Override
    public void getSingledayWeatherResponse(String lat, String lon) {
        new SingleDayAPIClient().getSingleDayWeatherResponse(lat, lon);
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
    public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
