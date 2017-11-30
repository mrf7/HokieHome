import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.LightController;
import controllers.LightController.LightListener;
import data.Light;
import data.Room;
import junit.extensions.TestSetup;
import socketIO.SocketManager;

public class LightTests {
	private static LightController lightController;
	private static HashMap<Integer, Light> newLights;
	private static HashMap<String, HashMap<Integer, Light>> rooms;

	// Start the server at the beginning of the test
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lightController = LightController.getInstance();
		newLights = new HashMap<Integer, Light>();
		rooms = new HashMap<String, HashMap<Integer, Light>>();
	}

	// Conenct a light before all tests start
	@Before
	public void setupBefore() throws IOException, InterruptedException {
		//If a light hasnt been connected yet, get one
		if (newLights.isEmpty()) {
			lightController.registerListener(listener);
			System.out.println("Open LightSimulatorTest.html, enter 1 in ID, and hit enter in the java console:");
			System.in.read();
			// Give Socket time to connect
			Thread.sleep(100);
		}
	}

	// Stop the server after tests
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SocketManager.stopServer();
	}
	//Tests adding new lights with and without a room
	@Test
	public void testNewLight() throws InterruptedException, IOException {

		// Make sure the light was added to the new lights lights list properly
		assertTrue(newLights.containsKey(1));
		// Add a light with a room
		System.out.println(
				"Open another instance of LightSimulatorTest.html, enter 2 in ID, test1 in room. Then hit connect and hit enter in the java console:");
		System.in.read();
		// Make sure light was not addded to new lights but was added to new room
		Thread.sleep(8000);
		assertFalse(newLights.containsKey(2));
		assertTrue(rooms.containsKey("test1"));
		assertTrue(rooms.get("test1").containsKey(2)); 
	}
	
	/**
	 * Tests the setBrightness method
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testSetBrightness() throws InterruptedException {
		// Get a light to test with
		Light light = newLights.get(1);
		// Test set brightness with valid numbers

		assertTrue(light.setBrightness(40));
		assertEquals(light.getBrightness(), 40);
		System.out.println("Brightness should be 40");
		assertTrue(light.setBrightness(30));
		assertEquals(light.getBrightness(), 30);
		System.out.println("Brightness should be 30");
		// Test edges
		assertTrue(light.setBrightness(0));
		assertEquals(light.getBrightness(), 0);
		System.out.println("Brightness should be 0");
		assertTrue(light.setBrightness(100));
		assertEquals(light.getBrightness(), 100);
		System.out.println("Brightness should be 100");
		// Test invalid
		assertFalse(light.setBrightness(-1));
		assertEquals(light.getBrightness(), 100);
		assertFalse(light.setBrightness(101));
		assertEquals(light.getBrightness(), 100);
		//Test turn on/off
		assertTrue(light.turnOff());
		System.out.println("Brightness should be 0");
		assertEquals(light.getBrightness(), 0);
		assertTrue(light.turnOn());
		System.out.println("Brightness should be 100");
		assertEquals(light.getBrightness(), 100);
		//Test failure for invalid light id
		Light badLight = new Light(49);
		assertFalse(badLight.turnOff());
		assertFalse(badLight.turnOn());
		assertFalse(badLight.setBrightness(54));
	}
	//Tests setting the light's room
	@Test
	public void testSetRoom() throws InterruptedException {
		Light light = newLights.get(1);
		Room room = new Room("test2");
		assertTrue(lightController.setRoom(light, room));
		//Wait for lightIdent to run
		Thread.sleep(100);
		//Make sure light removed from newLights and added to room
		assertFalse(newLights.containsKey(1));
		assertTrue(rooms.containsKey("test2"));
		assertTrue(rooms.get("test2").containsKey(1));
	} 
	//Test unregister listener
	public void testUnregisterListener() throws IOException {
		lightController.unregisterListener(listener);
		System.out.println(
				"Open another instance of LightSimulatorTest.html, enter 3 in ID. Then hit connect and hit enter in the java console:");
		System.in.read();
		assertFalse(newLights.containsKey(3));
	}
	private static LightListener listener = new LightListener() {
		@Override
		public void onLightConnected(Light newLight, String room) {
			if (room != null) {
				//If the light was set up after connection, remove it from the newLights list
				if (newLights.containsKey(1)) {
					newLights.remove(1);
				}
				if (rooms.containsKey(room)) {
					rooms.get(room).put(newLight.getId(), newLight);
				} else {
					HashMap<Integer, Light> lightsMap = new HashMap<Integer, Light>();
					lightsMap.put(newLight.getId(), newLight);
					rooms.put(room, lightsMap);
				}
			} else {
				newLights.put(newLight.getId(), newLight);
			}
			System.out.println(newLight + "  " + room);
		}
	};

}
