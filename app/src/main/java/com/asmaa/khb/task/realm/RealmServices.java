package com.asmaa.khb.task.realm;

import android.util.Log;

import com.asmaa.khb.task.models.CurrentArea;
import com.asmaa.khb.task.request.ServiceGenerator;
import com.asmaa.khb.task.util.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class RealmServices {

    private static final String TAG = "RealmServices";

    public static Observable<CurrentArea> syncData(String mSelectedCity, final int id) {
        return ServiceGenerator.getWeatherApi().getTodayWeather(mSelectedCity)
                .map(new Function<CurrentArea, CurrentArea>() {

                    @Override
                    public CurrentArea apply(CurrentArea currentArea) throws Exception {

                        currentArea.getCurrentWeather().setTime(System.currentTimeMillis());
                        currentArea.setId(id);

                        Realm realm = Realm.getDefaultInstance();
                        CurrentArea query = realm.where(CurrentArea.class).equalTo("id", id).findFirst();
                        Log.d(TAG, "apply: CityID "+query);
                        Log.d(TAG, "apply: if realm is empty: "+realm.isEmpty());


                        // if query is null then realm have no data
                        // we save data into realm for future use
                        if (realm.isEmpty() || query == null) {
                            Log.d(TAG, "apply: access only if we want to add data into realm for the first time");
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(currentArea);
                            realm.commitTransaction();

                        }

                        //deciding if we want to refresh the realm(to avoid memory leak)
                        int lastRefresh = (int) (query.getCurrentWeather().getTime()/1000);
                        int currentTime = (int) (System.currentTimeMillis() / 1000);

                        Log.d(TAG, "apply: currentTime - lastRefresh " + (currentTime - lastRefresh));
                        Log.d(TAG, "apply: " + (60 * 60 * 3));
                        Log.d(TAG, "apply: lastRefresh " + lastRefresh);
                        Log.d(TAG, "apply: currentTime "+currentTime);

                        //we refresh every 3 hours
                        if ((currentTime - lastRefresh) >= Constants.Weather_REFRESH_TIME) {
                            Log.d(TAG, "apply: access to refresh the data");
                            Refresh(query);
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(currentArea);
                            realm.commitTransaction();
                        }

                        return currentArea;
                    }
                }).subscribeOn(Schedulers.io());
    }

    //we clear data to make room and avoid memory leak
    private static void Refresh( CurrentArea query) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        query.deleteFromRealm();
        realm.commitTransaction();
    }

    //Get data from realm
    public static CurrentArea getRealmData(int id) {

        Realm realm = Realm.getDefaultInstance();
        if (!realm.isEmpty()) {
            CurrentArea first = realm.where(CurrentArea.class).equalTo("id", id).findFirst();
            return first;
        }
        return null;
    }

    //Get all data from realm to update main widget(MainActivity)
    public static List<CurrentArea> getAllWeather() {

        List<CurrentArea> mWeather = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            List<CurrentArea> reasults = realm.where(CurrentArea.class).greaterThanOrEqualTo("id", 0).findAll();
            for (CurrentArea c : reasults)
                mWeather.add(c);
        }
        return mWeather;
    }

    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }
}