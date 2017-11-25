package com.softwaredesign.group5.hokiehome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private User currentUser;
    private Room currentRoom;
    private IOManager IO;
    private BeaconManager b;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IO = new IOManager(this);
        IO.connect();
    }


    private void setLightBrightness(Light l, int brightness)
    {
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

    private void enteredRoom(Room r)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Enter");
            com.put("Object", "Room");
            com.put("Brightness", currentUser.getPreBrightness());
            com.put("Name", r.getName());
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLight(Room r, Light l)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Light");
            com.put("Room", r.getName());
            com.put("Name", l.getMAC());
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addRoom(Room r)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Room");
            com.put("Name", r.getName());
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addDash(DashButton d, Light[] l)
    {
        JSONObject com = new JSONObject();
        try {
            com.put("Action", "Add");
            com.put("Object", "Dash");
            com.put("Name", d.getMac());
            com.put("Lights", l);
            IO.sendCommands(com);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
