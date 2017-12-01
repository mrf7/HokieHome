import java.util.ArrayList;
import java.util.HashMap;

//Import dependencies from maven
import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.annotation.JsonValue;

import controllers.LightController;
import controllers.MobileController;
import data.Light;
import data.Room;

public class HokieHome {
	private static HashMap<String, Room> rooms;
	private static ArrayList<Light> newLights;
	public static void main(String[] args) throws InterruptedException {
		LightController controller = LightController.getInstance();
		MobileController mcontroller = MobileController.getInstance();
		rooms = new HashMap<String, Room>();
		newLights = new ArrayList<Light>();
		controller.registerListener(new LightController.LightListener() {
			@Override
			public void onLightConnected(Light newLight, String room) {
				if (room != null) {
					System.out.println(room);
					if (!rooms.containsKey(room)) {
						Room newRoom = new Room(room);
						newRoom.addLight(newLight);
						rooms.put(room, newRoom);
					} else {
						rooms.get(room);
					}
					System.out.println("Light " + newLight.getId() + " connected. Is in " + room);
				} else {
					newLights.add(newLight);
					System.out.println("New light " +newLight.getId()+" connected");
				}
			}
		});
		Thread.sleep(8000);
		//rooms.get("red").turnOn();
		Thread.sleep(2000);
		//rooms.get("red").turnOff();
		Thread.sleep(Integer.MAX_VALUE);
	}

}
