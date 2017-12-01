package com.softwaredesign.group5.hokiehome;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

/**
 * Created by Jordan on 11/29/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SocketIOTest {
    private SocketIO IO;
    private Manager m;
    private User u;

    protected void setUp() {
        u = new User("Jordan", "Pass", 50);
        m = new Manager(u);
        IO = m.getSocket();
    }

    @Test
    public void testSocketURIFormat() throws InterruptedException {
        setUp();
        //if URI is correct, mSocket will not be null
        assertNotNull(IO.getmSocket());
        //if you want to test connect to a server, you need to change the IP_port to your servers
        //ip and port values and uncomment
        //assertTrue(IO.getmSocket().connected());
    }

    @Test
    public void testsendingCommands()
    {
        setUp();
        Light l = new Light(1);
        m.setLightBrightness(l, 50);
        JSONObject sent = IO.getLastCommand();
        try {
            assertEquals(sent.get("lightID"), l.getId());
            assertEquals(sent.get("brightness"), 50);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        m.enteredRoom("Living Room");
        sent = IO.getLastCommand();
        try {
            assertEquals(sent.get("room"), "Living Room");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        m.addLight("Living Room", l);
        sent = IO.getLastCommand();
        try {
            assertEquals(sent.get("room"), "Living Room");
            assertEquals(sent.get("lightId"), l.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        m.leftRoom("Living Room");
        sent = IO.getLastCommand();
        try {
            assertEquals(sent.get("room"), "Living Room");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
