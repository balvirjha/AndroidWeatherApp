package com.inducesmile.temptoday.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.inducesmile.temptoday.entity.WeatherObject;

import java.util.List;

/**
 * Created by Balvir Jha on 11/13/18.
 */

@Dao
public interface IFiveDayDao {

    @Insert
    void insert(List<WeatherObject> weatherObjectList);

    @Query("DELETE FROM fivedayweatherobject")
    void deleteAll();

    @Query("SELECT * from fivedayweatherobject")
    List<WeatherObject> getAllFiveDayData();

    @Update
    int updateFiveDayData(List<WeatherObject> weatherObject);
}
