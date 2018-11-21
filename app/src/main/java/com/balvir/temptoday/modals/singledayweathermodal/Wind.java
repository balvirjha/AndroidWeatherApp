package com.balvir.temptoday.modals.singledayweathermodal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class Wind implements Parcelable {

    public final static Parcelable.Creator<Wind> CREATOR = new Creator<Wind>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        public Wind[] newArray(int size) {
            return (new Wind[size]);
        }

    };
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Double deg;

    protected Wind(Parcel in) {
        this.speed = ((Double) in.readValue((Double.class.getClassLoader())));
        this.deg = ((Double) in.readValue((Integer.class.getClassLoader())));
    }

    public Wind() {
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(speed);
        dest.writeValue(deg);
    }

    public int describeContents() {
        return 0;
    }

}
