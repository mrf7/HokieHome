package socketIO;
import examples.ChatObject;

//Import dependencies from maven
import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
public class HokieHome {
	static SocketIOClient client = null;
	public static void main(String[] args) throws InterruptedException {
		System.out.println("fasd");
		//Set up configuration
		Configuration config = new Configuration();
	    //config.setHostname("10.0.0.107");
	    config.setPort(9092);
	    
	     
		//Create the socketio server object
		final SocketIOServer server = new SocketIOServer(config);
		server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>(){
			@Override
			public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
				System.out.println("Received data");
				server.getBroadcastOperations().sendEvent("chatevent", data);
			}
		});
		server.addEventListener("message", String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient cl, String data, AckRequest ackSender) throws Exception {
				System.out.println("Got messagefdsa: "+data);
				HokieHome.client = cl;
				//client.sendEvent("ON");
			}
			
		});
		server.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient cl) {
				
				//cl.sendEvent("ON");
				System.out.println("onConn: "+ cl.getHandshakeData().getAddress().getHostName());
				
			}
		});
		server.start();
		Thread.sleep(1000);
		boolean on = false;
		while(true) {
			Thread.sleep(2000);
			if (client!=null) {
				client.sendEvent(on? "OFF" : "ON");
				on = !on;
				System.out.println("Sent: "+ client);
			}
		}
		
//		Thread.sleep(Integer.MAX_VALUE);
//		server.stop();
	}

}
