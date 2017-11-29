import java.util.HashMap;

//Import dependencies from maven
import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
public class HokieHome {
	static HashMap<String, SocketIOClient> lights = new HashMap<String, SocketIOClient>();
	public static void main(String[] args) throws InterruptedException {
		System.out.println("fasd");
		//Set up configuration
		Configuration config = new Configuration();
	    //config.setHostname("10.0.0.107");
	    config.setPort(9092);
	    
	     
		//Create the socketio server object
		final SocketIOServer server = new SocketIOServer(config);
//		server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>(){
//			@Override
//			public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
//				System.out.println("Received data");
//				server.getBroadcastOperations().sendEvent("chatevent", data);
//			}
//		});
		server.addEventListener("roomIdent", String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient cl, String data, AckRequest ackSender) throws Exception {
				if (!lights.containsKey(data)) {
					lights.put(data, cl);
				}
				System.out.println(data + " responded");
			}
			
		});
		server.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient cl) {
				
				//cl.sendEvent("ON");
				System.out.println("onConn: "+ cl.getHandshakeData().getAddress().getHostName());
				cl.sendEvent("changeBrightness", 100);
			}
		});
		server.start();
		Thread.sleep(1000);
		boolean redOn = false;
		while (!lights.containsKey("red") || !lights.containsKey("yellow")) {
			Thread.sleep(500);
		}
		System.out.println("Both found");
		while(true) {
			Thread.sleep(2000);
			lights.get("red").sendEvent("changeBrightness", redOn ? 0 : 100);
			lights.get("yellow").sendEvent("changeBrightness", redOn ? 100 : 0);
			redOn = !redOn;
		}
		
//		Thread.sleep(Integer.MAX_VALUE);
//		server.stop();
	}

}
