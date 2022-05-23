package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.viewall.R;

public class CategoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout popVidLayoutId, sporVidLayoutId, musVidLayoutId, gospVidLayoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

        popVidLayoutId = findViewById(R.id.popVidLayoutId);
        sporVidLayoutId = findViewById(R.id.sporVidLayoutId);
        musVidLayoutId = findViewById(R.id.musVidLayoutId);
        gospVidLayoutId = findViewById(R.id.gospVidLayoutId);

        popVidLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, VideoShowActivity.class));
            }
        });


        sporVidLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, VideoShowActivity.class));
            }
        });

        musVidLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, VideoShowActivity.class));
            }
        });

        gospVidLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, VideoShowActivity.class));
            }
        });
    }
}