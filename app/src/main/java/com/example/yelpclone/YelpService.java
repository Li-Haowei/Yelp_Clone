package com.example.yelpclone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpService {
    @GET("businesses/search")
    Call<String> getTerm(@Query("term") String term);
    Call<String> getLocation(@Query("location") String location);
}
