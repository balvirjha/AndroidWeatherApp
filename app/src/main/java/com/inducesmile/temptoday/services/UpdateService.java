package com.inducesmile.temptoday.services;

import android.app.IntentService;
import android.app.Notification;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

import com.inducesmile.temptoday.R;
import com.inducesmile.temptoday.common.TempTodayApplication;
import com.inducesmile.temptoday.common.Utils;
import com.inducesmile.temptoday.common.WeatherDataBase;
import com.inducesmile.temptoday.daos.ISingleDdayDao;
import com.inducesmile.temptoday.helpers.Helper;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;
import com.inducesmile.temptoday.views.TempTodayWidgetProvider;

/**
 * Created by Balvir Jha on 11/19/18.
 */
public class UpdateService extends IntentService {
    private ISingleDdayDao iSingleDdayDao;
    private static final String TAG = UpdateService.class.getSimpleName();

    public UpdateService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1, new Notification());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherDataBase db = TempTodayApplication.getDatabase();
        iSingleDdayDao = db.isIgleDdayDao();
        SingleDatWeatherModal singleDatWeatherModal = getCurrentDateTime();
        if (singleDatWeatherModal != null) {
            Log.d(TAG, "Recieved city: " + singleDatWeatherModal.getCityName());
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.textTemp, singleDatWeatherModal.getTemp().toString() + "°C");


            if (null == singleDatWeatherModal) {
                Log.d(TAG, "Single Day Weather Database empty Response returned");
            } else {
                Log.d(TAG, "Single Day Weather Database Response Good");
                String city = singleDatWeatherModal.getCityName();
                String sunrise = singleDatWeatherModal.getSunrise();
                String sunset = singleDatWeatherModal.getSunset();
                String todayDate = Utils.getTodayDateInStringFormat();
                String tempVal = String.valueOf(singleDatWeatherModal.getTemp().intValue());
                String weatherTemp = tempVal;
                String weatherDescription = Helper.capitalizeFirstLetter(singleDatWeatherModal.getWeatherDescription());
                String windSpeed = String.valueOf(singleDatWeatherModal.getWindSpeed().intValue());
                String humidityValue = String.valueOf(singleDatWeatherModal.getHumidity().intValue());


                remoteViews.setTextViewText(R.id.textSunrise, sunrise);
                remoteViews.setTextViewText(R.id.subtitleTemp, Html.fromHtml(weatherDescription).toString());
                remoteViews.setTextViewText(R.id.dateText, Html.fromHtml(todayDate));
                remoteViews.setTextViewText(R.id.cityText, Html.fromHtml(city));
                remoteViews.setTextViewText(R.id.textsunset, sunset);
                remoteViews.setTextViewText(R.id.textTemp, Html.fromHtml(weatherTemp).toString() + "°C");
                remoteViews.setTextViewText(R.id.textwind, Html.fromHtml(windSpeed) + " " + getResources().getString(R.string.kmph));
                remoteViews.setTextViewText(R.id.texthumid, Html.fromHtml(humidityValue) + " " + getResources().getString(R.string.percentSign));


            }

            ComponentName theWidget = new ComponentName(this, TempTodayWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(theWidget, remoteViews);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    private SingleDatWeatherModal getCurrentDateTime() {
        return iSingleDdayDao.getAllSingleData();

    }
}

