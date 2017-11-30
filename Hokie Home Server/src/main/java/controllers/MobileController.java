package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;


import data.User;
import socketIO.SocketManager;

public class MobileController {
	private static MobileController instance = null;
	
	private ArrayList<MobileListener> listeners; 
	private HashMap<String, SocketIOClient> userMap;
	private final SocketIOServer server; 
	
	/**
	 * Creates a new MobileController and sets up the SocketIO server to receive
	 * mobile events
	 */
	private MobileController() {
		server = SocketManager.getInstance();
		server.addEventListener("userIdent", String.class, identListener);
		server.addEventListener("getRooms", String.class, getRoomsListener);
	}
	// Get the instance of the SocketManager, creating if necessary.
	public static MobileController getInstance() {
		if (instance == null) {
			instance = new MobileController();
		}
		return instance;
	}
	/**
	 * Adds a listener for this methods callbacks
	 * 
	 * @param listener
	 *            the new listener
	 */
	public void registerListener(MobileListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes a listener
	 * 
	 * @param listener
	 *            the listener to remove
	 */
	public void unregisterListener(MobileListener listener) {
		listeners.remove(listener);
	}

	//Listeners
	private DataListener<String> identListener = new DataListener<String>() {
		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
			// Create a JSON object from the string received
			JSONObject jsonData = new JSONObject(data);
			String userName = jsonData.getString("name");
			int prefBrightness = jsonData.getInt("prefBrightness");
			User user = new User(userName, prefBrightness);// Notify listener, if it exists
			for (MobileListener listener : listeners) {
				listener.onUserConnected(user);
			}
			userMap.put(userName, client);
		}
	};
	private DataListener<String> getRoomsListener = new DataListener<String>() {
		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
			String sample = "{'bedroom': [{'lightId': 1, 'brightness': 32}, {'lightId': 2, 'brightness': 24}]}";
			System.out.println("Received request");
			client.sendEvent("roomsCallback", sample);
		}
		
	};
	// Object to receive User related callback events
	public interface MobileListener {
		public void onUserConnected(User user);
	}


}
