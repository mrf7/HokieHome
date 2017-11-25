package com.softwaredesign.group5.hokiehome;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.altbeacon.beacon.Beacon;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jordan on 11/25/2017.
 * Service that will run in the background to control the beacons and server communication
 */

public class ManagerService extends Service {

    private User currentUser;
    private Room currentRoom;
    private SocketIO IO;
    private BeaconManager b;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IO.connect();
        return null;
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

    private void enteredRoom(String r)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Enter");
            com.put("Object", "Room");
            com.put("Brightness", currentUser.getPreBrightness());
            com.put("Name", r);
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
