package com.inducesmile.temptoday.interfaces;

import android.location.Location;

import com.inducesmile.temptoday.events.FiveDayWeatherEvent;
import com.inducesmile.temptoday.events.SigleDayDataInsertEvent;
import com.inducesmile.temptoday.events.SingleDayDataFetchEvent;
import com.inducesmile.temptoday.events.SingleDayWeatherEvent;
import com.inducesmile.temptoday.modals.json.Forecast;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDayWeatherResponse;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public interface IWeatherContract {

    interface View extends IBaseView<Presenter> {
        void saveSingleWeatherData(SingleDayWeatherResponse singleWeatherData);

        void setFiveDayWeatherData(Forecast fiveDayWeatherData);

        void callSingleDayWeatherAPI(Location location);

        void showGPSDisabledAlertToUser();

        void displaySingleWeatherData(SingleDatWeatherModal singleDatWeatherModal);

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

        void insertSingleDayData(SingleDatWeatherModal datWeatherModal);

        void onSuccessSingleDayData(String status);

        void onErrorSingleDayData(String status);

        void fetchAllSingleDayData();

        void onSucessOfFetchingSingleDayData(SingleDatWeatherModal singleDatWeatherModal);

        void onFailureOfFetchingSingleDayData(String status);
    }

    interface Interactor extends IBaseInteractor {
        void getSingledayWeatherResponse(String lat, String lon);

        void onSuccessSingleWeatherData(SingleDayWeatherEvent.OnLoaded onLoaded);

        void onErrorSingleWeatherData(SingleDayWeatherEvent.OnLoadingError onLoadingError);

        public void getFivedayWeatherResponse(String cityName);

        void onSuccessFiveWeatherData(FiveDayWeatherEvent.OnLoaded onLoaded);

        void onErrorFiveWeatherData(FiveDayWeatherEvent.OnLoadingError onLoadingError);

        void insertSingleDayData(SingleDatWeatherModal datWeatherModal);

        void onSuccessSingleDayData(SigleDayDataInsertEvent.OnLoaded onLoaded);

        void onErrorSingleDayData(SigleDayDataInsertEvent.OnLoadingError onLoadingError);

        void fetchAllSingleDayData();

        void onSucessOfFetchingSingleDayData(SingleDayDataFetchEvent.OnLoaded onLoaded);

        void onFailureOfFetchingSingleDayData(SingleDayDataFetchEvent.OnLoadingError onLoadingError);
    }
}
