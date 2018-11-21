package com.balvir.temptoday.interactor;

import android.util.Log;

import com.balvir.temptoday.events.CityListDatabaseFetchEvent;
import com.balvir.temptoday.events.CityListDatabaseInsertEvent;
import com.balvir.temptoday.events.CityNameWeatherEvent;
import com.balvir.temptoday.interfaces.ICityListContract;
import com.balvir.temptoday.modals.cityweathermodal.CityListDataModal;
import com.balvir.temptoday.repositories.CityDataRepositories;
import com.balvir.temptoday.restclients.CityNameAPIClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Balvir Jha on 11/11/18.
 */
public class CityListInteractor implements ICityListContract.Interactor {

    private static final String TAG = CityListInteractor.class.getSimpleName();
    private ICityListContract.Presenter mPresenter;

    public CityListInteractor(ICityListContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setInteractor(this);
    }

    @Override
    public void start() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void callGetWeatherDataByCityName(String cityName) {
        CityNameAPIClient cityNameAPIClient = CityNameAPIClient.getInstance();
        cityNameAPIClient.addReuqest(cityName);
        cityNameAPIClient.executeWeatherDataByCityName();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onSuccessGetWeatherDataByCityName(CityNameWeatherEvent.OnLoaded onLoaded) {
        mPresenter.setCityListWeatherData(onLoaded.getResponse());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onErrorGetWeatherDataByCityName(CityNameWeatherEvent.OnLoadingError onLoadingError) {
        Log.d(TAG, "Error while recieving weatherData by city name");
    }

    @Override
    public void insertCityListData(CityListDataModal cityListDataModal) {
        new CityDataRepositories().insert(cityListDataModal);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onFailedCitydataAddition(CityListDatabaseInsertEvent.OnLoadingError onLoadingError) {
        mPresenter.onFailedCitydataAddition(onLoadingError.getErrorMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onSuccessCitydataAddition(CityListDatabaseInsertEvent.OnLoaded onCityDataAdded) {
        mPresenter.onSuccessCitydataAddition(onCityDataAdded.getResponse());
    }

    @Override
    public void fetchAllCityListData() {
        new CityDataRepositories().fetchAllCityListData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onSuccessCityListDataFetchingDOne(CityListDatabaseFetchEvent.OnLoaded onCityDataFetchingDone) {
        mPresenter.onSuccessCityListDataFetchingDone(onCityDataFetchingDone.getResponse());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onFailedCityListDataFetchingDone(String status) {
        mPresenter.onFailedCityListDataFetchingDone(status);
    }

    @Override
    public void stop() {
        EventBus.getDefault().unregister(this);
    }
}
