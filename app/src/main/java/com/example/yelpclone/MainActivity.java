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
    private static final String API_KEY = "hiding boys";
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

        etFood = findViewById(R.id.txFood); //input field for food
        etLocation = findViewById(R.id.txLocation); //input field for location
        btnSearch = findViewById(R.id.btnSearch); //search button

        retrofit = new Retrofit.Builder() //build a new Retrofit class
                .baseUrl(BASE_URL) //Base url, all Yelp fusion API endpoints
                .addConverterFactory(GsonConverterFactory.create()) //This is the adapter that converts JSON data into GSON which can be read and write by Java(not the only way)
                .build();
        yp = retrofit.create(YelpService.class); //this class reads the GSON file

        btnSearch.setOnClickListener(view->{
            loading(); //Toast message
            if((etFood.getText().toString().equals("Food") ||etFood.getText().toString().length()==0 )||
                    (etLocation.getText().toString().equals("Location") ||etFood.getText().toString().length()==0 )){
                //check if user input something
                Toast toast = Toast.makeText(this, "You haven't input anything", Toast.LENGTH_LONG);
                toast.show();
            }else{
                //Authentication : https://www.yelp.com/developers/documentation/v3/authentication, Query(term), Query(location)
                //For example: "Bearer API_KEY BBQ Boston", why? because retrofit works that way
                callAsync = yp.searchRestaurants("Bearer " + API_KEY,etFood.getText().toString(),etLocation.getText().toString());
                //Retrofit has two call method: Call.execute() (Synchronously) and Call.enqueue() (Asynchronously) Methods : https://howtodoinjava.com/retrofit2/retrofit-sync-async-calls/
                //In other words, execute() runs the request on the current thread, enqueue() runs the request on the background thread and runs the callback on the current thread
                callAsync.enqueue(new Callback<YelpDataClasses>() {
                    @Override
                    public void onResponse(Call<YelpDataClasses> call, Response<YelpDataClasses> response) {
                        Intent intent = new Intent(MainActivity.this, DisplayActivity.class); //This will be our display class
                        //Everything below will be the information for display
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
                        loadingFailed(); //Toast message
                        Log.d("creation", "onFail " + t); //debug purpose
                    }
                });
            }
        });
    }
    //Toast messages
    private void loading(){
        Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show();
    }
    private void loadingFailed(){
        Toast.makeText(this,"Try Again",Toast.LENGTH_SHORT).show();
    }
}
