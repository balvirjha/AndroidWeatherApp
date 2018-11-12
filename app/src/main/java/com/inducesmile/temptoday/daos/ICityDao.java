package com.inducesmile.temptoday.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.inducesmile.temptoday.modals.cityweathermodal.CityListDataModal;

import java.util.List;

/**
 * Created by Balvir Jha on 11/12/18.
 */

@Dao
public interface ICityDao {
    @Insert
    void insert(CityListDataModal cityListDataModal);

    @Query("DELETE FROM citydatamodal")
    void deleteAll();

    @Query("SELECT * from citydatamodal")
    List<CityListDataModal> getAllCityData();

    @Update
    int updateCityData(CityListDataModal cityListDataModal);
}
