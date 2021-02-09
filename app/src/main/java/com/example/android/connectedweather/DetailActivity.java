package com.example.android.connectedweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private ForecastDataItem forecastDataItem;
    private String city;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Weather forecast from CS 492 Weather from" + city + ",")
                        .append(forecastDataItem.getMonthString() + " " + forecastDataItem.getDayString() + ",")
                        .append(forecastDataItem.getTimeString() + ";")
                        .append(forecastDataItem.getShortDescription() + "with high of")
                        .append(forecastDataItem.getHighTemp() + "°F" + ",a low of")
                        .append(forecastDataItem.getLowTemp() + "°F,")
                        .append((int) forecastDataItem.getPop() * 100 + "may rain")
                        .append("Wind speed is " + forecastDataItem.getWindSpeed() + "mph");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String JsonData = intent.getStringExtra("forecastDataItem");
        city = intent.getStringExtra("city");
        forecastDataItem = new Gson().fromJson(JsonData, ForecastDataItem.class);
        imageView = findViewById(R.id.weatherIV);
        imageView.setVisibility(View.INVISIBLE);
        progressBar = findViewById(R.id.weatherProcess);
        progressBar.setVisibility(View.VISIBLE);
        TextView cityTV = findViewById(R.id.cityTV);
        cityTV.setText("🏙️ " + city);
        TextView dateTV = findViewById(R.id.dateTV);
        dateTV.setText("📅 " + forecastDataItem.getMonthString() + " " + forecastDataItem.getDayString() + ", " + forecastDataItem.getTimeString());
        TextView lowTempTV = findViewById(R.id.lowTempTV);
        lowTempTV.setText("🌡 " + forecastDataItem.getLowTemp() + " °F");
        TextView highTempTV = findViewById(R.id.highTempTV);
        highTempTV.setText("🌡 " + forecastDataItem.getHighTemp() + " °F");
        TextView popTV = findViewById(R.id.popTV);
        popTV.setText("☔ " + (int) (forecastDataItem.getPop() * 100.0) + "% precip.");
        TextView cloudTV = findViewById(R.id.cloudTV);
        cloudTV.setText("🌫️ " + forecastDataItem.getCloudiness() + "% cloudiness");
        TextView windTV = findViewById(R.id.windTV);
        windTV.setText("🌀 " + forecastDataItem.getWindSpeed() + " mph");
        TextView desTV = findViewById(R.id.descriptionTV);
        desTV.setText(forecastDataItem.getShortDescription());
        String url = "https://openweathermap.org/img/wn/" + forecastDataItem.getIconURL() + "@4x.png";
        new DetailActivity.getIconClass().execute(url);
    }

    private class getIconClass extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url;
            InputStream inputStream;
            try {
                url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                inputStream = connection.getInputStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
    }
}