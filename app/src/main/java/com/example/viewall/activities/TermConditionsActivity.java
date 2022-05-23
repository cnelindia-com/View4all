package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;

import com.example.viewall.R;

public class TermConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_conditions);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
    }
}