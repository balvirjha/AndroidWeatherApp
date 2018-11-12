package com.inducesmile.temptoday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inducesmile.temptoday.R;
import com.inducesmile.temptoday.entity.WeatherObject;

import java.util.List;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    protected Context context;
    private List<WeatherObject> dailyWeather;

    public RecyclerViewAdapter(Context context, List<WeatherObject> dailyWeather) {
        this.dailyWeather = dailyWeather;
        this.context = context;
    }
    
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_daily_list, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        holder.dayOfWeek.setText(dailyWeather.get(position).getDayOfWeek());
        holder.weatherIcon.setImageResource(dailyWeather.get(position).getWeatherIcon());

        double mTemp = Double.parseDouble(dailyWeather.get(position).getWeatherResult());
        holder.weatherResult.setText(String.valueOf(Math.round(mTemp)) + "°");

        holder.weatherResultSmall.setText(dailyWeather.get(position).getWeatherResultSmall());
        holder.weatherResultSmall.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dailyWeather.size();
    }

}
