package com.asmaa.khb.task.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class CurrentArea extends RealmObject implements Parcelable {

    @PrimaryKey()
    int id;

    String timezone;

    @SerializedName("currently")
    @Expose
    CurrentWeather currentWeather;

    public CurrentArea() {
    }

    public CurrentArea(String timezone, CurrentWeather currentWeather) {
        this.timezone = timezone;
        this.currentWeather = currentWeather;
    }

    protected CurrentArea(Parcel in) {
        id = in.readInt();
        timezone = in.readString();
        currentWeather = in.readParcelable(CurrentWeather.class.getClassLoader());
    }

    public static final Creator<CurrentArea> CREATOR = new Creator<CurrentArea>() {
        @Override
        public CurrentArea createFromParcel(Parcel in) {
            return new CurrentArea(in);
        }

        @Override
        public CurrentArea[] newArray(int size) {
            return new CurrentArea[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimezone() {
        return timezone;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(timezone);
        dest.writeParcelable(currentWeather, flags);
    }
}
