import java.util.ArrayList;
import java.util.HashMap;

import controllers.LightController;
import controllers.LightController.LightListener;
import controllers.MobileController;
import controllers.MobileController.MobileListener;
import data.Light;
import data.Room;
import data.User;

public class Server implements LightListener, MobileListener {
	private static Server instance = null;
	private static ArrayList<Light> newLights;
	private static HashMap<String, Room> rooms;
	// Create the server instance to store controllers and listeners
	private Server() {
		LightController lightController = LightController.getInstance();
		MobileController mobileController = MobileController.getInstance();
		rooms = new HashMap<String, Room>();
		newLights = new ArrayList<Light>();
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
	
	//Getters
	public static HashMap<String, Room> getRooms() {
		return rooms;
	}

	public static ArrayList<Light> getNewLights() {
		return newLights;
	}

	@Override
	public void onLightConnected(Light newLight, String room) {
		if (room != null) {
			if (!rooms.containsKey(room)) {
				Room newRoom = new Room(room);
				newRoom.addLight(newLight);
				rooms.put(room, newRoom);
			} else {
				rooms.get(room);
			}
		} else {
			newLights.add(newLight);
		}

	}

	@Override
	public void onUserConnected(User user) {
		// TODO Auto-generated method stub
	}
}
