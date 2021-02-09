package com.example.android.connectedweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ForecastAdapter.OnForecastItemClickListener {

    private ArrayList<ForecastDataItem> forecastDataItems = new ArrayList<>();
    private Toast longDescriptionToast;
    private ProgressBar progressBar;
    private RecyclerView forecastListRV;
    private String TAG = "mainLog";
    private String city = "Corvallis";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.location, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_location:
                Uri gmmIntentUri = Uri.parse("geo:44.5645659,-123.2620435?z=12");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        forecastListRV = findViewById(R.id.rv_forecast_list);
        forecastListRV.setLayoutManager(new LinearLayoutManager(this));
        forecastListRV.setHasFixedSize(true);
        forecastListRV.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        new getForecastDataItemsTask().execute(constructForecastURL(city));
    }

    @Override
    public void onForecastItemClick(ForecastDataItem forecastDataItem) {
//        if (longDescriptionToast != null) {
//            longDescriptionToast.cancel();
//        }
//        longDescriptionToast = Toast.makeText(this, forecastDataItem.getLongDescription(), Toast.LENGTH_LONG);
//        longDescriptionToast.show();
        Log.d(TAG, forecastDataItem.toString());
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("forecastDataItem", new Gson().toJson(forecastDataItem));
        intent.putExtra("city", city);
        startActivity(intent);
    }

    private String constructForecastURL(String cityName) {
        String appID = "68d9091ad07e6dafa1f88720eec57fea";
        return "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName
                + "&units=imperial&lang=en&appid=" + appID;
    }

    private class getForecastDataItemsTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            InputStream inputStream;
            try {
                url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                //  Log.d(TAG, stringBuilder.toString());
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, e.toString());
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.get("message").toString().equals("0")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        forecastDataItems.add(new Gson().fromJson(jsonArray.get(i).toString(), ForecastDataItem.class));
                        Log.d(TAG, forecastDataItems.get(i).toString());
                    }
                } else {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
            ForecastAdapter forecastAdapter = new ForecastAdapter(MainActivity.this);
            forecastListRV.setAdapter(forecastAdapter);
            forecastAdapter.updateForecastData(MainActivity.this.forecastDataItems);
            progressBar.setVisibility(View.INVISIBLE);
            forecastListRV.setVisibility(View.VISIBLE);
        }
    }
}