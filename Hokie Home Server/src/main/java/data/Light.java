package data;

public class Light
{
    private String ipAddress;
    private boolean isOn;
    
    
    public Light(String ip)
    {
        ipAddress = ip;
        isOn = false;
    }
    public boolean isOn()
    {
        return isOn;
    }
    public void turnOn()
    {
        
    }
    public void turnOff()
    {
        
    }
    public String getIpaddress()
    {
        return ipAddress;
    }
    public boolean equals(Object o)
    {
        if(o == null)
        {
            return false;
        }
        if(o.getClass() == this.getClass())
        {
            Light comp = (Light)o;
            return this.ipAddress.equals(comp.getIpaddress());
        }
        return false;
    }
    
}
