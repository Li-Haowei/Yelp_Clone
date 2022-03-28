package com.example.yelpclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.yelp.com/v3/";
    private static final String API_KEY = "LRODHsBmKivCuPY8DmDdauyGsR1rCuIwi7bYG9UyisX0hJQtG_Xj9dHNewuqW5F4s04G8hpf7DkBFuPOIW5eD7M6WYD4DX4BWMvRntAzhqW3wuhnLFl4J4BBGpk_YnYx";
    private Gson gson;
    private Retrofit retrofit;
    private YelpService yp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        YelpRestaurants[] restaurants = new YelpRestaurants[10];
        RestaurantsAdapter rd = new RestaurantsAdapter(this, restaurants);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        yp = retrofit.create(YelpService.class);
        Call<YelpDataClasses> callAsync = yp.searchRestaurants("Bearer " + API_KEY,"Avocado Toast","New York");
        callAsync.enqueue(new Callback<YelpDataClasses>() {
            @Override
            public void onResponse(Call<YelpDataClasses> call, Response<YelpDataClasses> response) {
                Log.d("creation", "onResponse " + response.body().restaurants[0].location.address);
            }

            @Override
            public void onFailure(Call<YelpDataClasses> call, Throwable t) {
                Log.d("creation", "onFail " + t);
            }
        });

    }
}