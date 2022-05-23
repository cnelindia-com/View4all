package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viewall.R;
import com.example.viewall.models.register.RegisterResponse;
import com.example.viewall.serviceapi.RetrofitClient;
import com.example.viewall.utils.SharePrefrancClass;

import java.net.NetworkInterface;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button submit;
    EditText firstname, lastname, email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    String strMacAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        strMacAddress = getMacAddress();
        Toast.makeText(RegisterActivity.this, "MAC "+getMacAddress(), Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        submit = findViewById(R.id.submit);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        email.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        /*NumberFormat.getInstance().format(email.getText().toString());*/

        PhoneNumberUtils.formatNumber(email.getText().toString());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    callRegisterApi();
                }
            }
        });

    }

    public void callRegisterApi() {
        progressDialog.show();

        String strFirstName = firstname.getText().toString().trim();
        String strLastName = lastname.getText().toString().trim();
        String strEmail = email.getText().toString().trim();

        Call<RegisterResponse> call = RetrofitClient.getInstance().getMyApi().register(strFirstName,
                strLastName, strEmail, strMacAddress/*, "1111111111"*/);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getStatus().equals("Success")) {
                        Toast.makeText(RegisterActivity.this,
                                response.body().getData().getOFirstName() + " Register Successfully.",
                                Toast.LENGTH_SHORT).show();

                        SharePrefrancClass.getInstance(RegisterActivity.this).savePref("fname", response.body().getData().getOFirstName());
                        SharePrefrancClass.getInstance(RegisterActivity.this).savePref("lname", response.body().getData().getOLastName());
                        SharePrefrancClass.getInstance(RegisterActivity.this).savePref("email", response.body().getData().getEmail());
                        SharePrefrancClass.getInstance(RegisterActivity.this).savePref("id", response.body().getData().getId());
                        SharePrefrancClass.getInstance(RegisterActivity.this).savePref("phone_number", response.body().getData().getOPhone());

                        //Saving register status
                        SharePrefrancClass.getInstance(RegisterActivity.this).savePref("isLogin", "true");

                        startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean validate() {
        if (TextUtils.isEmpty(firstname.getText())) {
            firstname.setError("Please enter First name");
            firstname.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(lastname.getText())) {
            lastname.setError("Please enter Last name");
            lastname.requestFocus();
            return false;
        } /*else if (!email.getText().toString().matches(emailPattern)) {
            email.setError("Please enter valid email id");
            email.requestFocus();
            return false;
        }*/else if (TextUtils.isEmpty(email.getText())) {
            email.setError("Please enter Mobile Number");
            email.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    //Method for get mac address
    public static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : all) {
                if (!networkInterface.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = networkInterface.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                Log.e("get mac", "getMacAddr: "+res1.toString() );
                for (byte b : macBytes) {
                    // res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString().replace(":","-");
            }
        } catch (Exception ex) {
            Log.e("TAG", "getMacAddr: ", ex);
        }
        return "";
    }
}