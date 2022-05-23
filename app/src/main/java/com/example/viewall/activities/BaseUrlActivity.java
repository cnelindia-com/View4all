package com.example.viewall.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viewall.R;
import com.example.viewall.utils.SharePrefrancClass;

import java.io.File;

public class BaseUrlActivity extends AppCompatActivity {

    Button submitId;
    EditText baseUrlId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final int REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_url);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        sharedPreferences = getSharedPreferences("VIEWALL", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        submitId = findViewById(R.id.submitId);
        baseUrlId = findViewById(R.id.baseUrlId);

        //Saving the current page name in the prefrence
        SharePrefrancClass.getInstance(BaseUrlActivity.this).savePref("fromActivity",
                "");

        //code for permission.
        /*if (ActivityCompat.checkSelfPermission(BaseUrlActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(BaseUrlActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(BaseUrlActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, REQ_CODE);
        } else {
            *//*Toast.makeText(VideoShowActivity.this, "Permission Granted.", Toast.LENGTH_SHORT).show();*//*
//                    startActivity(new Intent(VideoShowActivity.this, SyncDataActivity.class));
            *//*callDownload();*//*
            //Code for create folder
            File dir = new File(Environment.getExternalStorageDirectory() + "/Download/view4all/");
            dir.mkdirs(); // creates needed dirs
        }*/



        submitId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePrefrancClass.getInstance(BaseUrlActivity.this).savePref("baseurl", baseUrlId.getText().toString().trim());
                editor.putString("baseurl2", baseUrlId.getText().toString());
                editor.commit();


                String val = SharePrefrancClass.getInstance(BaseUrlActivity.this).getPref("isLogin");
                if (val != null && val.equals("true")){
                    startActivity(new Intent(BaseUrlActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(BaseUrlActivity.this, RegisterActivity.class));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        /*switch (requestCode) {
            case REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    *//*Toast.makeText(this, "Request permission method", Toast.LENGTH_SHORT).show();*//*
                    *//*startActivity(new Intent(VideoShowActivity.this, SyncDataActivity.class));*//*
//                    callDownload();
                } else {
                    finishAffinity();
                }
                break;
        }*/
    }
}