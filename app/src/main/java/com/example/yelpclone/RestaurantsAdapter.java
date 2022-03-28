package com.example.yelpclone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
        //Log.d("creation", ""+restaurants.length);
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
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            Log.d("creation",tvName.getText().toString());
            Log.d("creation",restaurant.name);
            tvName.setText(restaurant.name);
            Log.d("creation", "set text good");
        }
    }
}
