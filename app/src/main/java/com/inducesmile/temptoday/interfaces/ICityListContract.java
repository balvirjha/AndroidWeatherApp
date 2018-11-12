package com.inducesmile.temptoday.interfaces;

import com.inducesmile.temptoday.events.CityListDatabaseFetchEvent;
import com.inducesmile.temptoday.events.CityListDatabaseInsertEvent;
import com.inducesmile.temptoday.events.CityNameWeatherEvent;
import com.inducesmile.temptoday.modals.cityweathermodal.CityListDataModal;
import com.inducesmile.temptoday.modals.cityweathermodal.CityWeatherResult;

import java.util.List;

/**
 * Created by Balvir Jha on 11/11/18.
 */
public class ICityListContract {

    public interface View extends IBaseView<ICityListContract.Presenter> {
        void setCityNameWeatherData(List<CityWeatherResult> cityWeatherResults);

        void onSuccessCitydataAddition(String status);

        void onSuccessCityListDataFetchingDone(List<CityListDataModal> cityListDataModals);
    }

    public interface Presenter extends IBasePresenter<ICityListContract.Interactor> {
        void callGetWeatherDataByCityName(String cityName);

        void setCityListWeatherData(List<CityWeatherResult> cityWeatherResults);

        void insertCityListData(CityListDataModal cityListDataModal);

        void onSuccessCitydataAddition(String status);

        void onFailedCitydataAddition(String status);

        void fetchAllCityListData();

        void onSuccessCityListDataFetchingDone(List<CityListDataModal> cityListDataModals);

        void onFailedCityListDataFetchingDone(String status);
    }

    public interface Interactor extends IBaseInteractor {

        void callGetWeatherDataByCityName(String cityName);

        void onSuccessGetWeatherDataByCityName(CityNameWeatherEvent.OnLoaded onLoaded);

        void onErrorGetWeatherDataByCityName(CityNameWeatherEvent.OnLoadingError onLoadingError);

        void insertCityListData(CityListDataModal cityListDataModal);

        void onSuccessCitydataAddition(CityListDatabaseInsertEvent.OnLoaded onCityDataAdded);

        void onFailedCitydataAddition(CityListDatabaseInsertEvent.OnLoadingError onLoadingError);

        void fetchAllCityListData();

        void onSuccessCityListDataFetchingDOne(CityListDatabaseFetchEvent.OnLoaded onCityDataFetchingDone);

        void onFailedCityListDataFetchingDone(String status);

    }
}
