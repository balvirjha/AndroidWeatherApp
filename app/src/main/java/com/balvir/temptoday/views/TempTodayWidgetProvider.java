package com.balvir.temptoday.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.balvir.temptoday.services.UpdateService;

/**
 * Created by Balvir Jha on 11/18/18.
 */
public class TempTodayWidgetProvider extends AppWidgetProvider {
    private PendingIntent pendingIntent;
    private static final String TAG = TempTodayWidgetProvider.class.getSimpleName();
    private static final Long INTERVAL_TIME_MILLIS = new Long(60000 * 60 * 3);

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "OnUpdate called in widget");
        Toast.makeText(context, "Updating the widget", Toast.LENGTH_SHORT).show();
        setRepeatingAlarm(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled called in widget, starting service immidiately");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, UpdateService.class));
        } else {
            context.startService(new Intent(context, UpdateService.class));
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive called in widget");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d(TAG, "boot completed onReceive called in widget");
            setRepeatingAlarm(context);

        }
    }

    private void setRepeatingAlarm(Context context) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent i = new Intent(context, UpdateService.class);

        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), INTERVAL_TIME_MILLIS, pendingIntent);
    }

}
