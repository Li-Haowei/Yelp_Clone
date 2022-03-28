package com.example.yelpclone;

import com.google.gson.annotations.SerializedName;

public class YelpDataClasses {
    @SerializedName("total") int total;
    @SerializedName("businesses") YelpRestaurants[] restaurants;
}
class YelpRestaurants{
    @SerializedName("name") String name;
    @SerializedName("rating") String rating;
    @SerializedName("price") String price;
    @SerializedName("review_count") int numReviews;
    @SerializedName("image_url") String imageUrl;
    @SerializedName("categories") YelpCategory[] categories;
    @SerializedName("location") YelpLocation location;
}
class YelpCategory{
    @SerializedName("title") String title;
}

class YelpLocation{
    @SerializedName("address") String address;
}