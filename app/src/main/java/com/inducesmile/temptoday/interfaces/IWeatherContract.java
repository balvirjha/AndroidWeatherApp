package com.inducesmile.temptoday.interfaces;

import android.location.Location;

import com.inducesmile.temptoday.events.FiveDayWeatherEvent;
import com.inducesmile.temptoday.events.SingleDayWeatherEvent;
import com.inducesmile.temptoday.modals.SingleDayWeatherResponse;
import com.inducesmile.temptoday.modals.json.Forecast;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public interface IWeatherContract {

    interface View extends IBaseView<Presenter> {
        void setSingleWeatherData(SingleDayWeatherResponse singleWeatherData);

        void setFiveDayWeatherData(Forecast fiveDayWeatherData);

        void callSingleDayWeatherAPI(Location location);

        void showGPSDisabledAlertToUser();
    }

    interface Presenter extends IBasePresenter<Interactor> {
        void getSingledayWeatherresponse(String lat, String lon);

        void setSingleWeatherData(SingleDayWeatherResponse singleWeatherData);

        void getFiveDayWeatherresponse(String cityName);

        void setFiveDayWeatherData(Forecast fiveDayWeatherData);

        void initiateLocationFetch();

        boolean lcheckLocationPermission();

        void requestLocationPermission();

        void requestLocationUpdates();
    }

    interface Interactor extends IBaseInteractor {
        void getSingledayWeatherResponse(String lat, String lon);

        void onSuccessSingleWeatherData(SingleDayWeatherEvent.OnLoaded onLoaded);

        void onErrorSingleWeatherData(SingleDayWeatherEvent.OnLoadingError onLoadingError);

        public void getFivedayWeatherResponse(String cityName);

        void onSuccessFiveWeatherData(FiveDayWeatherEvent.OnLoaded onLoaded);

        void onErrorFiveWeatherData(FiveDayWeatherEvent.OnLoadingError onLoadingError);
    }
}
