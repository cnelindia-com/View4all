package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viewall.R;
import com.example.viewall.models.contact.ContactResponse;
import com.example.viewall.models.getuser.GetUserResponse;
import com.example.viewall.serviceapi.RetrofitClient;
import com.example.viewall.utils.SharePrefrancClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameId, cellNumberId, etMessageId;
    String strId;
    ProgressDialog progressDialog;
    Button btnSendId, btnVideoId,btnAddId, btnIssueId, btnQuesId, btnRewardId, btnOtherId;
    String strValue;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

        strId = SharePrefrancClass.getInstance(ContactUsActivity.this).getPref("id");

        nameId = findViewById(R.id.nameId);
        cellNumberId = findViewById(R.id.cellNumberId);
        etMessageId = findViewById(R.id.etMessageId);

        btnSendId = findViewById(R.id.btnSendId);
        btnSendId.setOnClickListener(this);
        btnVideoId = findViewById(R.id.btnVideoId);
        btnVideoId.setOnClickListener(this);
        btnAddId = findViewById(R.id.btnAddId);
        btnAddId.setOnClickListener(this);
        btnIssueId = findViewById(R.id.btnIssueId);
        btnIssueId.setOnClickListener(this);
        btnQuesId = findViewById(R.id.btnQuesId);
        btnQuesId.setOnClickListener(this);
        btnRewardId = findViewById(R.id.btnRewardId);
        btnRewardId.setOnClickListener(this);
        btnOtherId = findViewById(R.id.btnOtherId);
        btnOtherId.setOnClickListener(this);

        callGetUserDataApi();
    }

    public void callGetUserDataApi() {
        progressDialog.show();

        Call<GetUserResponse> call = RetrofitClient.getInstance().getMyApi().getUserData(strId);

        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.body() != null){
                    progressDialog.dismiss();

                    nameId.setText(response.body().getData().getName());
                    cellNumberId.setText(response.body().getData().getPhone());
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ContactUsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callSaveContactApi() {
        progressDialog.show();

        Call<ContactResponse> call = RetrofitClient.getInstance().getMyApi().sendContact(
                SharePrefrancClass.getInstance(ContactUsActivity.this).getPref("id"), nameId.getText().toString(),
                cellNumberId.getText().toString(), strValue, etMessageId.getText().toString());

        call.enqueue(new Callback<ContactResponse>() {
            @Override
            public void onResponse(Call<ContactResponse> call, Response<ContactResponse> response) {
                if (response.body() != null){
                    progressDialog.dismiss();
                    Toast.makeText(ContactUsActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ContactUsActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ContactResponse> call, Throwable t) {
                progressDialog.dismiss();
//                Toast.makeText(ContactUsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnVideoId:

                strValue = "Video";
                /*Toast.makeText(ContactUsActivity.this, strValue, Toast.LENGTH_SHORT).show();*/
                btnVideoId.setBackgroundResource(R.color.lighRed);
                btnAddId.setBackgroundResource(R.color.lightgray);
                btnIssueId.setBackgroundResource(R.color.lightgray);
                btnQuesId.setBackgroundResource(R.color.lightgray);
                btnRewardId.setBackgroundResource(R.color.lightgray);
                btnOtherId.setBackgroundResource(R.color.lightgray);
                break;

            case R.id.btnAddId:

                strValue = "Advertising";
                /*Toast.makeText(ContactUsActivity.this, strValue, Toast.LENGTH_SHORT).show();*/
                btnAddId.setBackgroundResource(R.color.lighRed);
                btnVideoId.setBackgroundResource(R.color.lightgray);
                btnIssueId.setBackgroundResource(R.color.lightgray);
                btnQuesId.setBackgroundResource(R.color.lightgray);
                btnRewardId.setBackgroundResource(R.color.lightgray);
                btnOtherId.setBackgroundResource(R.color.lightgray);
                break;

            case R.id.btnIssueId:

                strValue = "Issues";
                /*Toast.makeText(ContactUsActivity.this, strValue, Toast.LENGTH_SHORT).show();*/
                btnIssueId.setBackgroundResource(R.color.lighRed);

                btnVideoId.setBackgroundResource(R.color.lightgray);
                btnAddId.setBackgroundResource(R.color.lightgray);
                btnQuesId.setBackgroundResource(R.color.lightgray);
                btnRewardId.setBackgroundResource(R.color.lightgray);
                btnOtherId.setBackgroundResource(R.color.lightgray);
                break;

            case R.id.btnQuesId:

                strValue = "Questions";
                /*Toast.makeText(ContactUsActivity.this, strValue, Toast.LENGTH_SHORT).show();*/
                btnQuesId.setBackgroundResource(R.color.lighRed);

                btnVideoId.setBackgroundResource(R.color.lightgray);
                btnAddId.setBackgroundResource(R.color.lightgray);
                btnIssueId.setBackgroundResource(R.color.lightgray);
                btnRewardId.setBackgroundResource(R.color.lightgray);
                btnOtherId.setBackgroundResource(R.color.lightgray);
                break;

            case R.id.btnRewardId:

                strValue = "Rewards";
                /*Toast.makeText(ContactUsActivity.this, strValue, Toast.LENGTH_SHORT).show();*/
                btnRewardId.setBackgroundResource(R.color.lighRed);

                btnVideoId.setBackgroundResource(R.color.lightgray);
                btnAddId.setBackgroundResource(R.color.lightgray);
                btnIssueId.setBackgroundResource(R.color.lightgray);
                btnQuesId.setBackgroundResource(R.color.lightgray);
                btnOtherId.setBackgroundResource(R.color.lightgray);
                break;

            case R.id.btnOtherId:

                strValue = "Other";
                /*Toast.makeText(ContactUsActivity.this, strValue, Toast.LENGTH_SHORT).show();*/
                btnOtherId.setBackgroundResource(R.color.lighRed);

                btnVideoId.setBackgroundResource(R.color.lightgray);
                btnAddId.setBackgroundResource(R.color.lightgray);
                btnIssueId.setBackgroundResource(R.color.lightgray);
                btnQuesId.setBackgroundResource(R.color.lightgray);
                btnRewardId.setBackgroundResource(R.color.lightgray);
                break;

            case R.id.btnSendId:
                callSaveContactApi();
                break;
        }

    }
}