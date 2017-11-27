package data;

public class User
{
    private String username;
    private String password;
    
    
    
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
    public void setPassword(String pass)
    {
        this.password = pass;
    }

    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
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
    
}
