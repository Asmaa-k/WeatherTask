package com.asmaa.khb.task.models;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class CurrentWeather extends RealmObject implements Parcelable {

    long time;
    float temperature;
    float pressure;
    float windSpeed;
    String icon;
    String summary;
    String humidity;


    public CurrentWeather() {
    }


    public CurrentWeather(long time, float temperature, float pressure, float windSpeed, String icon, String summary, String humidity) {
        this.time = time;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.icon = icon;
        this.summary = summary;
        this.humidity = humidity;
    }

    protected CurrentWeather(Parcel in) {
        time = in.readLong();
        temperature = in.readFloat();
        pressure = in.readFloat();
        windSpeed = in.readFloat();
        icon = in.readString();
        summary = in.readString();
        humidity = in.readString();
    }

    public static final Creator<CurrentWeather> CREATOR = new Creator<CurrentWeather>() {
        @Override
        public CurrentWeather createFromParcel(Parcel in) {
            return new CurrentWeather(in);
        }

        @Override
        public CurrentWeather[] newArray(int size) {
            return new CurrentWeather[size];
        }
    };

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(time);
        dest.writeFloat(temperature);
        dest.writeFloat(pressure);
        dest.writeFloat(windSpeed);
        dest.writeString(icon);
        dest.writeString(summary);
        dest.writeString(humidity);
    }
}

