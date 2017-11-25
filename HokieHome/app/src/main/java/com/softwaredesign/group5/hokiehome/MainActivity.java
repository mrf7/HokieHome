package com.softwaredesign.group5.hokiehome;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private User currentUser;
    private Room currentRoom;
    private SocketIO IO;
    private BeaconManager b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IO = new SocketIO(this);
        IO.connect();
        b = new BeaconManager();
    }


    public boolean checkEnter()
    {
        Beacon closestB = b.findClosestBeacon();
        if (closestB != b.getCurrentClosest())
        {
            enteredRoom(String.valueOf(closestB.getId1()));
            return true;
        }
        return false;
    }

    private void setLightBrightness(Light l, int brightness)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Power");
            com.put("Object", "Light");
            com.put("Brightness", brightness);
            com.put("Name", l.getMAC());
            IO.sendCommands(com);
            l.setCurrentBrightness(brightness);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void enteredRoom(String name)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Enter");
            com.put("Object", "Room");
            com.put("Brightness", currentUser.getPreBrightness());
            com.put("Name", name);
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLight(Room r, Light l)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Light");
            com.put("Room", r.getName());
            com.put("Name", l.getMAC());
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addRoom(Room r)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Room");
            com.put("Name", r.getName());
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addDash(DashButton d, Light[] l)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Dash");
            com.put("Name", d.getMac());
            com.put("Lights", l);
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
