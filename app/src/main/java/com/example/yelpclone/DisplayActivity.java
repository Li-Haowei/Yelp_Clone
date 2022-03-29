package com.example.yelpclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    private String[] names;
    private TextView[] restaurants;
    private String[] ImageURLs;
    private ImageView[] images;
    private int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        length = intent.getIntExtra("length",0);
        images = new ImageView[length];
        restaurants = new TextView[length];
        ImageURLs = new String[length];
        names = new String[length];
        for (int i = 0; i < length; i++) {
            names[i] = intent.getStringExtra(""+i);
            ImageURLs[i] = intent.getStringExtra("image"+i);
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
        params1.setMargins(0, 30, 0, 30);
        params1.gravity = Gravity.CENTER;
        for (int i = 0; i < length; i++) {
            restaurants[i] = new TextView(this);
            restaurants[i].setText(names[i]);
            restaurants[i].setLayoutParams(params1);
            linearLayout.addView(restaurants[i]);

            images[i] = new ImageView(this);
            images[i].setLayoutParams(params1);
            new ImageLoadTask(ImageURLs[i], images[i]).execute();
            linearLayout.addView(images[i]);

        }
        LinearLayout linear = findViewById(R.id.rootContainer);
        linear.addView(scrollView);
    }
}