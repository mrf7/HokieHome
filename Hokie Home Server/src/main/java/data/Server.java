package data;

import java.util.LinkedList;

public class Server
{

    private LinkedList<Room> rooms;
    private LinkedList<DashButton> buttons;
    private LinkedList<User> user;
    
    public Server()
    {
        rooms = new LinkedList<Room>();
        buttons= new LinkedList<DashButton>();
        user = new LinkedList<User>();
    }
    public void onButtonPress(String mac)
    {
        
    }
    public void onRoomEntered(Room room, User user)
    {
        
    }
    public void onRoomLeft(Room room, User user)
    {
        
    }
    public boolean addRoom(Room r)
    {
        return rooms.add(r);
    }
    public boolean addButton(DashButton button)
    {
        return buttons.add(button);
    }
    public boolean removeButton(DashButton button)
    {
        if(buttons.size() == 0)
        {
            return false;
        }
        return buttons.remove(button);
    }
    public boolean addUser(User use)
    {
        return user.add(use);
    }
    public boolean removeUser(User use)
    {
        if(user.size() == 0)
        {
            return false;
        }
        return user.remove(use);
    }
    public Room[] getRooms()
    {
        return rooms.toArray(new Room[0]);
    }
    public DashButton[] getButtons()
    {
        return buttons.toArray(new DashButton[0]);
    }
    public User[] getUsers()
    {
        return user.toArray(new User[0]);
    }
     
    

    
}
