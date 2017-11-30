package com.softwaredesign.group5.hokiehome;

import android.app.Activity;
import android.app.Application;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jordan on 11/23/2017.
 */

public class Manager {

    private final User currentUser;
    private ArrayList<Beacon> beacons = new ArrayList<>();
    private Beacon currentClosest = null;
    private SocketIO socket = new SocketIO();

    public SocketIO getSocket() {
        return socket;
    }

    public Manager (User u)
    {
        currentUser = u;
        socket.connect();
    }

    public Beacon getCurrentClosest() {
        return currentClosest;
    }

    /**
     * method to send a JSON message to the server to set the light brightnes
     *
     * @param l          the light being changed
     * @param brightness new brightness value
     */
    public void setLightBrightness(Light l, int brightness) {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Power");
            com.put("Object", "Light");
            com.put("Brightness", brightness);
            com.put("Name", l.getMAC());
            socket.sendCommands(com);
            l.setCurrentBrightness(brightness);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void checkForLights()
    {

    }

    /**
     * method used to send a JSON method to the server when entering a new room
     *
     * @param name name of entered room
     */
    public void enteredRoom(String name) {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Enter");
            com.put("Object", "Room");
            com.put("Brightness", currentUser.getPreBrightness());
            com.put("Name", name);
            socket.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();

            Log.d("BeaconReferenceApp", "Crash");
        }
    }

    /**
     * method used to send a JSON message to add a light to the server
     *
     * @param r room object that the light is contained in
     * @param l light being added
     */
    public void addLight(String roomName, Light l) {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Light");
            com.put("Room", roomName);
            com.put("Name", l.getMAC());
            socket.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to send a JSON message to the server to add a new user
     *
     * @param u new user to be added
     */
    public void addUser(User u) {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "User");
            com.put("Name", u.getUsername());
            com.put("Pass", u.getPassword());
            com.put("PreBright", u.getPreBrightness());
            socket.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to send a JSON message to the server to add a room
     *
     * @param r room object to be added
     */
    public void addRoom(Room r) {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Room");
            com.put("Name", r.getName());
            socket.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to send a JSON message to the server to add a dash button
     *
     * @param d dash button to be added
     * @param l array of lights that the dash button controls
     */
    public void addDash(DashButton d, ArrayList<Light> l) {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Dash");
            com.put("Name", d.getMac());
            com.put("Lights", l);
            socket.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void assignActivity (Activity a)
    {
        socket.assignActivity(a);
    }

}
