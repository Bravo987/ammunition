package com.example.warrior.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.warrior.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    ImageSlider imageSlider;
    Button fire, ammo, scope;
    private static final int BOTTOM_HOME_ID = R.id.bottom_home;
    private static final int BOTTOM_CART_ID = R.id.bottom_cart;
    private static final int BOTTOM_HISTORY_ID = R.id.bottom_history;
    private static final int BOTTOM_PROFILE_ID = R.id.bottom_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fire = findViewById(R.id.firearmbtn);
        ammo = findViewById(R.id.ammobtn);
        scope = findViewById(R.id.Scopebtn);
        imageSlider = findViewById(R.id.image_slider);

        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.gun1, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.gun2, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.gun3, ScaleTypes.FIT));

        imageSlider.setImageList(imageList);

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ammo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Ammo.class);
                startActivity(intent);
            }
        });

        scope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, ScopeActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setSelectedItemId(BOTTOM_HOME_ID);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == BOTTOM_HOME_ID) {
                    return true;
                } else if (itemId == BOTTOM_CART_ID) {
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    finish();
                    return true;
                } else if (itemId == BOTTOM_HISTORY_ID) {
                    startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                    finish();
                    return true;
                } else if (itemId == BOTTOM_PROFILE_ID) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}
