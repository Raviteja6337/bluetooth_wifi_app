package com.example.bluetoothwifiindicator.fragments;

import android.bluetooth.BluetoothAdapter;
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

import com.example.bluetoothwifiindicator.R;
import com.example.bluetoothwifiindicator.ui.FragmentMain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

public class BluetoothFragment extends Fragment {

    private static final String TAG = "BluetoothFragment Class";

    private View viewBluetooth;
    private BluetoothAdapter mBluetoothAdapter;
    private TextView bluetoothTxtView;
    private Switch btnToggle;
    NotificationCompat.Builder mbuilderr;
    FragmentMain fragmentMainParent;

    public BluetoothFragment(FragmentMain parent, NotificationCompat.Builder mBuilder) {
        this.mbuilderr = mBuilder;
        fragmentMainParent = parent;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewBluetooth = inflater.inflate(R.layout.frag_bluetooth_layout, container, false);

        bluetoothTxtView = (TextView) viewBluetooth.findViewById(R.id.txtBluetooth);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (isBluetoothEnabled())
            bluetoothTxtView.setText(getResources().getString(R.string.bluetooth_enabled));
        else
            bluetoothTxtView.setText(getResources().getString(R.string.bluetooth_disabled));



        //Toggle button onClick to Enable/Disable Bluetooth from the Application
        btnToggle = (Switch) viewBluetooth.findViewById(R.id.switchBluetooth);
        btnToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on)
                {
                    Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.switching_on_bluetooth),Toast.LENGTH_SHORT).show();
                    mBluetoothAdapter.enable();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),getResources().getString(R.string.switching_off_bluetooth),Toast.LENGTH_SHORT).show();
                    mBluetoothAdapter.disable();
                }
            }
        });




        // Register for broadcasts on BluetoothAdapter state change
        try {
            IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            getActivity().registerReceiver(mReceiver, filter);
        }
        catch (Exception e)
        {
            Log.i(TAG, "Error in bluetooth Adapter" +e.getMessage());
        }

        return viewBluetooth;
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        bluetoothTxtView.setText(getResources().getString(R.string.bluetooth_disabled));
                        btnToggle.setChecked(false);
                        fragmentMainParent.mBuilder.setContentText("Bluetooth: disabled"); //changing the notification content text to disabled
                        break;
                    case BluetoothAdapter.STATE_ON:
                        bluetoothTxtView.setText(getResources().getString(R.string.bluetooth_enabled));
                        fragmentMainParent.mBuilder.setContentText("Bluetooth: enabled"); //changing the notification content text to enabled
                        btnToggle.setChecked(true);
                        break;
                }
            }
        }
    };

    private boolean isBluetoothEnabled()
    {
        return mBluetoothAdapter.isEnabled();
    }

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
            Log.i(TAG, "Error in bluetooth Adapter onDestroy()" +e.getMessage());
        }
    }
}
