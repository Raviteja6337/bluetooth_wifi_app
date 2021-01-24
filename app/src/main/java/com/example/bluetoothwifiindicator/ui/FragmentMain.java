package com.example.bluetoothwifiindicator.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.bluetoothwifiindicator.R;
import com.example.bluetoothwifiindicator.fragments.BluetoothFragment;
import com.example.bluetoothwifiindicator.fragments.WifiFragment;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class FragmentMain extends FragmentActivity {

    NotificationManager notificationManager;
    public NotificationCompat.Builder mBuilder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setting theme
        setTheme(R.style.Theme_AppCompat_Light);
//    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_frag);

        addNotification();

        try {

            BluetoothFragment bluetoothFragment = new BluetoothFragment(this, mBuilder);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.bluetoothFragment, bluetoothFragment, bluetoothFragment.getTag()).commit();


            WifiFragment wifiFragment = new WifiFragment(this, mBuilder);
            manager.beginTransaction().replace(R.id.wifiFragment, wifiFragment, wifiFragment.getTag()).commit();
        }
        catch (Exception e)
        {
            Log.i("error in on Create", e.getMessage());
        }

    }


    private void addNotification() {
        mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.bluetooth_icon) //set icon for notification
                        .setContentTitle("Bluetooth Wifi Indicator") //set title of notification
                        .setContentText("This is a notification message")//this is notification message
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)//set priority of notification
                        .setOngoing(true);


        Intent notificationIntent = new Intent(this, FragmentMain.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());
    }



    @Override
        protected void onResume() {
        super.onResume();
    }
}
