package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import data.Light;
import data.Room;
import data.Server;
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
		listeners = new ArrayList<MobileListener>();
		userMap = new HashMap<String, SocketIOClient>();
		server = SocketManager.getInstance();
		server.addEventListener("userIdent", String.class, identListener);
		server.addEventListener("enteredRoom", String.class, enteredRoomListener);
		server.addEventListener("exitedRoom", String.class, exitedRoomListener);
		server.addEventListener("setBrightness", String.class, setBrightnessListener);
		server.addEventListener("getRooms", String.class, getRoomsListener);
		server.addEventListener("getNewLights", String.class, getNewLightsListener);
		server.addEventListener("addLight", String.class, addLightListener);
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

	// Listeners
	// Listener for user identification event. Creates a new User object from the
	// data
	private DataListener<String> identListener = new DataListener<String>() {
		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) {
			// Create a JSON object from the string received
			JSONObject jsonData;
			String userName;
			int prefBrightness;
			
			try {
				jsonData = new JSONObject(data);
				userName = jsonData.getString("name");
				prefBrightness = jsonData.getInt("prefBrightness");
			} catch (JSONException e) {
				System.out.println("Bad data recieved in userIdent:" + data);
				return;
			}
			User user = new User(userName, prefBrightness);// Notify listener, if it exists
			for (MobileListener listener : listeners) {
				listener.onUserConnected(user);
			}
			userMap.put(userName, client);
		}
	};
	// Receives the entered room event and notifies the all listeners
	private DataListener<String> enteredRoomListener = new DataListener<String>() {
		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) {
			JSONObject jsonData;
			String room;
			String userName;
			try {
				jsonData = new JSONObject(data);
				room = jsonData.getString("room");
				userName = jsonData.getString("user");
			} catch (JSONException e) {
				System.out.println("Bad data recieved in enteredRoom:" + data);
				return;
			}
			System.out.println(userName + " entered " + room);
			for (MobileListener listener : listeners) {
				listener.onUserEnteredRoom(userName, room);
			}
		}
	};
	// Receives exited room event and notifes all listeners
	private DataListener<String> exitedRoomListener = new DataListener<String>() {

		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
			if (data == null) {
				System.out.println("No data received in exitedRoom");
			}
			for (MobileListener listener : listeners) {
				listener.onRoomExit(data);
			}
		}

	};
	// Receives the setBrightness event and notifies listeners
	private DataListener<String> setBrightnessListener = new DataListener<String>() {

		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
			JSONObject jsonData;
			int lightID;
			int lightBrightness;
			try {
				jsonData = new JSONObject(data);
				lightID = jsonData.getInt("id");
				lightBrightness = jsonData.getInt("brightness");
			} catch (JSONException e) {
				System.out.println("Bad data received from setBrightness" + data);
				return;
			}
			for (MobileListener listener : listeners) {
				listener.onSetBrightness(lightID, lightBrightness);
			}
		}

	};
	// Returns the dictionary of rooms on the getRooms event
	private DataListener<String> getRoomsListener = new DataListener<String>() {
		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) {
			HashMap<String, Room> rooms = Server.getRooms();
			JSONObject roomsJSON = new JSONObject();
			try {
				for (Room room : rooms.values()) {
					JSONArray lights = new JSONArray();
					for (Light light : room.getLights()) {
						lights.put(light.toJson());
					}
					roomsJSON.put(room.getRoomName(), lights);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}
			System.out.println("Rooms callback: " +roomsJSON.toString());
			client.sendEvent("roomsCallback", roomsJSON.toString());
		}

	};
	// Receives the getNewLights event and notifies listeners
	private DataListener<String> getNewLightsListener = new DataListener<String>() {

		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
			ArrayList<Light> newLights = Server.getNewLights();
			JSONArray jsonArray = new JSONArray();
			for (Light light : newLights) {
				jsonArray.put(light.getId());
			}
			client.sendEvent("newLightsCallback", jsonArray.toString());
		}

	};
	// Recieves the addLight event and notifies listeners
	private DataListener<String> addLightListener = new DataListener<String>() {

		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
			JSONObject jsonData;
			int lightID;
			String room;
			try {
				jsonData = new JSONObject(data);
				lightID = jsonData.getInt("id");
				room = jsonData.getString("room");
			} catch (JSONException e) {
				System.out.println("Bad data received from addLight" + data);
				return;
			}
			for (MobileListener listener : listeners) {
				listener.onAddLight(lightID, room);
			}
		}

	};

	// Object to receive User related callback events
	public interface MobileListener {
		public void onUserConnected(User user);

		public void onUserEnteredRoom(String userName, String roomName);

		public void onRoomExit(String room);

		public void onSetBrightness(int lightID, int brightness);

		public void onAddLight(int lightID, String room);
	}
}
