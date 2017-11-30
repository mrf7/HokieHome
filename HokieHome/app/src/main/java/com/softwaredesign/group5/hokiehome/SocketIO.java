package com.softwaredesign.group5.hokiehome;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Jordan on 11/22/2017.
 */

public class SocketIO {

    private Activity mainActivity;
    private String Ip_Port = "http://10.0.0.100:10443";
    private JSONObject lastCommand;

    public JSONObject getLastCommand() {
        return lastCommand;
    }

    public SocketIO()

    {

    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Ip_Port);

            Log.d("BeaconReferenceApp", "Success");

        } catch (URISyntaxException e) {

            Log.d("BeaconReferenceApp","Failure");
        }
    }

    public Socket getmSocket() {
        return mSocket;
    }

    public void checkForRoom (Activity a)
    {
        Log.d("debug", "request");
        mainActivity = a;
        mSocket.on("roomsCallback", checkForRoom);
        mSocket.emit("getRooms", "");
    }

    public void checkforLights(Activity a)
    {
        mainActivity = a;
        mSocket.on("newLightsCallback", checkForLights);
        mSocket.emit("getNewLights", "");
    }

    public void connect()
    {
        mSocket.connect();
        Log.d("debug", "connect");
        sendUserInfo();
    }

    private void sendUserInfo() {
    }


    /**
     * method used to send a create Object message
     * @param message
     */
    public void addLight(JSONObject message) {
        mSocket.emit("addLight", message);
        lastCommand = message;
    }

    /**
     * method used to send an account changes message
     * @param message
     */
    public void setBrightness(JSONObject message) {
        mSocket.emit("setBrightness", message);
        lastCommand = message;
    }

    /**
     * method used to send a command message
     * @param message
     */
    public void enteredRoom(JSONObject message) {
        mSocket.emit("enteredRoom", message.toString());
        lastCommand = message;
    }



    /**
     * listens to server callbacks
     */
    private Emitter.Listener checkForRoom = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("BeaconReferenceApp", "Got some data");
                    JSONObject data = null;
                    try {
                        data = new JSONObject((String) args[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayList<Room> rooms = new ArrayList<>();
                    try {
                        Iterator<String> keys = data.keys();
                        while (keys.hasNext())
                        {
                            String currentKey = keys.next();
                            Room cRoom = new Room(currentKey);
                            JSONArray lights = data.getJSONArray(currentKey);
                            for(int i = 0; i < lights.length(); i++)
                            {
                                Light cLight = new Light(((JSONObject) lights.get(i)).getInt("lightId"));
                                cLight.setCurrentBrightness(((JSONObject) lights.get(i)).getInt("brightness"));
                                cRoom.addLight(cLight);
                            }
                            rooms.add(cRoom);
                        }
                        ((MainActivity) mainActivity).passRooms(rooms);
                    } catch (JSONException e) {
                        return;
                    }
                    Log.d("BeaconReferenceApp", rooms.get(0).getName() + rooms.get(0).getLights().get(0).getId());
                }
            });
        }
    };

    /**
     * listens to server callbacks
     */
    private Emitter.Listener checkForLights = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            new Runnable() {
                @Override
                public void run() {
                    Log.d("debug", "recieved info");
                    JSONArray data = (JSONArray) args[0];
                    ArrayList<Light> lights = new ArrayList<>();
                    for(int i = 0; i < data.length(); i++)
                    {
                        try {
                            Light cLight = new Light((Integer) data.get(i));
                            lights.add(cLight);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    // add the message to view
                    ((AddLightActivity) mainActivity).passNewLights(lights);
                }
            };
        }
    };
}
