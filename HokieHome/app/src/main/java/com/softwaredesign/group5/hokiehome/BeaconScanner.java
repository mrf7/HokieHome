package com.softwaredesign.group5.hokiehome;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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

import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * Created by Jordan on 11/30/2017.
 */

public class BeaconScanner implements BootstrapNotifier, BeaconConsumer, RangeNotifier{

    private static final String TAG = "BeaconReferenceApp";
    private RegionBootstrap regionBootstrap;
    private boolean haveDetectedBeaconsSinceBoot = false;
    private BeaconManager beaconManager;
    private Context appC;
    private Manager m;


    public Beacon getCurrentClosest() {
        return currentClosest;
    }

    private Beacon currentClosest = null;
    private Region region;

    public BeaconManager getBeaconManager() {
        return beaconManager;
    }

    public BeaconScanner( Context c, Manager manager) {
        appC = c;
        beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(getApplicationContext());
        beaconManager.setBackgroundBetweenScanPeriod(1000l);
        beaconManager.setForegroundBetweenScanPeriod(500l);
        m = manager;
        beaconManager.bind(this);

        // wake up the app when a beacon is seen
        region = new Region("backgroundRegion",
                null, null, null);

        regionBootstrap = new RegionBootstrap(this, region);

        //Uncomment the below methods for testing simulated beacons
        //It should list the beacon names as they are found in Logcat
        //The beacons should be in the order LivingRoom, Kitchen, Bathroom, DiningRoom
        //The TAG "BeaconReferenceApp" can be used in Logcat to filter the results.
        //BeaconManager.setBeaconSimulator(new TimedBeaconSimulator() );
        //((TimedBeaconSimulator) BeaconManager.getBeaconSimulator()).createTimedSimulatedBeacons();
    }




    @Override
    public void didEnterRegion(Region arg0) {
        //Log.d(TAG, "did enter region.");
    }


    @Override
    public void didExitRegion(Region region) {
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {
        //Log.d(TAG,"I have just switched from seeing/not seeing beacons: " + state);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        if (beacons.size() > 0) {
            Double distance = Double.MAX_VALUE;
            Beacon closestBeacon = currentClosest;
            String str = "";
            for (Beacon b : beacons) {
                Double dis = b.getDistance();
                if (dis < distance)
                {
                    byte[] bytearray = b.getId1().toByteArray();
                    try {
                        str = new String(bytearray, "UTF-8");
                        str = str.trim();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    closestBeacon = b;
                    distance = dis;
                }
            }
            if (currentClosest == null || !closestBeacon.getId1().toString().equals(currentClosest.getId1().toString())) {
                Log.d(TAG,"Closet Beacon " + str + "\n" );
                if (currentClosest != null)
                {
                    try {
                        m.leftRoom(new String(currentClosest.getId1().toByteArray(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                currentClosest = closestBeacon;
                m.enteredRoom(str);

            }
        }

    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(this);

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("Name", null,null,null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }


    public void unbind() {
        beaconManager.unbind(this);
    }

    @Override
    public Context getApplicationContext() {
        return appC;
    }
}
