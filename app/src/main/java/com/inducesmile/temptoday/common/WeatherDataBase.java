package com.inducesmile.temptoday.common;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.inducesmile.temptoday.daos.ICityDao;
import com.inducesmile.temptoday.daos.IFiveDayDao;
import com.inducesmile.temptoday.daos.ISingleDdayDao;
import com.inducesmile.temptoday.entity.WeatherObject;
import com.inducesmile.temptoday.modals.cityweathermodal.CityListDataModal;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;

/**
 * Created by Balvir Jha on 11/12/18.
 */
@Database(entities = {CityListDataModal.class, SingleDatWeatherModal.class, WeatherObject.class}, version = 2)
public abstract class WeatherDataBase extends RoomDatabase {

    public abstract ICityDao cityDao();

    public abstract ISingleDdayDao isIgleDdayDao();

    public abstract IFiveDayDao iFiveDayDao();
}
