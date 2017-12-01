package data;

import java.util.ArrayList;
import java.util.HashMap;

import controllers.LightController;
import controllers.LightController.LightListener;
import controllers.MobileController;
import controllers.MobileController.MobileListener;

public class Server implements LightListener, MobileListener {
	private static Server instance = null;
	private static ArrayList<Light> newLights;
	private static HashMap<String, Room> rooms;
	private static HashMap<String, User> users;

	// Create the server instance to store controllers and listeners
	private Server() {
		LightController lightController = LightController.getInstance();
		MobileController mobileController = MobileController.getInstance();
		rooms = new HashMap<String, Room>();
		newLights = new ArrayList<Light>();
		users = new HashMap<String, User>();
		lightController.registerListener(this);
		mobileController.registerListener(this);
	}

	// Get the instance of the Server, creating if necessary.
	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	// Getters
	public static HashMap<String, Room> getRooms() {
		return rooms;
	}

	public static ArrayList<Light> getNewLights() {
		return newLights;
	}

	// LightController callback
	/**
	 * Occurs when light identifies it self
	 * 
	 * @param newLight
	 *            the light object representing the new light.
	 * @param room
	 *            the room the light is in. if room is null the light has not been
	 *            set up yet
	 */
	@Override
	public void onLightConnected(Light newLight, String room) {
		if (room != null) {
			// Create the room or add the light to an existing room
			if (!rooms.containsKey(room)) {
				Room newRoom = new Room(room);
				newRoom.addLight(newLight);
				rooms.put(room, newRoom);
			} else {
				rooms.get(room);
				
			}
		} else { // Add light to list of not set up lights
			newLights.add(newLight);
		}
		//Flash the light to indicate connection
		newLight.turnOn();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		newLight.turnOff();
	}

	// User controller callbacks
	/**
	 * Occurs when user identifies itself
	 * 
	 * @User the user
	 */
	@Override
	public void onUserConnected(User user) {
		// This will replace an old instance if username already exists and create a new
		// entry otherwise
		users.put(user.getUsername(), user);
	}

	@Override
	public void onUserEnteredRoom(String userName, String roomName) {
		// Get the room object 
		Room room = rooms.get(roomName);
		if (room == null) {
			//If room is null, the room with roomName has no connected lights. Add the room to the rooms list to add lights
			room = new Room(roomName);
			rooms.put(roomName, room);			
		}
	}

	@Override
	public void onRoomExit(String room) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSetBrightness(int lightID, int brightness) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddLight(int lightID, String room) {
		// TODO Auto-generated method stub

	}
}
