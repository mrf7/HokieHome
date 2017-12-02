package socketIO;

import java.io.InputStream;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

//This class handles socket IO communication. It is a singleton class 
public class SocketManager {
	private static SocketIOServer server;
	private static SocketManager instance = null;
	//Creates a new SocketIO server
	private SocketManager() {
		//Set up the configuration for the server
		Configuration config = new Configuration();
	    config.setPort(9092);
//	    config.setKeyStorePassword("test1234");
//        InputStream stream = SslChatLauncher.class.getResourceAsStream("/keystore.jks");
//        config.setKeyStore(stream);
	    //Begin the socketio server
	    server = new SocketIOServer(config);
	    //Start the server asynchronously
	    server.startAsync();
	}
	//Stops the socketio server
	public static void stopServer() {
		server.stop();
	}
	// Get the instance of the SocketManager, creating if necessary. 
	public static SocketIOServer getInstance() {
		if (instance == null) {
			instance = new SocketManager();
		}
		return instance.server;
	}
}
