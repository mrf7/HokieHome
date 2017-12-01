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
        socket.connect(u);
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
            com.put("lightId", l.getId());
            com.put("brightness", brightness);
            socket.setBrightness(com);
            l.setCurrentBrightness(brightness);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void checkForLights(Activity a)
    {
        socket.checkforLights(a);
    }

    public void checkForRooms(Activity a)
    {
        socket.checkForRoom(a);
    }

    /**
     * method used to send a JSON method to the server when entering a new room
     *
     * @param name name of entered room
     */
    public void enteredRoom(String name) {
        JSONObject com = new JSONObject();
        try {
            com.put("room", name);
            com.put("user", currentUser.getUsername());
            socket.enteredRoom(com);
        } catch (JSONException e) {
            e.printStackTrace();

            Log.d("BeaconReferenceApp", "Crash");
        }
    }

    /**
     * method used to send a JSON message to add a light to the server
     *
     * @param roomName room object that the light is contained in
     * @param l light being added
     */
    public void addLight(String roomName, Light l) {
        JSONObject com = new JSONObject();
        try {
            com.put("lightId", l.getId());
            com.put("room", roomName);
            socket.addLight(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void leftRoom(String s) {
        socket.exitedRoom(s);
    }
}
