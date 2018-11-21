package com.balvir.temptoday.repositories;

import com.balvir.temptoday.common.TempTodayApplication;
import com.balvir.temptoday.common.WeatherDataBase;
import com.balvir.temptoday.daos.ICityDao;
import com.balvir.temptoday.events.CityListDatabaseFetchEvent;
import com.balvir.temptoday.events.CityListDatabaseInsertEvent;
import com.balvir.temptoday.events.CityListDatabaseUpdateEvent;
import com.balvir.temptoday.helpers.Helper;
import com.balvir.temptoday.modals.cityweathermodal.CityListDataModal;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class CityDataRepositories {

    private ICityDao iCityDao;
    List<CityListDataModal> cityListDataModals;

    public CityDataRepositories() {
        WeatherDataBase db = TempTodayApplication.getDatabase();
        iCityDao = db.cityDao();
    }

    public List<CityListDataModal> getCityListDataModals() {
        return cityListDataModals;
    }

    public void setCityListDataModals(List<CityListDataModal> cityListDataModals) {
        this.cityListDataModals = cityListDataModals;
    }

    public void insert(final CityListDataModal cityListDataModal) {

        Completable.fromAction(new Action() {
            @Override
            public void run() {
                iCityDao.insert(cityListDataModal);
                update(cityListDataModal);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new CityListDatabaseInsertEvent.OnLoaded(Helper.SUCCESS_STATUS));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new CityListDatabaseInsertEvent.OnLoadingError(Helper.FAILURE_STATUS, -1));
                    }
                });
    }

    public void update(final CityListDataModal cityListDataModal) {

        Completable.fromAction(new Action() {
            @Override
            public void run() {
                iCityDao.updateCityData(cityListDataModal);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new CityListDatabaseUpdateEvent.OnLoaded("success"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new CityListDatabaseUpdateEvent.OnLoadingError("failed", -1));
                    }
                });
    }

    public void fetchAllCityListData() {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                cityListDataModals = iCityDao.getAllCityData();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new CityListDatabaseFetchEvent.OnLoaded(cityListDataModals));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new CityListDatabaseFetchEvent.OnLoadingError(Helper.FAILURE_STATUS, -1));
                    }
                });
    }
}
