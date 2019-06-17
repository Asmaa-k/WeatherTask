package com.asmaa.khb.task.request;

import com.asmaa.khb.task.models.CurrentArea;
import com.asmaa.khb.task.util.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherApi {

    @GET("forecast/" + Constants.API_KEY + "/{coor}")
    Observable<CurrentArea> getTodayWeather(
            @Path("coor") String latlan
    );

}
