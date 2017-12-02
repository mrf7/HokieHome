package data;

import java.util.ArrayList;
import java.util.LinkedList;

public class Room
{

    private String name;
    private ArrayList<Light> lights;
    
    public Room()
    {
        name = null; 
        this.lights = new ArrayList<Light>();
    }
    public Room(String nameto)
    {
        name = nameto;
        this.lights = new ArrayList<Light>();
    }
    public String getRoomName()
    {
        return name;
    }
    public ArrayList<Light> getLights()
    {
        return lights;
    }
    public Boolean addLight(Light light)
    {
        return lights.add(light);
    }
    public Boolean removeLight(Light light)
    {
        return lights.remove(light);
    }
    public void turnOn()
    {
        for(Light light : lights)
        {
            light.turnOn();
        }
    }
    public void turnOff()
    {
        for(Light light:lights)
        {
            light.turnOff();
        }
    }
    public void setBrightness(int brightness) {
    	for (Light light : lights) {
    		light.setBrightness(brightness);
    	}
    }
    @Override
    public String toString() {
    	return name + " has lights: " +lights;
    }
    
}
