package com.softwaredesign.group5.hokiehome;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    /**
     * method to send a JSON message to the server to set the light brightnes
     * @param l the light being changed
     * @param brightness new brightness value
     */
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

    /**
     * method used to send a JSON method to the server when entering a new room
     * @param name  name of entered room
     */
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

    /**
     * method used to send a JSON message to add a light to the server
     * @param r room object that the light is contained in
     * @param l light being added
     */
    private void addLight(Room r, Light l)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Light");
            com.put("Room", r.getName());
            com.put("Name", l.getMAC());
            r.addLight(l);
            IO.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to send a JSON message to the server to add a new user
     * @param u new user to be added
     */
    private void addUser(User u)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "User");
            com.put("Name", u.getUsername());
            com.put("Pass", u.getPassword());
            com.put("PreBright", u.getPreBrightness());
            IO.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to send a JSON message to the server to add a room
     * @param r room object to be added
     */
    private void addRoom(Room r)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Room");
            com.put("Name", r.getName());
            IO.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to send a JSON message to the server to add a dash button
     * @param d dash button to be added
     * @param l array of lights that the dash button controls
     */
    private void addDash(DashButton d, ArrayList<Light> l)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Dash");
            com.put("Name", d.getMac());
            com.put("Lights", l);
            IO.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
