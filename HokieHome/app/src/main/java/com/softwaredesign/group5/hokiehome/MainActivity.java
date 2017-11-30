package com.softwaredesign.group5.hokiehome;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private User currentUser;
    private Room currentRoom;

    Intent startManagerServiceIntent;

    boolean isInitalized = false;
    BeaconApplication app;
    Manager manage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//Service Creation Methods
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app = (BeaconApplication) getApplication();
            app.getB().unbind();
    }
}
