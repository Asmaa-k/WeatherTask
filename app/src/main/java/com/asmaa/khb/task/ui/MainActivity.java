package com.asmaa.khb.task.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.asmaa.khb.task.R;
import com.asmaa.khb.task.adapters.CitiesRecyclerAdapter;
import com.asmaa.khb.task.adapters.VerticalSpacingItemDecoration;
import com.asmaa.khb.task.models.CurrentArea;
import com.asmaa.khb.task.mvp.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements CitiesRecyclerAdapter.OnCityListener, MainPresenter.View {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private CitiesRecyclerAdapter adapter;
    private MainPresenter presenter;
    private List<CurrentArea> mWeather;
    private String city = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        mWeather = new ArrayList<>();
        mWeather = presenter.getAllWeather();
        initRecycler();
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CitiesRecyclerAdapter(mWeather);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        VerticalSpacingItemDecoration itemDecorator = new VerticalSpacingItemDecoration(100);
        mRecyclerView.addItemDecoration(itemDecorator);
    }

    @Override
    public void onCityClick(int id, String latlan, String cityName) {
        city = cityName;
        presenter.getWeather(latlan, id);
    }

    @Override
    protected void onDestroy() {
        presenter.getDisposables().clear();
        presenter.getRealm().close();
        super.onDestroy();
    }

    @Override
    public void updateInf(CurrentArea currentArea) {

        if (currentArea == null) {
            Log.d(TAG, "updateInf: nulllll");
            return;
        }

        Log.d(TAG, "updateInf: stubbbbbid");
        mWeather.add(currentArea.getId(), currentArea);
        adapter.notifyItemChanged(currentArea.getId());

        Intent intent = new Intent(this, WeatherDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("weather_details", currentArea.getCurrentWeather());
        intent.putExtra("city", city);
        startActivity(intent);
    }
}