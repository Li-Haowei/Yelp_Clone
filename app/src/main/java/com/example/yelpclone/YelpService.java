package com.example.yelpclone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpService {
    @GET("businesses/search")
    public Call<String> searchRestaurants(
            @Query("term") String term,
            @Query("location") String location
    );
}
