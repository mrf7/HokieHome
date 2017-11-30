package com.softwaredesign.group5.hokiehome;

import android.app.Application;
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

import java.util.Collection;

/**
 * Created by Jordan on 11/27/2017.
 */

public class BeaconApplication extends Application {

    private Manager m;
    private BeaconScanner b;

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
}
