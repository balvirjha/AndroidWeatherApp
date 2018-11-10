package com.inducesmile.temptoday.modals;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class Clouds implements Parcelable {

    public final static Parcelable.Creator<Clouds> CREATOR = new Creator<Clouds>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Clouds createFromParcel(Parcel in) {
            return new Clouds(in);
        }

        public Clouds[] newArray(int size) {
            return (new Clouds[size]);
        }

    };
    @SerializedName("all")
    @Expose
    private Integer all;

    protected Clouds(Parcel in) {
        this.all = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Clouds() {
    }

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(all);
    }

    public int describeContents() {
        return 0;
    }

}