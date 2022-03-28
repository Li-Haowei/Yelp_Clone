package com.example.yelpclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>{
    private Context context;
    private YelpRestaurants[] restaurants;
    public RestaurantsAdapter(Context context, YelpRestaurants[] restaurants) {
        this.context=context;
        this.restaurants = restaurants;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YelpRestaurants restaurant = restaurants[position];
        holder.bind(restaurant);
    }

    public int getItemCount(){
        return restaurants.length;
    }
    protected class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(YelpRestaurants restaurant) {
            tvName = itemView.findViewById(R.id.tvName);
            tvName.setText(restaurant.name);
        }
    }
}
