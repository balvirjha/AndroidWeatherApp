package com.inducesmile.temptoday.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.inducesmile.temptoday.R;

/**
 * Created by Balvir Jha on 11/11/18.
 */
public class CityListViewHolders extends RecyclerView.ViewHolder {

    private static final String TAG = RecyclerViewHolders.class.getSimpleName();

    public TextView envTextView;

    public TextView windTextView3;

    public TextView humidityTextView3;

    public TextView citynameTextView;

    public CityListViewHolders(final View itemView) {
        super(itemView);
        envTextView = itemView.findViewById(R.id.envTextView);
        windTextView3 = itemView.findViewById(R.id.windTextView3);
        humidityTextView3 = itemView.findViewById(R.id.humidityTextView3);
        citynameTextView = itemView.findViewById(R.id.citynameTextView);
    }

}
