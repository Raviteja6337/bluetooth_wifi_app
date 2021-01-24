package com.example.bluetoothwifiindicator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import com.example.bluetoothwifiindicator.ui.FragmentMain;
import com.example.bluetoothwifiindicator.ui.NotificationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;
import static android.app.Notification.PRIORITY_HIGH;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.Theme_AppCompat);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Wifi-Bluetooth Indicator");

        Intent intent = new Intent(getApplicationContext(), FragmentMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
