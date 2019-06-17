package com.asmaa.khb.task.request;

import com.asmaa.khb.task.util.Constants;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.asmaa.khb.task.util.Constants.CONNECTION_TIMEOUT;
import static com.asmaa.khb.task.util.Constants.READ_TIMEOUT;
import static com.asmaa.khb.task.util.Constants.WRITE_TIMEOUT;


public class ServiceGenerator {
    private static OkHttpClient client = new OkHttpClient.Builder()
            //time to establish connection to the server
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)

            //time between each byte read from the server
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

            //time between each byte sent to the server
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    private static Retrofit.Builder retrofit_builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofit_builder.build();

    private static WeatherApi weatherApi = retrofit.create(WeatherApi.class);

    public static WeatherApi getWeatherApi()//to access in the another activity
    {
        return weatherApi;
    }
}
