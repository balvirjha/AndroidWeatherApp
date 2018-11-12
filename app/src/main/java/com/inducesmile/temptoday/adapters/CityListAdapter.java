package com.inducesmile.temptoday.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inducesmile.temptoday.R;
import com.inducesmile.temptoday.modals.cityweathermodal.CityListDataModal;

import java.util.List;


/**
 * Created by Balvir Jha on 11/11/18.
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListViewHolders> {

    protected Context context;
    List<CityListDataModal> data;

    public CityListAdapter(Context context, List<CityListDataModal> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public CityListViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        CityListViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_list_row_item, parent, false);
        viewHolder = new CityListViewHolders(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CityListViewHolders holder, int position) {
        CityListDataModal mCityWeatherResult = data.get(position);
        if (mCityWeatherResult != null && mCityWeatherResult.getCity() != null) {
            holder.windTextView3.setText(String.valueOf(mCityWeatherResult.getWind().intValue()));

            double mTemp = Math.round(Math.floor(Double.parseDouble(String.valueOf(mCityWeatherResult.getTemp().intValue()))));
            holder.envTextView.setText(String.valueOf(Math.round(mTemp)) + "°");

            holder.humidityTextView3.setText(String.valueOf(mCityWeatherResult.getHumidity().intValue()));
            holder.citynameTextView.setText(mCityWeatherResult.getCity());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
