package com.example.bluetoothwifiindicator.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.bluetoothwifiindicator.R;
import com.example.bluetoothwifiindicator.ui.FragmentMain;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

public class WifiFragment extends Fragment {

    private static final String TAG = "WiFiFragment Class";

    private TextView textView;
    private View viewWifi;
    private WifiManager wifiAdapter;
    private NotificationCompat.Builder mbuilderr;
    FragmentMain fragmentMain;

    public WifiFragment(FragmentMain parent, NotificationCompat.Builder mBuilder) {
        this.mbuilderr = mBuilder;
        fragmentMain = parent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        viewWifi = inflater.inflate(R.layout.frag_wifi_layout, parent, false);

        textView = (TextView) viewWifi.findViewById(R.id.textViewWifi);

        if (isWiFiEnabled())
            textView.setText(getResources().getString(R.string.wifi_enabled));
        else
            textView.setText(getResources().getString(R.string.wifi_disabled));


        //Toggle button onClick to Enable/Disable WiFi from the Application
        Switch btnWiFi = (Switch) viewWifi.findViewById(R.id.switchWifi);
        btnWiFi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on)
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Switching On Wifi",Toast.LENGTH_SHORT).show();

                    wifiAdapter = (WifiManager) getActivity().getApplicationContext().getSystemService(getContext().WIFI_SERVICE);
                    wifiAdapter.setWifiEnabled(true);
                }
                else
                {
                    wifiAdapter = (WifiManager) getActivity().getApplicationContext().getSystemService(getContext().WIFI_SERVICE);
                    wifiAdapter.setWifiEnabled(false);
                    Toast.makeText(getActivity().getApplicationContext(),"Switching Off Wifi",Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Register for broadcasts on Wifi state change
        try {
            IntentFilter filter = new IntentFilter(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
            getActivity().registerReceiver(mReceiver, filter);
        }
        catch (Exception e)
        {
            Log.i(TAG, "Error in bluetooth Adapter" +e.getMessage());
        }

        return viewWifi;
    }

    private boolean isWiFiEnabled()
    {
        try {
            wifiAdapter = (WifiManager) getActivity().getApplicationContext().getSystemService(getContext().WIFI_SERVICE);
            return wifiAdapter.isWifiEnabled();
        }
        catch (Exception e)
        {
            Log.i(TAG,"error in Wifi check"+e.getMessage());
        }

        return false;
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

                if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
                    if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
                        textView.setText(getResources().getString(R.string.wifi_enabled));
                        fragmentMain.mBuilder.setSubText("WiFi: disabled");
                    }
                    else
                    {
                        textView.setText(getResources().getString(R.string.wifi_disabled));
                        fragmentMain.mBuilder.setSubText("WiFi: disabled");
                    }
                }
                else {
                    textView.setText(getResources().getString(R.string.wifi_disabled));
                    fragmentMain.mBuilder.setSubText("WiFi: enabled");
                }
            }

    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /* ... */

        // Unregister broadcast listeners
        try {
            getActivity().unregisterReceiver(mReceiver);
        }
        catch (Exception e)
        {
            Log.i(TAG, "Error in WiFi Adapter onDestroy()" +e.getMessage());
        }
    }

}
