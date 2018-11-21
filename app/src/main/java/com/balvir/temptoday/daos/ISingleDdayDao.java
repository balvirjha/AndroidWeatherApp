package com.balvir.temptoday.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.balvir.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;

import java.util.List;

/**
 * Created by Balvir Jha on 11/12/18.
 */

@Dao
public interface ISingleDdayDao {
    @Insert
    void insert(SingleDatWeatherModal singleDatWeatherModal);

    @Query("DELETE FROM singledaydatamodal")
    void deleteAll();

    @Query("SELECT * from singledaydatamodal")
    SingleDatWeatherModal getAllSingleData();

    @Update
    int updateSingleData(SingleDatWeatherModal singleDatWeatherModal);
}
