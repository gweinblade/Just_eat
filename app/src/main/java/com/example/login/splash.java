package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    private static int splash_screen = 3000;
    Animation topAnim, buttomAnim;
    ImageView imageView;
    TextView textlogo,telogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.topsplashanimation);
        buttomAnim= AnimationUtils.loadAnimation(this, R.anim.botomsplachanimation);

        imageView = findViewById(R.id.screen_image);
        textlogo=  findViewById(R.id.logo);
        telogan =  findViewById(R.id.slogan);

        imageView.setAnimation(topAnim);
        textlogo.setAnimation(buttomAnim);
        telogan.setAnimation(buttomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash.this,Activity_principale.class);
                startActivity(intent);
                finish();
            }
        }, splash_screen);
    }
    }

