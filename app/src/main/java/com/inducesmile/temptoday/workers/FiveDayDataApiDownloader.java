package com.inducesmile.temptoday.workers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.inducesmile.temptoday.common.SharedPrefUtil;
import com.inducesmile.temptoday.common.TempTodayApplication;
import com.inducesmile.temptoday.restclients.SingleDayAPIClient;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class FiveDayDataApiDownloader extends Worker {

    private Context context;

    public FiveDayDataApiDownloader(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        this.context = appContext;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            String locationString = SharedPrefUtil.getInstance(TempTodayApplication.getInstance()).getCurrentLocation();
            new SingleDayAPIClient().getSingleDayWeatherResponse(locationString.split("-")[0], locationString.split("-")[1]);
            return Worker.Result.SUCCESS;
        } catch (Exception e) {
            return Result.FAILURE;
        }
    }
}
