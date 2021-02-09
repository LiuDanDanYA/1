package com.example.android.connectedweather;

import android.util.Log;

import com.google.gson.internal.$Gson$Preconditions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForecastDataItem {
    private String dt_txt;
    private Main main;
    private ArrayList<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private int visibility;
    private double pop;
    private Sys sys;

    public ForecastDataItem() {
    }

    public String getIconURL() {
        return this.weather.get(0).icon;
    }

    public double getWindSpeed() {
        return this.wind.speed;
    }

    public int getCloudiness() {
        return this.clouds.all;
    }

    public String getTimeString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date;
        try {
            date = formatter.parse(this.dt_txt);
            DateFormat format = DateFormat.getTimeInstance(DateFormat.SHORT);
            Log.d("xxxx", format.format(date));
            return format.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    public String getMonthString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date;
        try {
            date = formatter.parse(this.dt_txt);
        } catch (ParseException e) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
    }

    public String getDayString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date;
        try {
            date = formatter.parse(this.dt_txt);
        } catch (ParseException e) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public double getHighTemp() {
        return this.main.temp_max;
    }

    public double getLowTemp() {
        return this.main.temp_min;
    }

    public double getPop() {
        return pop;
    }

    public String getShortDescription() {
        return this.weather.get(0).description;
    }

    public String getLongDescription() {
        return "longDescription";
    }

    private static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int sea_level;
        private int grnd_level;
        private int humidity;
        private double temp_kf;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(double feels_like) {
            this.feels_like = feels_like;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public int getSea_level() {
            return sea_level;
        }

        public void setSea_level(int sea_level) {
            this.sea_level = sea_level;
        }

        public int getGrnd_level() {
            return grnd_level;
        }

        public void setGrnd_level(int grnd_level) {
            this.grnd_level = grnd_level;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public double getTemp_kf() {
            return temp_kf;
        }

        public void setTemp_kf(double temp_kf) {
            this.temp_kf = temp_kf;
        }

        @Override
        public String toString() {
            return "Main{" +
                    "temp=" + temp +
                    ", feels_like=" + feels_like +
                    ", temp_min=" + temp_min +
                    ", temp_max=" + temp_max +
                    ", pressure=" + pressure +
                    ", sea_level=" + sea_level +
                    ", grnd_level=" + grnd_level +
                    ", humidity=" + humidity +
                    ", temp_kf=" + temp_kf +
                    '}';
        }
    }

    private static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "id=" + id +
                    ", main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    private static class Clouds {
        private int all;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        @Override
        public String toString() {
            return "Clouds{" +
                    "all=" + all +
                    '}';
        }
    }

    private static class Wind {
        private double speed;
        private int deg;

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        @Override
        public String toString() {
            return "Wind{" +
                    "speed=" + speed +
                    ", deg=" + deg +
                    '}';
        }
    }

    private static class Sys {
        private String pop;

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        @Override
        public String toString() {
            return "Sys{" +
                    "pop='" + pop + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ForecastDataItem{" +
                "dt_txt='" + dt_txt + '\'' +
                ", main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", visibility=" + visibility +
                ", pop=" + pop +
                ", sys=" + sys +
                '}';
    }
}
