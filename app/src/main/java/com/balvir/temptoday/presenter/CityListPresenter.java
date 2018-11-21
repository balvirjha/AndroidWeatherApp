package com.balvir.temptoday.presenter;

import android.util.Log;

import com.balvir.temptoday.helpers.Helper;
import com.balvir.temptoday.interfaces.ICityListContract;
import com.balvir.temptoday.modals.cityweathermodal.CityListDataModal;
import com.balvir.temptoday.modals.cityweathermodal.CityWeatherResult;

import java.util.List;

/**
 * Created by Balvir Jha on 11/11/18.
 */
public class CityListPresenter implements ICityListContract.Presenter {

    private static final String TAG = CityListPresenter.class.getSimpleName();
    private ICityListContract.View mCitylistView;
    private ICityListContract.Interactor mCityListInteractor;

    public CityListPresenter(ICityListContract.View view) {
        mCitylistView = view;
        mCitylistView.setPresenter(this);
    }

    @Override
    public void setInteractor(ICityListContract.Interactor interactor) {
        this.mCityListInteractor = interactor;
    }

    @Override
    public void start() {
        mCityListInteractor.start();
    }

    @Override
    public void callGetWeatherDataByCityName(String cityName) {
        mCityListInteractor.callGetWeatherDataByCityName(cityName);
    }

    @Override
    public void setCityListWeatherData(List<CityWeatherResult> cityWeatherResults) {
        mCitylistView.setCityNameWeatherData(cityWeatherResults);
    }

    @Override
    public void insertCityListData(CityListDataModal cityListDataModal) {
        mCityListInteractor.insertCityListData(cityListDataModal);
    }

    @Override
    public void onFailedCitydataAddition(String status) {
        Log.d(TAG, "Inserting city data failed");
    }

    @Override
    public void fetchAllCityListData() {
        mCityListInteractor.fetchAllCityListData();
    }

    @Override
    public void onSuccessCityListDataFetchingDone(List<CityListDataModal> cityListDataModals) {
        mCitylistView.onSuccessCityListDataFetchingDone(cityListDataModals);
    }

    @Override
    public void onSuccessCitydataAddition(String status) {
        if (status.equals(Helper.SUCCESS_STATUS)) {
            mCitylistView.onSuccessCitydataAddition(Helper.SUCCESS_STATUS);
        } else if (status.equals(Helper.FAILURE_STATUS)) {
            onFailedCitydataAddition(Helper.FAILURE_STATUS);
        }
    }

    @Override
    public void onFailedCityListDataFetchingDone(String status) {
        Log.d(TAG, "Error while fetching all city list data");
    }

    @Override
    public void stop() {
        mCityListInteractor.stop();
    }
}
