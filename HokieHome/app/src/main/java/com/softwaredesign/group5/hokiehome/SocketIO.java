package com.softwaredesign.group5.hokiehome;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Jordan on 11/22/2017.
 */

public class SocketIO {

    private Activity mainActivity;

    public SocketIO(Activity main)
    {
        mainActivity = main;
    }

    private Socket mSocket;
    {
        try {
            mSocket = com.github.nkzawa.socketio.client.IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    public void connect()
    {
        mSocket.on("Server callback", onNewMessage);
        mSocket.connect();
    }

    /**
     * method used to send a create Object message
     * @param message
     */
    public void createObject(JSONObject message) {
        mSocket.emit("Create", message);
    }

    /**
     * method used to send an account changes message
     * @param message
     */
    public void sendAccountChanges(JSONObject message) {
        mSocket.emit("Changes", message);
    }

    /**
     * method used to send a command message
     * @param message
     */
    public void sendCommands(JSONObject message) {
        mSocket.emit("Command", message);
    }


    /**
     * listens to server callbacks
     */
    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            mainActivity.runOnUiThread(new Runnable() {
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
            });
        }
    };
}
