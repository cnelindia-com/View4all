package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;

import com.example.viewall.MainActivity;
import com.example.viewall.R;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*getSupportActionBar().hide();*/

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

        //Below code for set the background music in the app for test purpose
        mediaPlayer = MediaPlayer.create(SplashActivity.this, R.raw.background);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                Intent intent = new Intent(SplashActivity.this, BaseUrlActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, 5000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
}