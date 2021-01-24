package com.example.bluetoothwifiindicator.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.example.bluetoothwifiindicator.R;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationView extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        textView = findViewById(R.id.wifiNotification);
        //getting the notification message
        String message=getIntent().getStringExtra("message");
        textView.setText(message);
    }
}

