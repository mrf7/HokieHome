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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jordan on 11/27/2017.
 */

public class BeaconApplication extends Application implements BootstrapNotifier, BeaconConsumer, RangeNotifier {
    private static final String TAG = "BeaconReferenceApp";
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;
    private boolean haveDetectedBeaconsSinceBoot = false;
    private BeaconManager beaconManager;
    private Manager m;
    private Beacon currentClosest = null;
    private ArrayList<Light> currentLights;

    public void onCreate() {
        super.onCreate();
        beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.setBackgroundBetweenScanPeriod(5000l);
        beaconManager.setForegroundBetweenScanPeriod(2000l);
        beaconManager.bind(this);

        // By default the AndroidBeaconLibrary will only find AltBeacons.  If you wish to make it
        // find a different type of beacon, you must specify the byte layout for that beacon's
        // advertisement with a line like below.  The example shows how to find a beacon with the
        // same byte layout as AltBeacon but with a beaconTypeCode of 0xaabb.  To find the proper
        // layout expression for other beacon types, do a web search for "setBeaconLayout"
        // including the quotes.
        //
        //beaconManager.getBeaconParsers().clear();
        //beaconManager.getBeaconParsers().add(new BeaconParser().
        //        setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        Log.d(TAG, "setting up background monitoring for beacons and power saving");
        // wake up the app when a beacon is seen
        Region region = new Region("backgroundRegion",
                null, null, null);

        regionBootstrap = new RegionBootstrap(this, region);

        // simply constructing this class and holding a reference to it in your custom Application
        // class will automatically cause the BeaconLibrary to save battery whenever the application
        // is not visible.  This reduces bluetooth power usage by about 60%
        backgroundPowerSaver = new BackgroundPowerSaver(this);

        // If you wish to test beacon detection in the Android Emulator, you can use code like this:
        BeaconManager.setBeaconSimulator(new TimedBeaconSimulator() );
        ((TimedBeaconSimulator) BeaconManager.getBeaconSimulator()).createTimedSimulatedBeacons();
        User u = new User("Jordan", "Pass", 25);
        m = new Manager(u);
    }



    @Override
    public void didEnterRegion(Region arg0) {
        Log.d(TAG, "did enter region.");
        try {
            beaconManager.startRangingBeaconsInRegion(arg0);
        }
        catch (RemoteException e) {
            if (BuildConfig.DEBUG) Log.d(TAG, "Can't start ranging");
        }
    }

    @Override
    public void didExitRegion(Region region) {
        try {
            beaconManager.stopRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        Log.d(TAG,"I have just switched from seeing/not seeing beacons: " + state);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

        Log.d(TAG,"Scanned");
        if (beacons.size() > 0) {
            Double distance = Double.MAX_VALUE;
            Beacon closestBeacon = currentClosest;
            for (Beacon b : beacons) {
                Double dis = b.getDistance();
                if (dis < distance) ;
                {
                    closestBeacon = b;
                    distance = dis;
                }
            }
            if (currentClosest == null || !closestBeacon.getBluetoothName().equals(currentClosest.getBluetoothName()))
            {
                Log.d(TAG,closestBeacon.getDistance() + closestBeacon.getBluetoothName());
                m.enteredRoom(closestBeacon.getBluetoothName());
                currentClosest = closestBeacon;
            }
        }

    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(this);
    }

    public Manager getManager() {
        return m;
    }

    public void setCurrentLights(ArrayList<Light> light){
        this.currentLights=light;
    }
    public ArrayList<Light> getCurrentLights(){
        return this.currentLights;
    }
}
