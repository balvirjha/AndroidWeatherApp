package com.inducesmile.temptoday.views;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.inducesmile.temptoday.R;
import com.inducesmile.temptoday.adapters.CityListAdapter;
import com.inducesmile.temptoday.helpers.Helper;
import com.inducesmile.temptoday.interactor.CityListInteractor;
import com.inducesmile.temptoday.interfaces.ICityListContract;
import com.inducesmile.temptoday.modals.cityweathermodal.CityListDataModal;
import com.inducesmile.temptoday.modals.cityweathermodal.CityWeatherResult;
import com.inducesmile.temptoday.presenter.CityListPresenter;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, ICityListContract.View {

    private static final String TAG = CityListActivity.class.getSimpleName();
    private EditText cityEditText;
    private ImageView searchButton;
    private ICityListContract.Presenter mPresenter;
    private RecyclerView recyclerView;
    private CityListAdapter recyclerViewAdapter;
    private java.util.List<CityWeatherResult> cityWeatherResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        initView();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAllCityListData();
    }

    private void initView() {
        new CityListInteractor(new CityListPresenter(this));
        cityEditText = findViewById(R.id.cityEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.cityList);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(CityListActivity.this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void setListeners() {
        searchButton.setOnClickListener(this);
        cityEditText.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchButton) {
            callGetWeatherDataByCityName();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchButton.setEnabled(true);
        searchButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void callGetWeatherDataByCityName() {
        if (cityEditText.getText() != null) {
            String cityName = cityEditText.getText().toString();
            if (!TextUtils.isEmpty(cityName)) {
                mPresenter.callGetWeatherDataByCityName(cityName);
            }
        }
    }

    @Override
    public void setCityNameWeatherData(List<CityWeatherResult> cityWeatherResults) {

        this.cityWeatherResultList = cityWeatherResults;
        for (final CityWeatherResult cityWeatherResult :
                cityWeatherResultList) {

            CityListDataModal cityListDataModal = new CityListDataModal(cityWeatherResult.getCity().getName(),
                    cityWeatherResult.getList().get(0).getMain().getTemp(),
                    cityWeatherResult.getList().get(0).getWind().getSpeed(),
                    cityWeatherResult.getList().get(0).getMain().getHumidity(),
                    String.valueOf(cityWeatherResult.getList().get(0).getDt()));

            mPresenter.insertCityListData(cityListDataModal);
        }
    }

    private void fetchAllCityListData() {
        mPresenter.fetchAllCityListData();
    }

    @Override
    public void onSuccessCityListDataFetchingDone(List<CityListDataModal> cityListDataModals) {
        displayData(cityListDataModals);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void displayData(List<CityListDataModal> cityListDataModals) {
        if (CityListActivity.this != null && !CityListActivity.this.isFinishing() && !CityListActivity.this.isDestroyed()) {
            recyclerViewAdapter = new CityListAdapter(this, cityListDataModals);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onSuccessCitydataAddition(String status) {
        if (status.equals(Helper.SUCCESS_STATUS)) {
            fetchAllCityListData();
        }
    }

    @Override
    public void setPresenter(ICityListContract.Presenter presenter) {
        this.mPresenter = presenter;
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
}
