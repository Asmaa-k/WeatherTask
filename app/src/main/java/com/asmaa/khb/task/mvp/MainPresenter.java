package com.asmaa.khb.task.mvp;

import android.util.Log;

import com.asmaa.khb.task.models.CurrentArea;
import com.asmaa.khb.task.realm.RealmServices;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;


public class MainPresenter {

    private RealmServices realmServices;

    private View view;

    private CompositeDisposable disposables = new CompositeDisposable();

    private static final String TAG = "MainPresenter";

    public MainPresenter(View view) {
        this.realmServices = new RealmServices();
        this.view = view;
    }

    public void getWeather(String mSelectedCity, int id) {
        CurrentArea area = realmServices.getRealmData(id);
        view.updateInf(area);

        realmServices.syncData(mSelectedCity, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CurrentArea>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(CurrentArea currentArea) {
                        Log.d(TAG, "onNext: " + currentArea.getId());

                        if (currentArea != null)
                            view.updateInf(currentArea);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    public List<CurrentArea> getAllWeather() {
        List<CurrentArea> mWeather;
        mWeather = realmServices.getAllWeather();
        return mWeather;
    }

    public Realm getRealm() {
        return realmServices.getRealm();
    }

    public CompositeDisposable getDisposables() {
        return disposables;
    }

    public interface View {
        void updateInf(CurrentArea currentArea);
    }
}
