package com.inducesmile.temptoday.repositories;

import com.inducesmile.temptoday.common.TempTodayApplication;
import com.inducesmile.temptoday.common.WeatherDataBase;
import com.inducesmile.temptoday.daos.ISingleDdayDao;
import com.inducesmile.temptoday.events.SigleDayDataInsertEvent;
import com.inducesmile.temptoday.events.SingleDayDataFetchEvent;
import com.inducesmile.temptoday.events.SingleDayDataUpdateEvent;
import com.inducesmile.temptoday.helpers.Helper;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class SIngleDayDataRepositories {

    private ISingleDdayDao iSingleDdayDao;
    private SingleDatWeatherModal singleDatWeatherModal;


    public SIngleDayDataRepositories() {
        WeatherDataBase db = TempTodayApplication.getDatabase();
        iSingleDdayDao = db.isIgleDdayDao();
    }

    public ISingleDdayDao getiSingleDdayDao() {
        return iSingleDdayDao;
    }

    public void setiSingleDdayDao(ISingleDdayDao iSingleDdayDao) {
        this.iSingleDdayDao = iSingleDdayDao;
    }

    public SingleDatWeatherModal getSingleDatWeatherModal() {
        return singleDatWeatherModal;
    }

    public void setSingleDatWeatherModal(SingleDatWeatherModal singleDatWeatherModal) {
        this.singleDatWeatherModal = singleDatWeatherModal;
    }

    public void insert(final SingleDatWeatherModal datWeatherModal) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                iSingleDdayDao.insert(datWeatherModal);
                update(datWeatherModal);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new SigleDayDataInsertEvent.OnLoaded(Helper.SUCCESS_STATUS));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new SigleDayDataInsertEvent.OnLoadingError(Helper.FAILURE_STATUS, -1));
                    }
                });
    }

    public void update(final SingleDatWeatherModal singleDatWeatherModal) {

        Completable.fromAction(new Action() {
            @Override
            public void run() {
                iSingleDdayDao.updateSingleData(singleDatWeatherModal);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new SingleDayDataUpdateEvent.OnLoaded("success"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new SingleDayDataUpdateEvent.OnLoadingError("failed", -1));
                    }
                });
    }

    public void fetchAllSingleDayData() {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                singleDatWeatherModal = iSingleDdayDao.getAllSingleData();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new SingleDayDataFetchEvent.OnLoaded(singleDatWeatherModal));
                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new SingleDayDataFetchEvent.OnLoadingError(Helper.FAILURE_STATUS, -1));
                    }
                });
    }


}
