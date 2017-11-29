package com.softwaredesign.group5.hokiehome;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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
        app = (BeaconApplication) getApplication();
        manage = app.getManager();
        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(this);
        manage.assignActivity(this);
    }

    public void onClick(View v) {
        manage.enteredRoom("living Room");
    }
}
