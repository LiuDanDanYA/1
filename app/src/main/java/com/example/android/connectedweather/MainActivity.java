package com.example.android.connectedweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastItemClickListener {

    private ArrayList<ForecastDataItem> forecastDataItems;
    private Toast longDescriptionToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView forecastListRV = findViewById(R.id.rv_forecast_list);
        forecastListRV.setLayoutManager(new LinearLayoutManager(this));
        forecastListRV.setHasFixedSize(true);

        initForecastDataItems();
        ForecastAdapter forecastAdapter = new ForecastAdapter(this);
        forecastListRV.setAdapter(forecastAdapter);
        forecastAdapter.updateForecastData(this.forecastDataItems);
    }

    @Override
    public void onForecastItemClick(ForecastDataItem forecastDataItem) {
        if (longDescriptionToast != null) {
            longDescriptionToast.cancel();
        }
        longDescriptionToast = Toast.makeText(this, forecastDataItem.getLongDescription(), Toast.LENGTH_LONG);
        longDescriptionToast.show();
    }

    private void initForecastDataItems() {
        this.forecastDataItems = new ArrayList<>();
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                14,
                51,
                43,
                0.25,
                "Mostly sunny",
                "Mostly sunny with clouds increasing in the evening"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                15,
                55,
                39,
                0.8,
                "AM showers",
                "Morning showers receding in the afternoon, with patches of sun later in the day"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                16,
                47,
                39,
                0.1,
                "AM fog/PM clouds",
                "Cooler, with morning fog lifting into a cloudy day"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                17,
                53,
                36,
                0.6,
                "AM showers",
                "Chance of light rain in the morning"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                18,
                49,
                33,
                0.1,
                "Partly cloudy",
                "Early clouds clearing as the day goes on; nighttime temperatures approaching freezing"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                19,
                49,
                36,
                0.15,
                "Partly cloudy",
                "Clouds increasing throughout the day with periods of sun interspersed"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                20,
                48,
                38,
                0.30,
                "Mostly cloudy",
                "Cloudy all day, with a slight chance of rain late in the evening"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                21,
                45,
                35,
                0.5,
                "Showers",
                "Cooler with periods of rain throughout the day"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                22,
                45,
                30,
                0.3,
                "AM showers",
                "Cool with a chance of rain in the morning; nighttime temperatures just below freezing"
        ));
        forecastDataItems.add(new ForecastDataItem(
                2021,
                0,
                23,
                44,
                31,
                0.5,
                "Few showers",
                "Cool with a chance rain throughout the day; nighttime temperatures just below freezing"
        ));
    }
}