package androidserver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testcases
{


    @Test
    public void testUser()
    {
        User use = new User("Hello", "GoodBye");
        assertTrue(use.getUsername().equals("Hello"));
        assertTrue(use.getPassword().equals("GoodBye"));
        assertFalse(use.equals(null));
        assertFalse(use.equals("Hello"));
        User baduse = new User("", "");
        assertFalse(use.equals(baduse));
        assertTrue(use.equals(use));
        use.setPassword("Hellotwo");
        assertTrue(use.getPassword().equals("Hellotwo"));
    }
    
    @Test
    public void testLight()
    {
        Light light = new Light("192.168.1.1");
        assertTrue(light.getIpaddress().equals("192.168.1.1"));
        assertFalse(light.equals(null));
        assertFalse(light.equals("Hello"));
        assertTrue(light.equals(light));
        Light newlight = new Light("192");
        assertFalse(light.equals(newlight));
    }
    @Test
    public void testDashButton()
    {
       Light[] lights = {new Light("testone"), new Light("testtwo")};
       DashButton button = new DashButton("address", lights);
       Light[] newlights = button.getLights();
       assertTrue(lights[0].equals(newlights[0]));
       assertTrue(lights[1].equals(newlights[1]));
       assertTrue(button.getMacAddress().equals("address"));
       button.addLight(new Light("testthree"));
       assertTrue(button.getLights()[2].getIpaddress().equals("testthree"));
       button.removeLight(new Light("testthree"));
       assertTrue(button.getLights().length == 2);
       assertTrue(button.getLights()[0].equals(new Light("testone")));
       assertTrue(button.getLights()[1].equals(new Light("testtwo")));
    }
    @Test
    public void testRoom()
    {
        Room room = new Room();
        Room room2 = new Room("Living room");
        assertTrue(room2.getRoomName().equals("Living room"));
        room.addLight(new Light("lightone"));
        room.addLight(new Light("lighttwo"));
        Light[] lights = room.getLights();
        assertTrue(lights.length == 2);
        assertTrue(lights[0].getIpaddress().equals("lightone"));
        assertTrue(lights[1].getIpaddress().equals("lighttwo"));
        for(Light light: lights)
        {
            assertTrue(light.isOn() == false);
        }

    }
    @Test
    public void testServer()
    {
        Server server = new Server();
        server.addRoom(new Room("living room"));
        assertTrue(server.getRooms()[0].getRoomName().equals("living room"));
        server.addRoom(new Room("den"));
        assertTrue(server.getRooms()[1].getRoomName().equals("den"));
        server.addButton(new DashButton("hello", null));
        server.addButton(new DashButton("goodbye", null));
        assertTrue(server.getButtons()[0].getMacAddress().equals("hello"));
        assertTrue(server.getButtons()[1].getMacAddress().equals("goodbye"));
        server.removeButton(new DashButton("hello", null));
        assertTrue(server.getButtons().length == 1);
        assertTrue(server.getButtons()[0].equals(new DashButton("goodbye", null)));
        server.addUser(new User("john", "smith"));
        server.addUser(new User("jane", "smith"));
        assertTrue(server.getUsers().length == 2);
        assertTrue(server.getUsers()[0].getUsername().equals("john"));
        assertTrue(server.getUsers()[1].getUsername().equals("jane"));
        server.removeUser(new User("jane", "smith"));
        assertTrue(server.getUsers()[0].equals(new User("john", "smith")));
        server.removeUser(new User("john", "smith"));
        assertTrue(server.getUsers().length == 0);
        assertFalse(server.removeUser(new User("jason", "bourne")));
    }

}
