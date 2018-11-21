package com.balvir.temptoday.modals.singledayweathermodal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class SingleDayWeatherResponse implements Parcelable {

    public final static Parcelable.Creator<SingleDayWeatherResponse> CREATOR = new Creator<SingleDayWeatherResponse>() {

        @SuppressWarnings({
                "unchecked"
        })
        public SingleDayWeatherResponse createFromParcel(Parcel in) {
            return new SingleDayWeatherResponse(in);
        }

        public SingleDayWeatherResponse[] newArray(int size) {
            return (new SingleDayWeatherResponse[size]);
        }

    };
    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private List<WeatherNew> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private Integer cod;

    protected SingleDayWeatherResponse(Parcel in) {
        this.coord = ((Coord) in.readValue((Coord.class.getClassLoader())));
        in.readList(this.weather, (WeatherNew.class.getClassLoader()));
        this.base = ((String) in.readValue((String.class.getClassLoader())));
        this.main = ((Main) in.readValue((Main.class.getClassLoader())));
        this.visibility = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.wind = ((Wind) in.readValue((Wind.class.getClassLoader())));
        this.clouds = ((Clouds) in.readValue((Clouds.class.getClassLoader())));
        this.dt = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.sys = ((Sys) in.readValue((Sys.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.cod = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public SingleDayWeatherResponse() {
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<WeatherNew> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherNew> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(coord);
        dest.writeList(weather);
        dest.writeValue(base);
        dest.writeValue(main);
        dest.writeValue(visibility);
        dest.writeValue(wind);
        dest.writeValue(clouds);
        dest.writeValue(dt);
        dest.writeValue(sys);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(cod);
    }

    public int describeContents() {
        return 0;
    }

}