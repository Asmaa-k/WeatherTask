package com.asmaa.khb.task.adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asmaa.khb.task.R;
import com.asmaa.khb.task.models.CurrentArea;
import com.asmaa.khb.task.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class CitiesRecyclerAdapter extends RecyclerView.Adapter<CitiesRecyclerAdapter.CityHolder> {
    private static final String TAG = "CitiesRecyclerAdapter";
    private String[] mCities = {"AMMAN", "IRBID", "AQABA"};
    private String[] mCoordnates = {Constants.LATLNG_AMMAN, Constants.LATLNG_IRBID, Constants.LATLNG_AQABA};
    private List<CurrentArea> mWeather = new ArrayList<>();
    OnCityListener cityListener;

    public CitiesRecyclerAdapter(List<CurrentArea> mWeather) {
        this.mWeather = mWeather;
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_cities_list, viewGroup, false);
        return new CityHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder viewHolder, int i) {

        viewHolder.textName.setText(mCities[i]);

        if (!mWeather.isEmpty()) {
            Log.d(TAG, "onBindViewHolder: Not Empty");
            if (mWeather.size() > i) {

                viewHolder.weatherTemp.setText(Constants.celsiusConvert
                        (mWeather.get(i).getCurrentWeather().getTemperature()) + "c");

                viewHolder.weatherDetail.setText(mWeather.get(i).getCurrentWeather().getSummary() + "");

                Resources res = viewHolder.itemView.getContext().getResources();
                viewHolder.weatherIcon.setImageDrawable(ResourcesCompat.getDrawable(res
                        , Constants.setDrawableWeather(mWeather.get(i).getCurrentWeather().getIcon())
                        , null));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mCities.length;
    }

    public class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textName, weatherDetail, weatherTemp;
        ImageView weatherIcon;

        public CityHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.city_name);
            weatherDetail = itemView.findViewById(R.id.weather_detail);
            weatherTemp = itemView.findViewById(R.id.weather_temp);
            weatherIcon = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (cityListener != null) {
                int i = getAdapterPosition();
                cityListener.onCityClick(i, mCoordnates[i], mCities[i]);
            }
        }
    }

    public interface OnCityListener {
        void onCityClick(int position, String Coordinates, String City);
    }

    public void setOnItemClickListener(OnCityListener listener) {
        cityListener = listener;
    }
}
