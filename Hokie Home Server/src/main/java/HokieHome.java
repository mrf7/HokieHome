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
import data.Server;

public class HokieHome {
	private static HashMap<String, Room> rooms;
	private static ArrayList<Light> newLights;
	public static void main(String[] args) throws InterruptedException {
		Server server = Server.getInstance();
		Thread.sleep(Integer.MAX_VALUE);
	}
}
