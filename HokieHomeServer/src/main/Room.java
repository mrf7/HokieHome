package main;

import java.util.LinkedList;

public class Room
{

    private String name;
    private LinkedList<Light> lights;
    
    public Room()
    {
        name = null; 
        this.lights = new LinkedList<>();
    }
    public Room(String nameto)
    {
        name = nameto;
        this.lights = new LinkedList<>();
    }
    public String getRoomName()
    {
        return name;
    }
    public Light[] getLights()
    {
        return lights.toArray(new Light[0]);
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

    
}
