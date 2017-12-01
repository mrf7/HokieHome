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
	
	private LightController lightController;
	private MobileController mobileController;
	// Create the server instance to store controllers and listeners
	private Server() {
		lightController = LightController.getInstance();
		mobileController = MobileController.getInstance();
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
		// Flash the light to indicate connection
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
		System.out.println("User connected: " + user);
	}

	@Override
	public void onUserEnteredRoom(String userName, String roomName) {
		// Get the room object
		Room room = rooms.get(roomName);
		if (room == null) {
			// If room is null, the room with roomName has no connected lights. Add the room
			// to the rooms list so users can see the room and add lights
			room = new Room(roomName);
			rooms.put(roomName, room);
			return;
		}
		// Get the users preferences
		User user = users.get(userName);
		if (user == null) {
			// If no user exists, create a new user with default brightness
			user = new User(userName);
			users.put(userName, user);
		}
		room.setBrightness(user.getPreferredBrightness());
	}

	@Override
	public void onRoomExit(String roomName) {
		// Get the room object
		Room room = rooms.get(roomName);
		if (room == null) {
			// If room is null, the room with roomName has no connected lights. Add the room
			// to the rooms list so users can see the room and add lights
			room = new Room(roomName);
			rooms.put(roomName, room);
			return;
		}
		room.turnOff();
	}

	@Override
	public void onSetBrightness(int lightID, int brightness) {
		Light current = null;
		for (Light light : newLights)
		{
			if (light.getId() == lightID)
			{
				current = light;
				break;
			}	
		}
		if (light != null)
		{
			light.setBrightness(brightness);
		}
		//if (!lightController.setBrightness(lightID, brightness)) {
		else
		{
			System.out.println("No light found for id: " + lightID);
		}
	}

	@Override
	public void onAddLight(int lightID, String roomName) {
		// Get the light associated with ID
		Light light = null;
		for (Light l : newLights) {
			if (l.getId() == lightID) {
				light = l;
				newLights.remove(l);
				break;
			}
		}
		//If light wasnt found, print error
		if (light == null) {
			System.out.println("New light " + lightID + " not found");
			return;
		}
		// Get the room to add the light too
		Room room = rooms.get(roomName);
		// If room doesnt exist yet, create it 
		if (room == null) {
			room = new Room(roomName);
			rooms.put(roomName, room);
		}
		room.addLight(light);
	}
}
