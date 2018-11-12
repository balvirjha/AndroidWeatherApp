package com.inducesmile.temptoday.views;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pavlospt.CircleView;
import com.inducesmile.temptoday.R;
import com.inducesmile.temptoday.adapters.RecyclerViewAdapter;
import com.inducesmile.temptoday.common.Utils;
import com.inducesmile.temptoday.entity.WeatherObject;
import com.inducesmile.temptoday.helpers.Helper;
import com.inducesmile.temptoday.interactor.WeatherInteractor;
import com.inducesmile.temptoday.interfaces.IWeatherContract;
import com.inducesmile.temptoday.modals.json.FiveWeathers;
import com.inducesmile.temptoday.modals.json.Forecast;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDatWeatherModal;
import com.inducesmile.temptoday.modals.singledayweathermodal.SingleDayWeatherResponse;
import com.inducesmile.temptoday.presenter.WeatherPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class WeatherActivity extends AppCompatActivity implements IWeatherContract.View, View.OnClickListener {

    private static final String TAG = WeatherActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private android.view.View rootLayout;

    private RecyclerViewAdapter recyclerViewAdapter;

    private TextView cityCountry;

    private TextView currentDate;

    private ImageView weatherImage;

    private CircleView circleTitle;

    private TextView windResult;

    private TextView humidityResult;

    FloatingActionButton fab;

    private String isLocationSaved;

    private IWeatherContract.Presenter mPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        initView();
        mPresenter.initiateLocationFetch();
        if (mPresenter.lcheckLocationPermission()) {
            mPresenter.requestLocationPermission();
        } else {
            mPresenter.requestLocationUpdates();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            if (mPresenter.lcheckLocationPermission()) {
                mPresenter.requestLocationPermission();
            } else {
                mPresenter.requestLocationUpdates();
            }
        }
    }

    @Override
    public void callSingleDayWeatherAPI(Location location) {
        if (mPresenter != null) {
            mPresenter.getSingledayWeatherresponse(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        }
    }

    private void initView() {
        new WeatherInteractor(new WeatherPresenter(this));

        rootLayout = findViewById(R.id.rootLayout);
        cityCountry = findViewById(R.id.city_country);
        currentDate = findViewById(R.id.current_date);
        weatherImage = findViewById(R.id.weather_icon);
        circleTitle = findViewById(R.id.weather_result);
        windResult = findViewById(R.id.wind_result);
        humidityResult = findViewById(R.id.humidity_result);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(WeatherActivity.this, 5);

        recyclerView = findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void setSingleWeatherData(SingleDayWeatherResponse singleWeatherData) {
        if (null == singleWeatherData) {
            Toast.makeText(getApplicationContext(), "Nothing was returned", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Single Day Weather API empty Response returned");
        } else {
            Log.d(TAG, "Single Day Weather API Response Good");
            String city = singleWeatherData.getName() + ", " + singleWeatherData.getSys().getCountry();
            String sunrise = new SimpleDateFormat("HH:mm").format(new java.util.Date(new Long(singleWeatherData.getSys().getSunrise()) * 1000));
            String sunset = new SimpleDateFormat("HH:mm").format(new java.util.Date(new Long(singleWeatherData.getSys().getSunset()) * 1000));
            String todayDate = Utils.getTodayDateInStringFormat();
            String tempVal = String.valueOf(Math.round(Math.floor(Double.parseDouble(String.valueOf(singleWeatherData.getMain().getTemp())))));
            String weatherTemp = tempVal + " ";
            String weatherDescription = Helper.capitalizeFirstLetter(singleWeatherData.getWeather().get(0).getDescription());
            String windSpeed = String.valueOf(singleWeatherData.getWind().getSpeed());
            String humidityValue = String.valueOf(singleWeatherData.getMain().getHumidity());

            SingleDayWeatherResponse singleDayWeatherResponse = singleWeatherData;
            SingleDatWeatherModal singleDatWeatherModal = new SingleDatWeatherModal(singleDayWeatherResponse.getName(),
                    singleDayWeatherResponse.getWind().getSpeed(),
                    new Double(singleDayWeatherResponse.getMain().getHumidity()),
                    new SimpleDateFormat("HH:mm").format(new java.util.Date(new Long(singleDayWeatherResponse.getSys().getSunrise()) * 1000)),
                    new SimpleDateFormat("HH:mm").format(new java.util.Date(new Long(singleDayWeatherResponse.getSys().getSunset()) * 1000)),
                    new Double(Math.round(Math.floor(Double.parseDouble(String.valueOf(singleDayWeatherResponse.getMain().getTemp()))))),
                    Helper.capitalizeFirstLetter(singleDayWeatherResponse.getWeather().get(0).getDescription()));
            mPresenter.insertSingleDayData(singleDatWeatherModal);

            cityCountry.setText(Html.fromHtml(city));
            currentDate.setText(Html.fromHtml(todayDate));
            circleTitle.setTitleText(Html.fromHtml(weatherTemp).toString());
            circleTitle.setSubtitleText(Html.fromHtml(weatherDescription).toString());
            windResult.setText(Html.fromHtml(windSpeed) + " " + getResources().getString(R.string.kmph));
            humidityResult.setText(Html.fromHtml(humidityValue) + " " + getResources().getString(R.string.percentSign));

            if (findViewById(R.id.sunriseResult) != null) {
                ((TextView) findViewById((R.id.sunriseResult))).setText(sunrise);
                ((TextView) findViewById((R.id.sunsetResult))).setText(sunset);
            }
            rootLayout.invalidate();

            if (mPresenter != null) {
                mPresenter.getFiveDayWeatherresponse(singleWeatherData.getName());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Helper.REQUEST_LOCATION) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if (mPresenter != null) {
                    mPresenter.requestLocationUpdates();
                }
            } else {
                Toast.makeText(WeatherActivity.this, getString(R.string.permission_notice), Toast.LENGTH_LONG).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.alertMessage))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.positiveMessage), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        if (WeatherActivity.this != null &&
                !WeatherActivity.this.isDestroyed() &&
                WeatherActivity.this.isFinishing()) {
            alert.show();
        }
    }

    @Override
    public void setFiveDayWeatherData(Forecast fiveDayWeatherData) {
        final List<WeatherObject> daysOfTheWeek = new ArrayList<>();
        if (null == fiveDayWeatherData) {
            Toast.makeText(getApplicationContext(), "Nothing was returned in five day API", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Five Day Weather API empty Response returned");
        } else {
            Log.d(TAG, "Five Day Weather API Response Good");
            int[] everyday = new int[]{0, 0, 0, 0, 0, 0, 0};

            List<FiveWeathers> weatherInfo = fiveDayWeatherData.getList();
            if (null != weatherInfo) {
                for (int i = 0; i < weatherInfo.size(); i++) {
                    String time = weatherInfo.get(i).getDt_txt();
                    String shortDay = Utils.convertTimeToDay(time);
                    String temp = weatherInfo.get(i).getMain().getTemp();
                    String tempMin = weatherInfo.get(i).getMain().getTemp_min();

                    if (Utils.convertTimeToDay(time).equals(getResources().getString(R.string.Mon)) && everyday[0] < 1) {
                        daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                        everyday[0] = 1;
                    }
                    if (Utils.convertTimeToDay(time).equals(getResources().getString(R.string.Tue)) && everyday[1] < 1) {
                        daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                        everyday[1] = 1;
                    }
                    if (Utils.convertTimeToDay(time).equals(getResources().getString(R.string.Wed)) && everyday[2] < 1) {
                        daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                        everyday[2] = 1;
                    }
                    if (Utils.convertTimeToDay(time).equals(getResources().getString(R.string.Thu)) && everyday[3] < 1) {
                        daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                        everyday[3] = 1;
                    }
                    if (Utils.convertTimeToDay(time).equals(getResources().getString(R.string.Fri)) && everyday[4] < 1) {
                        daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                        everyday[4] = 1;
                    }
                   /* if (Utils.convertTimeToDay(time).equals(getResources().getString(R.string.Sat)) && everyday[5] < 1) {
                        daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                        everyday[5] = 1;
                    }*/
                    /*if (Utils.convertTimeToDay(time).equals(getResources().getString(R.string.Sun)) && everyday[6] < 1) {
                        daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
                        everyday[6] = 1;
                    }*/

                }
                if (daysOfTheWeek != null && daysOfTheWeek.size() > 0) {
                    recyclerViewAdapter = new RecyclerViewAdapter(WeatherActivity.this, daysOfTheWeek);
                    recyclerView.setAdapter(recyclerViewAdapter);
                }
            }
        }
    }

    @Override
    public void setPresenter(IWeatherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            Intent intent = new Intent(WeatherActivity.this, CityListActivity.class);
            startActivity(intent);
        }
    }
}
