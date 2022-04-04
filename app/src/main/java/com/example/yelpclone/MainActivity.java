package com.example.yelpclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.Duration;
import java.util.ArrayList;

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
    private Call<YelpDataClasses> callAsync;
    private EditText etFood;
    private EditText etLocation;
    private Button btnSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etFood = findViewById(R.id.txFood);
        etLocation = findViewById(R.id.txLocation);
        btnSearch = findViewById(R.id.btnSearch);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        yp = retrofit.create(YelpService.class);

        btnSearch.setOnClickListener(view->{
            loading();
            if((etFood.getText().toString().equals("Food") ||etFood.getText().toString().length()==0 )||
                    (etLocation.getText().toString().equals("Location") ||etFood.getText().toString().length()==0 )){
                Toast toast = Toast.makeText(this, "You haven't input anything", Toast.LENGTH_LONG);
                toast.show();
            }else{
                callAsync = yp.searchRestaurants("Bearer " + API_KEY,etFood.getText().toString(),etLocation.getText().toString());
                callAsync.enqueue(new Callback<YelpDataClasses>() {
                    @Override
                    public void onResponse(Call<YelpDataClasses> call, Response<YelpDataClasses> response) {
                        ArrayList<YelpDataClasses> restaurants = new ArrayList<YelpDataClasses>();
                        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                        intent.putExtra("length", response.body().restaurants.length);
                        for (int i = 0; i < response.body().restaurants.length; i++) {
                            intent.putExtra(""+i,response.body().restaurants[i].name);
                            intent.putExtra("rating"+i,response.body().restaurants[i].rating);
                            intent.putExtra("price"+i,response.body().restaurants[i].price);
                            intent.putExtra("location"+i,response.body().restaurants[i].location.address);
                            intent.putExtra("image"+i,response.body().restaurants[i].imageUrl);
                        }
                        MainActivity.this.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<YelpDataClasses> call, Throwable t) {
                        loadingFailed();
                        Log.d("creation", "onFail " + t);
                    }
                });
            }
        });
    }
    private void loading(){
        Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show();
    }
    private void loadingFailed(){
        Toast.makeText(this,"Try Again",Toast.LENGTH_SHORT).show();
    }
}