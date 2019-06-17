package com.asmaa.khb.task.ui;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asmaa.khb.task.R;
import com.asmaa.khb.task.models.CurrentWeather;
import com.asmaa.khb.task.util.Constants;

public class WeatherDetails extends AppCompatActivity {

    private static final String TAG = "WeatherDetails";

    TextView cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, updatedField;
    ImageView weatherIcon;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        loader = (ProgressBar) findViewById(R.id.loader);
        cityField = (TextView) findViewById(R.id.city_field);
        updatedField = (TextView) findViewById(R.id.date_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
        humidity_field = (TextView) findViewById(R.id.humidity_field);
        pressure_field = (TextView) findViewById(R.id.pressure_field);
        weatherIcon = (ImageView) findViewById(R.id.weather_icon);


        if (getIntent().hasExtra("weather_details")) {
            CurrentWeather weatherDetail = getIntent().getParcelableExtra("weather_details");
            updateInf(weatherDetail);
        }
    }

    public void updateInf(CurrentWeather weather) {
        if (weather == null) return;

        loader.setVisibility(View.INVISIBLE);
        String city = getIntent().getStringExtra("city");
        cityField.setText(city);

        updatedField.setText(Constants.timeConvert(weather.getTime()));

        detailsField.setText(weather.getSummary());

        humidity_field.setText("Humidity: " + weather.getHumidity());

        pressure_field.setText("Pressure: " + weather.getPressure());

        RelativeLayout container = findViewById(R.id.temperature_container);
        container.setVisibility(View.VISIBLE);
        currentTemperatureField.setText(Constants.celsiusConvert(weather.getTemperature()) + "");

        weatherIcon.setImageDrawable(ResourcesCompat.getDrawable(
                getResources()
                , Constants.setDrawableWeather(weather.getIcon())
                , null));
    }
}
