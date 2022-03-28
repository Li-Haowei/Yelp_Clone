package com.example.yelpclone;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpService {
    @GET("businesses/search")
    public Call<Object> searchRestaurants(
            @Header("Authorization") String authHeader,
            @Query("term") String term,
            @Query("location") String location
    );
}
