package com.balvir.temptoday.restclients;

import android.util.Log;

import com.balvir.temptoday.common.OpenWeatherClient;
import com.balvir.temptoday.events.CityNameWeatherEvent;
import com.balvir.temptoday.helpers.Helper;
import com.balvir.temptoday.modals.cityweathermodal.CityWeatherResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Balvir Jha on 11/11/18.
 */
public class CityNameAPIClient {
    private static CityNameAPIClient cityNameAPIClient = new CityNameAPIClient();
    private static final String TAG = CityNameAPIClient.class.getSimpleName();
    List<Observable<?>> requests = new ArrayList<>();
    List<CityWeatherResult> cityWeatherResults = new ArrayList<>();

    private CityNameAPIClient() {
    }

    public synchronized static CityNameAPIClient getInstance() {
        if (cityNameAPIClient == null) {
            cityNameAPIClient = new CityNameAPIClient();
        }
        return cityNameAPIClient;
    }

    public void addReuqest(String cityName) {
        Observable<CityWeatherResult> callObservable = OpenWeatherClient.getOpenWeaterAPI().getWeatherDataByCityName(cityName, Helper.getApiKey()).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
        requests.add(callObservable);
    }


    public void executeWeatherDataByCityName() {
        Observable.zip(requests, new Function<Object[], List<CityWeatherResult>>() {
            @Override
            public List<CityWeatherResult> apply(Object[] objects) throws Exception {
                for (int i = 0; i < objects.length; i++) {
                    cityWeatherResults.add((CityWeatherResult) objects[i]);
                }
                return cityWeatherResults;
            }
        }).subscribe(new Consumer<List<CityWeatherResult>>() {
                         @Override
                         public void accept(List<CityWeatherResult> cityWeatherResults) throws Exception {
                             Log.d(TAG, "successful completion of all requests");
                             EventBus.getDefault().post(new CityNameWeatherEvent.OnLoaded(cityWeatherResults));
                             requests.clear();
                         }
                     },

                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Log.d(TAG, "unsuccessful completion of all requests");
                    }
                });
        Log.d(TAG, "getSingleDayWeatherResponse api called");
    }
}
