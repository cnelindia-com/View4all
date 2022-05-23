package com.example.viewall.broadcastrecivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;
import com.example.viewall.activities.DownloanActivity;
import com.example.viewall.serviceapi.GlobalApplication;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkConnected()){
            /*Toast.makeText(context, "Connected to Internet", Toast.LENGTH_SHORT).show();*/
        } else {
            Toast.makeText(context, "Not connected to Internet", Toast.LENGTH_SHORT).show();
            GlobalApplication.getAppContext().startActivity(new Intent(GlobalApplication.getAppContext(),
                    DownloanActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }


    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) GlobalApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
