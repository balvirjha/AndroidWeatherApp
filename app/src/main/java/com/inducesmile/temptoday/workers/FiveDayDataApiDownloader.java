package com.inducesmile.temptoday.workers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.inducesmile.temptoday.common.SharedPrefUtil;
import com.inducesmile.temptoday.common.TempTodayApplication;
import com.inducesmile.temptoday.restclients.FiveDayAPIClient;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by Balvir Jha on 11/12/18.
 */
public class FiveDayDataApiDownloader extends Worker {

    private static final String TAG = FiveDayDataApiDownloader.class.getSimpleName();

    private Context context;

    public FiveDayDataApiDownloader(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        this.context = appContext;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            new FiveDayAPIClient().getFiveDayWeatherResponse(SharedPrefUtil.getInstance(TempTodayApplication.getInstance()).getCurrentCity());
            Log.d(TAG, "dowork called");
            return Worker.Result.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.FAILURE;
        }
    }
}
