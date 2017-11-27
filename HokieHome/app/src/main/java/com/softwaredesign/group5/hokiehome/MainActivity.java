package com.softwaredesign.group5.hokiehome;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.nkzawa.socketio.client.IO;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private User currentUser;
    private Room currentRoom;
    private ManagerService Managerservice;
    private BeaconManager manager;
    boolean isBond = false;

    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ManagerService.MyBinder binder = (ManagerService.MyBinder) service;
            Managerservice = binder.getService();
            Managerservice.setManager(manager);
            isBond = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            manager = null;
            isBond = false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new BeaconManager();
    }
}
