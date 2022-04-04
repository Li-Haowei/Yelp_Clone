package com.example.yelpclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    private String[] names;
    private TextView[] restaurants;
    private TextView[] locations;
    private String[] ImageURLs;
    private ImageView[] images;
    private String[] location;
    private String[] price;
    private String[] rating;
    private int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        length = intent.getIntExtra("length",0);
        images = new ImageView[length];
        restaurants = new TextView[length];
        locations = new TextView[length];

        ImageURLs = new String[length];
        price = new String[length];
        location = new String[length];
        rating = new String[length];
        names = new String[length];
        for (int i = 0; i < length; i++) {
            names[i] = intent.getStringExtra(""+i);
            ImageURLs[i] = intent.getStringExtra("image"+i);
            price[i] = intent.getStringExtra("price"+i);
            rating[i] = intent.getStringExtra("rating"+i);
            location[i] = intent.getStringExtra("location"+i);
        }

        ScrollView scrollView = new ScrollView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParams);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(linearParams);
        scrollView.addView(linearLayout);

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(0, 0, 0, 30);
        params2.setMargins(0, 0, 0, 0);
        params1.gravity = Gravity.CENTER;
        params2.gravity = Gravity.CENTER;
        for (int i = 0; i < length; i++) {
            restaurants[i] = new TextView(this);
            restaurants[i].setText("Name: " + names[i]+'\n'+ "Rating: " + rating[i]+'\n'+ "Price: " + price[i]+'\n');
            restaurants[i].setTextColor(Color.parseColor("#000000"));
            restaurants[i].setLayoutParams(params2);
            linearLayout.addView(restaurants[i]);

            locations[i] = new TextView(this);
            locations[i].setText(location[i]);
            locations[i].setTextColor(Color.parseColor("#0027b3"));
            locations[i].setLayoutParams(params2);
            setOnClick(locations[i], i);
            linearLayout.addView(locations[i]);

            images[i] = new ImageView(this);
            images[i].setLayoutParams(params1);
            new ImageLoadTask(ImageURLs[i], images[i]).execute();
            linearLayout.addView(images[i]);
        }
        LinearLayout linear = findViewById(R.id.rootContainer);
        linear.addView(scrollView);
    }
    private void setOnClick(final TextView tv, final int i){
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loc = location[i];
                loc = loc.replace(' ', '+');
                Intent geoLocationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + loc));
                startActivity(geoLocationIntent);
            }
        });
    }
}