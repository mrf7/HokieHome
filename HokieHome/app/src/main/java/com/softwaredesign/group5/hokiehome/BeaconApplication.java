package com.softwaredesign.group5.hokiehome;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jordan on 11/27/2017.
 */

public class BeaconApplication extends Application {

    private Manager m;
    private BeaconScanner b;
    private ArrayList<Light> currentLights;
    private ArrayList<Room> rooms;
    public BeaconScanner getB() {
        return b;
    }

    public void onCreate() {
        super.onCreate();
        User u = new User("Jordan", "Pass", 25);
        m = new Manager(u);
        b = new BeaconScanner(this, m);
    }


    public Manager getManager() {
        return m;
    }

    public void setCurrentLights(ArrayList<Light>currentLights){
        this.currentLights=currentLights;
    }

    public ArrayList<Light> getCurrentLights(){
        return this.currentLights;
    }

    public void setRooms(ArrayList<Room>rooms){
        this.rooms=rooms;
    }
    public ArrayList<Room>getRooms(){
        return this.rooms;
    }
}
