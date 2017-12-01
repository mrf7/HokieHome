package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

import data.Light;
import data.Room;
import socketIO.SocketManager;

public class LightController {
	private static LightController instance = null;

	private ArrayList<LightListener> listeners;
	private final SocketIOServer server;
	private HashMap<Integer, SocketIOClient> lightMap;

	/**
	 * Creates a new LightController and sets up the SocketIO server to receive
	 * light events
	 */
	private LightController() {
		server = SocketManager.getInstance();
		server.addEventListener("lightIdent", String.class, identListener);
		server.addConnectListener(connectListener);
		lightMap = new HashMap<Integer, SocketIOClient>();
		listeners = new ArrayList<LightListener>();
	}

	// Get the instance of the SocketManager, creating if necessary.
	public static LightController getInstance() {
		if (instance == null) {
			instance = new LightController();
		}
		return instance;
	}

	/**
	 * Adds a listener for this methods callbacks
	 * 
	 * @param listener
	 *            the new listener
	 */
	public void registerListener(LightListener listener) {
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
	public void unregisterListener(LightListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Changes the brightness of the given light
	 * 
	 * @param light
	 *            the light to change
	 * @param bright
	 *            the brightness to set. Must be between 0 and 100
	 * @return true if the event was sent
	 */
	public boolean setBrightness(Light light, int bright) {
		SocketIOClient lightClient = lightMap.get(light.getId());
		// Make sure the light id is valid and the brightness is in acceptable range
		if (lightClient == null || bright > 100 || bright < 0) {
			return false;
		}
		lightClient.sendEvent("changeBrightness", bright);
		return true;
	}

	/**
	 * Sets the room of the light. Room.name should not be null.
	 * 
	 * @param light
	 *            the id
	 * @param room
	 *            the room for the light to store
	 * @return true if the event was sent
	 */
	public boolean setRoom(Light light, Room room) {
		SocketIOClient lightClient = lightMap.get(light.getId());
		// Make sure the light id is valid and the room name is not null
		if (lightClient == null || room.getRoomName() == null) {
			return false;
		}
		lightClient.sendEvent("setRoom", room.getRoomName());
		return true;
	}


	// Listeners
	private ConnectListener connectListener = new ConnectListener() {

		@Override
		public void onConnect(SocketIOClient client) {
			System.out.println(client.getHandshakeData().getAddress().getHostName());
		}

	};
	private DataListener<String> identListener = new DataListener<String>() {
		@Override
		public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
			// Create a JSON object from the string received
			JSONObject jsonData = new JSONObject(data);
			// Get the light attributes from the data
			int id = jsonData.getInt("id");
			String room = jsonData.getString("room");
			// If light is not set up, give listener null for the room
			room = room.equals("!NOROOM") ? null : room;
			Light newLight = new Light(id);
			// Notify listener, if it exists
			for (LightListener listener : listeners) {
				listener.onLightConnected(newLight, room);
			}

			// Add the light to the hashmap to control it based on the id
			lightMap.put(id, client);
		}

	};

	public interface LightListener {
		public void onLightConnected(Light newLight, String room);
	}
}
