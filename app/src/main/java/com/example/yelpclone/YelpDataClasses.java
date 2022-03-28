package com.example.yelpclone;

import com.google.gson.annotations.SerializedName;

public class YelpDataClasses {
    @SerializedName("total") int total;
    @SerializedName("businesses") YelpRestaurants[] restaurants;
}
class YelpRestaurants{
    @SerializedName("name") String name;
}