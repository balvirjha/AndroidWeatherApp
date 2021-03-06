package com.balvir.temptoday.repositories;

import com.balvir.temptoday.common.OpenWeatherClient;
import com.balvir.temptoday.common.TempTodayApplication;
import com.balvir.temptoday.common.WeatherDataBase;
import com.balvir.temptoday.daos.IFiveDayDao;
import com.balvir.temptoday.entity.WeatherObject;
import com.balvir.temptoday.events.FiveDayDataFetchingEvent;
import com.balvir.temptoday.events.FiveDayDataInsertionEvent;
import com.balvir.temptoday.events.FiveDayDataUpdatingEvent;
import com.balvir.temptoday.events.FiveDayWeatherEvent;
import com.balvir.temptoday.helpers.Helper;
import com.balvir.temptoday.modals.cityweathermodal.CityWeatherResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balvir Jha on 11/13/18.
 */
public class FiveDayDataRepositories {

    private IFiveDayDao iFiveDayDao;
    List<WeatherObject> weatherObjects;

    public FiveDayDataRepositories() {
        WeatherDataBase db = TempTodayApplication.getDatabase();
        iFiveDayDao = db.iFiveDayDao();
    }

    public List<WeatherObject> getWeatherObjects() {
        return weatherObjects;
    }

    public void setWeatherObjects(List<WeatherObject> weatherObjects) {
        this.weatherObjects = weatherObjects;
    }

    public void insert(final List<WeatherObject> weatherObjectList) {

        Completable.fromAction(new Action() {
            @Override
            public void run() {
                iFiveDayDao.deleteAll();
                iFiveDayDao.insert(weatherObjectList);
                //update(weatherObjectList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new FiveDayDataInsertionEvent.OnInsertingDataSuccess(Helper.SUCCESS_STATUS));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new FiveDayDataInsertionEvent.OnInsertingDataError(Helper.FAILURE_STATUS, -1));
                    }
                });
    }

    public void update(final List<WeatherObject> weatherObject) {

        Completable.fromAction(new Action() {
            @Override
            public void run() {
                iFiveDayDao.updateFiveDayData(weatherObject);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new FiveDayDataUpdatingEvent.OnUpdatingDataSuccess(Helper.SUCCESS_STATUS));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new FiveDayDataUpdatingEvent.OnUpdatingDataError(Helper.FAILURE_STATUS, -1));
                    }
                });
    }

    public void fetchAllFiveDayData() {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                weatherObjects = iFiveDayDao.getAllFiveDayData();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new FiveDayDataFetchingEvent.OnFetchingDataSuccess(weatherObjects));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new FiveDayDataFetchingEvent.OnFetchingDataError(Helper.FAILURE_STATUS, -1));
                    }
                });
    }
}
