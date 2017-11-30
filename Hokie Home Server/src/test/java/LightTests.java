import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.LightController;
import data.Light;
import socketIO.SocketManager;

public class LightTests implements LightController.LightListener{
	private static LightController lightController;
	//Start the server at the beginning of the test
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lightController = LightController.getInstance();
	}
	//Stop the server after tests
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SocketManager.stopServer();
	}

	@Test
	public void test() {
		lightController.registerListener(this);
	}
	
	@Override
	public void onLightConnected(Light newLight, String room) {
		// TODO Auto-generated method stub
		
	}

}
