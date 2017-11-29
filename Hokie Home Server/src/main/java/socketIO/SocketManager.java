package socketIO;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
//This class handles socket IO communication. It is a singleton class 
public class SocketManager {
	private final SocketIOServer server;
	private static SocketManager instance = null;
	private SocketManager() {
		//Set up the configuration for the server
		Configuration config = new Configuration();
	    config.setPort(9092);
	    //Begin the socketio server
	    server = new SocketIOServer(config);
	    Thread 	socketServer = new Thread() {
	    	@Override 
	    	public void run() {
	    		server.start();
	    		try {
					Thread.sleep(Integer.MAX_VALUE);
				} catch (InterruptedException e) {
					server.stop();
				}
	    	}
	    };
	    socketServer.start();
	}
	// Get the instance of the SocketManager, creating if necessary. 
	public static SocketIOServer getInstance() {
		if (instance == null) {
			instance = new SocketManager();
		}
		return instance.server;
	}
}
