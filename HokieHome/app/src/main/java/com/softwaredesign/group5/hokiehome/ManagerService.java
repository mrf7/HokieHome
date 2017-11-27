package com.softwaredesign.group5.hokiehome;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.github.nkzawa.socketio.client.IO;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jordan on 11/25/2017.
 * Service that will run in the background to control the beacons and server communication
 */

public class ManagerService extends Service {

    private User currentUser;
    private Room currentRoom;
    private SocketIO IO;

    private final IBinder iBinder = new MyBinder();
    BeaconManager manager;
    private PositionAsyncTask spinAsyncTask;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        spinAsyncTask = new PositionAsyncTask();
        spinAsyncTask.execute();
    }

    public void setManager(BeaconManager manager) {
        this.manager = manager;
    }

    public void createIO(Activity m) {
        IO = new SocketIO(m);
        IO.connect();
    }

    public boolean checkEnter() {
        Beacon closestB = manager.findClosestBeacon();
        if (closestB != manager.getCurrentClosest()) {
            enteredRoom(String.valueOf(closestB.getId1()));
            return true;
        }
        return false;
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
            IO.sendCommands(com);
            l.setCurrentBrightness(brightness);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        if (spinAsyncTask != null && spinAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            spinAsyncTask.cancel(true);
            spinAsyncTask = null;
        }
        super.onDestroy();
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
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * method used to send a JSON message to add a light to the server
     *
     * @param r room object that the light is contained in
     * @param l light being added
     */
    public void addLight(Room r, Light l) {
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
            IO.createObject(com);
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
            IO.createObject(com);
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
            IO.createObject(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class MyBinder extends Binder {
        ManagerService getService() {
            return ManagerService.this;
        }
    }


    private class PositionAsyncTask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            int interval = 1000;
            while (!isCancelled()) {
                checkEnter();
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}
