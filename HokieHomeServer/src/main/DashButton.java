package main;

import java.util.Arrays;
import java.util.LinkedList;

public class DashButton
{
    
    private String macAddress;
    private LinkedList<Light> lights;
    
    public DashButton(String address, Light[] Lights)
    {
        if(Lights == null)
        {
            this.lights = new LinkedList<>();
        }
        else
        {
            this.lights = new LinkedList<>(Arrays.asList(Lights));
        }
        macAddress = address;
    }
    public String getMacAddress()
    {
        return macAddress;
    }
    public Light[] getLights()
    {
        return lights.toArray(new Light[0]);
    }
    public void setMacAddress(String macaddress)
    {
        macAddress = macaddress;
    }
    public boolean addLight(Light light)
    {
        return lights.add(light);
    }
    public boolean removeLight(Light light)
    {
        if(lights.size() > 0)
        {
            return lights.remove(light);
        }
        return false;
    }
    public boolean equals(Object o)
    {
        if(o == null)
        {
            return false;
        }
        else if(this.getClass() != o.getClass())
        {
            return false;
        }
        else
        {
            DashButton obutton = (DashButton)o;
            return this.getMacAddress().equals(obutton.getMacAddress());
        }
    }


    
}
