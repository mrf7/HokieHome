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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.net.URISyntaxException;

/**
 * Created by Jordan on 11/22/2017.
 */

public class SocketIO {

    private Activity mainActivity;
    private String Ip_Port = "http://10.0.0.100:9092";
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
        mainActivity = a;
        mSocket.emit("getRooms");
        mSocket.on("Room", checkForRoom);
        mSocket.off("Lights");
    }

    public void checkforLights(Activity a)
    {
        mainActivity = a;
        mSocket.emit("getNewLights");
        mSocket.on("Lights", checkForLights);
        mSocket.off("Room");
    }

    public void connect()
    {
        mSocket.connect();
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
    public void enteredRoom(JSONObject message) throws JSONException {
        mSocket.emit("enteredRoom", message.toString());
        Log.d("BeaconReferenceApp", String.valueOf(message.get("Name")));
        lastCommand = message;
    }



    /**
     * listens to server callbacks
     */
    private Emitter.Listener checkForRoom = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    //addMessage(username, message);
                }
            };
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
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    //addMessage(username, message);
                }
            };
        }
    };
}
