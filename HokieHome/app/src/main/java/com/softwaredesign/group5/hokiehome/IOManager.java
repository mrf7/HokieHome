package com.softwaredesign.group5.hokiehome;
import android.app.Activity;
import android.os.Bundle;
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

public class IOManager {

    private Activity mainActivity;

    public IOManager(Activity main)
    {
        mainActivity = main;
    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    public void connect()
    {
        mSocket.on("Server callback", onNewMessage);
        mSocket.connect();
    }

    public void createAccount(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mSocket.emit("Create", message);
    }

    public void sendAccountChanges(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mSocket.emit("Changes", message);
    }

    public void sendCommands(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mSocket.emit("Command", message);
    }

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
