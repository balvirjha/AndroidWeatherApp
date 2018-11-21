package com.balvir.temptoday.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balvir.temptoday.R;


/**
 * Created by BalvirJha on 10-11-2018.
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerViewHolders.class.getSimpleName();

    public TextView dayOfWeek;

    public ImageView weatherIcon;

    public TextView weatherResult;

    public TextView weatherResultSmall;

    public RecyclerViewHolders(final View itemView) {
        super(itemView);
        dayOfWeek = itemView.findViewById(R.id.day_of_week);
        weatherIcon = itemView.findViewById(R.id.weather_icon);
        weatherResult = itemView.findViewById(R.id.weather_result);
        weatherResultSmall = itemView.findViewById(R.id.weather_result_small);
    }
}
