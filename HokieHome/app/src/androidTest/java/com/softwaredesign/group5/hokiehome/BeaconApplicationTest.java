package com.softwaredesign.group5.hokiehome;


import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;
import android.util.Log;

import junit.framework.TestCase;

import org.altbeacon.beacon.AltBeacon;
import org.altbeacon.beacon.AltBeaconParser;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Jordan on 11/29/2017.
 */

@RunWith(AndroidJUnit4.class)
public class BeaconApplicationTest{

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    @Test
    public void testRecognizeBeacon() {
        BeaconManager.setDebug(true);
        byte[] bytes = hexStringToByteArray("02011a1bff1801beac2f234454cf6d4a0fadf2f4911ba9ffa600010002c50900");
        AltBeaconParser parser = new AltBeaconParser();
        Beacon beacon = parser.fromScanData(bytes, -55, null);
        assertEquals ("Beacon should have one data field", 1, beacon.getDataFields().size());
        assertEquals("manData should be parsed", 9, ((AltBeacon) beacon).getMfgReserved());
    }


    @Test
    public void testParsesBeaconMissingDataField() {
        BeaconManager.setDebug(true);
        byte[] bytes = hexStringToByteArray("02011a1aff1801beac2f234454cf6d4a0fadf2f4911ba9ffa600010002c5000000");
        AltBeaconParser parser = new AltBeaconParser();
        Beacon beacon = parser.fromScanData(bytes, -55, null);
        assertEquals("mRssi should be as passed in", -55, beacon.getRssi());
        assertEquals("uuid should be parsed", "2f234454-cf6d-4a0f-adf2-f4911ba9ffa6", beacon.getIdentifier(0).toString());
        assertEquals("id2 should be parsed", "1", beacon.getIdentifier(1).toString());
        assertEquals("id3 should be parsed", "2", beacon.getIdentifier(2).toString());
        assertEquals("txPower should be parsed", -59, beacon.getTxPower());
        assertEquals("manufacturer should be parsed", 0x118 ,beacon.getManufacturer());
        assertEquals("missing data field zero should be zero", new Long(0l), beacon.getDataFields().get(0));
    }
}