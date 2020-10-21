package com.github.zbzalex.smsman.ui;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.github.zbzalex.smsman.R;
import com.github.zbzalex.smsman.broadcast.SmsReceiver;

public class MainActivity extends AppCompatActivity
{
    private static final int REQUETS_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && (
                checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_DENIED
                        || checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
//                        || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
//                        || checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
//                        || checkSelfPermission(Manifest.permission.CHANGE_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
//                        || checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
//                        || checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
        )) {
            checkPermissions();
        } else {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
            registerReceiver(new SmsReceiver(), intentFilter);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermissions()
    {
        requestPermissions(new String[] {
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
//                Manifest.permission.INTERNET,
//                Manifest.permission.ACCESS_NETWORK_STATE,
//                Manifest.permission.CHANGE_NETWORK_STATE,
//                Manifest.permission.ACCESS_WIFI_STATE,
//                Manifest.permission.CHANGE_WIFI_STATE
        }, REQUETS_MULTIPLE_PERMISSIONS);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        int granted = 0;
        for(int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) granted++;
        }

        if (granted < permissions.length) {
            checkPermissions();
        }

        startActivity(getIntent());
    }
}