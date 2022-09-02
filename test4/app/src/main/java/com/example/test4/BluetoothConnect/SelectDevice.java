package com.example.test4.BluetoothConnect;


import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test4.Adapters.DeviceListAdapter;
import com.example.test4.MainActivity;
import com.example.test4.Models.DeviceInfoModel;
import com.example.test4.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectDevice extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 77;
    private static String[] BTPERMISSIONS = {
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.BLUETOOTH_CONNECT,
            android.Manifest.permission.BLUETOOTH_SCAN
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_select_device);

        checkGrantPerms();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(SelectDevice.this, new String[]{
                        Manifest.permission.BLUETOOTH_CONNECT
                }, 3);
                return;
            }
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        List<Object> deviceList = new ArrayList<>();
        if (pairedDevices.size() > 0) {

            for (BluetoothDevice device : pairedDevices) {
                if (ActivityCompat.checkSelfPermission(SelectDevice.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        ActivityCompat.requestPermissions(SelectDevice.this, new String[]{
                                Manifest.permission.BLUETOOTH_SCAN
                        }, 3);
                        return;
                    }
                }

                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();//MAC address
                DeviceInfoModel deviceInfoModel = new DeviceInfoModel(deviceName, deviceHardwareAddress);
                deviceList.add(deviceInfoModel);

            }
            RecyclerView recyclerView = findViewById(R.id.recyclerViewDevice);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            DeviceListAdapter deviceListAdapter = new DeviceListAdapter(this, deviceList);
            recyclerView.setAdapter(deviceListAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        } else {
            View view = findViewById(R.id.recyclerViewDevice);
            Snackbar snackbar = Snackbar.make(view, "Activate Or Pair Bluetooth Device", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent xer1 = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                    startActivityForResult(xer1, 1);
                }
            });
            snackbar.show();
        }


    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permisssion : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permisssion) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkGrantPerms() {
        if (!hasPermissions(this, BTPERMISSIONS)) {
            ActivityCompat.requestPermissions(this, BTPERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }
}