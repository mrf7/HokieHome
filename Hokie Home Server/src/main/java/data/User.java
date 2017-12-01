package data;

public class User
{
    private String username;
    private int prefBrightness;
    
    
    
    public User(String username, int preferredBrightness)
    {
        this.username = username;
        this.prefBrightness = preferredBrightness;
    }


    public String getUsername()
    {
        return username;
    }

    public boolean equals(Object o)
    {
        if(o == null)
        {
            return false;
        }
        if(o.getClass() == this.getClass())
        {
            User newo = (User) o;
            return this.username == newo.getUsername();
        }
        return false;
    }
    
    public String toString() {
    	return "UserName: " + username +", Brightness Pref: " + prefBrightness;
    }
}
