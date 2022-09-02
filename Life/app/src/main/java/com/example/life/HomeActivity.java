package com.example.life;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        img = findViewById(R.id.detectfv);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        toolbar = findViewById(R.id.toolbar);

        initBottomNavView();

        setSupportActionBar(toolbar);

        img.setOnClickListener(this);

    }

    private void initBottomNavView() {
        bottomNavigationView.setSelectedItemId(R.id.item_recipes);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_meals:

                        break;
                    case R.id.item_chat:

                        break;
                    case R.id.item_recipes:

                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_chat:

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detectfv:
                startActivity(new Intent(HomeActivity.this, CalorieActivity.class));
                break;
            default:
                break;
        }
    }
}