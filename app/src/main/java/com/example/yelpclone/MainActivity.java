package com.example.yelpclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.yelp.com/v3/";
    private static final String API_KEY = "LRODHsBmKivCuPY8DmDdauyGsR1rCuIwi7bYG9UyisX0hJQtG_Xj9dHNewuqW5F4s04G8hpf7DkBFuPOIW5eD7M6WYD4DX4BWMvRntAzhqW3wuhnLFl4J4BBGpk_YnYx";
    private Retrofit retrofit;
    private YelpService yp;
    private RecyclerView rvRestaurant;
    private YelpRestaurants[] restaurants;
    private RestaurantsAdapter adapter;
    private int currentMaxSize = 10;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurants = new YelpRestaurants[currentMaxSize];
        adapter = new RestaurantsAdapter(MainActivity.this, restaurants);
        rvRestaurant = findViewById(R.id.rvRestaurants);
        rvRestaurant.setLayoutManager(new LinearLayoutManager(this));

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        yp = retrofit.create(YelpService.class);
        Call<YelpDataClasses> callAsync = yp.searchRestaurants("Bearer " + API_KEY,"Avocado Toast","New York");
        callAsync.enqueue(new Callback<YelpDataClasses>() {
            @Override
            public void onResponse(Call<YelpDataClasses> call, Response<YelpDataClasses> response) {
                //Log.d("creation", "onResponse " + response.body().restaurants[0].name);
                for (int i = 0; i < response.body().restaurants.length; i++) {
                    Log.d("creation", "onResponse " + response.body().restaurants[i].name);
                }
                /*
                YelpDataClasses body = response.body();
                try {
                    for (int i = 0; i < body.restaurants.length; i++) {
                        restaurants[currentIndex] = body.restaurants[i];
                        Log.d("creation", body.restaurants[i].name);
                        currentIndex++;
                        if(currentIndex==currentMaxSize){
                            increment();
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Log.d("creation", "onResponse failed");
                    e.printStackTrace();

                }

                 */

            }

            @Override
            public void onFailure(Call<YelpDataClasses> call, Throwable t) {
                Log.d("creation", "onFail " + t);
            }
        });
        //rvRestaurant.setAdapter(adapter);
    }
    private void increment(){
        currentMaxSize += 10;
        YelpRestaurants[] new_restaurants = new YelpRestaurants[currentMaxSize];
        for (int i = 0; i < restaurants.length; i++) {
            new_restaurants[i] = restaurants[i];
        }
        restaurants = new_restaurants;
    }
}