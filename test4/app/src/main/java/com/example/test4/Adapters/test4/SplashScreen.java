package com.example.test4.Adapters.test4;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test4.Adapters.test4.LogSign.Login;
import com.example.test4.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    ImageView image;
    TextView txt1, txt2;
    Animation bottomanim, topanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        initViews();

        topanim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        image.setAnimation(topanim);
        txt1.setAnimation(bottomanim);
        txt2.setAnimation(bottomanim);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        }, 4000);

    }

    private void initViews() {
        image = findViewById(R.id.milkimg);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
    }
}