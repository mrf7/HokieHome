
import examples.ChatObject;

import java.util.HashMap;
import org.json.*;

//Import dependencies from maven
import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.annotation.JsonValue;

public class HokieHome {
	static HashMap<String, SocketIOClient> lights = new HashMap<String, SocketIOClient>();
	public static void main(String[] args) throws InterruptedException {

	}

}
