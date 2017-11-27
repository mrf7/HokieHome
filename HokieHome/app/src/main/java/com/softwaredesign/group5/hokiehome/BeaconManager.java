package com.softwaredesign.group5.hokiehome;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;

/**
 * Created by Jordan on 11/23/2017.
 */

public class BeaconManager{

    private ArrayList<Beacon> beacons = new ArrayList<>();
    private Beacon currentClosest = null;


    public Beacon getCurrentClosest() {
        return currentClosest;
    }

    /**
     * finds the closest beacon out of the list of beacons
     * @return closest beacon
     */
    public Beacon findClosestBeacon()

    {
        double distance = Double.MAX_VALUE;
        Beacon closestBeacon = null;
        for (Beacon b : beacons) {
            Double dis = b.getDistance();
            if (dis < distance);
            {
                closestBeacon = b;
                distance = dis;
            }
        }
        currentClosest = closestBeacon;
        return closestBeacon;
    }



    /**
     * adds a new beacon to the list
     * @param name name of beacon
     * @param mac mac address of beacon
     * @param room room that the beacon is associated with
     * @return true if added. // needs a way to confirm a valid mac address
     */
    public boolean addNewBeacon(String name, String mac, String room)
    {
        Beacon b = new Beacon.Builder().setBluetoothName(name).setBluetoothAddress(mac)
                .setId1(room).build();
        beacons.add(b);
        return true;
    }

}
