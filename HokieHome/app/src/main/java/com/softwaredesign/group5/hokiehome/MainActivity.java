package com.softwaredesign.group5.hokiehome;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private User currentUser;
    private Room currentRoom;
    ArrayList<Room> rooms;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Light> lights=new ArrayList<Light>();
        lights.add(new Light("1"));
        lights.add(new Light("2"));
        lights.add(new Light("3"));
        rooms=new ArrayList<Room>();
        rooms.add(new Room("room 1",lights,null));
        ArrayList<Light> lights2=new ArrayList<Light>();
        lights2.add(new Light("4"));
        rooms.add(new Room("room 2",lights2,null));
        rooms.add(new Room("room 1",null,null));
        String []roomsName= {"room 1","room2","room3"};
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,roomsName);
        ListView roomView =(ListView)findViewById(R.id.list_view);
        roomView.setAdapter(adapter);
        roomView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               RoomInformation frag= (RoomInformation) getFragmentManager().findFragmentById(R.id.fragment);
               switch (i){
                   case 0:
                        frag.setRoomLight(rooms.get(0).getLights());
                       break;
                   case 1:
                       frag.setRoomLight(rooms.get(1).getLights());break;
                   case 2: frag.setRoomLight(rooms.get(2).getLights());break;
               }
           }
       }
       );

    }
}
