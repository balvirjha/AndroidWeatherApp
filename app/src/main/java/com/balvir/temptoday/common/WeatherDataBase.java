package com.balvir.temptoday.common;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.balvir.temptoday.daos.ICityDao;
import com.balvir.temptoday.daos.IFiveDayDao;
import com.balvir.temptoday.daos.ISingleDdayDao;
import com.balvir.temptoday.entity.WeatherObject;
import com.balvir.temptoday.modals.cityweathermodal.CityListDataModal;
import com.balvir.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;

/**
 * Created by Balvir Jha on 11/12/18.
 */
@Database(entities = {CityListDataModal.class, SingleDatWeatherModal.class, WeatherObject.class}, version = 2)
public abstract class WeatherDataBase extends RoomDatabase {

    public abstract ICityDao cityDao();

    public abstract ISingleDdayDao isIgleDdayDao();

    public abstract IFiveDayDao iFiveDayDao();
}
