package com.softwaredesign.group5.hokiehome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private User currentUser;
    private Room currentRoom;
    ArrayList<Room> rooms;
    ArrayList<Light> newLights;
    private Button addButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton=(Button)findViewById(R.id.button) ;
        addButton.setOnClickListener(buttonListener);
        ArrayList<Light> lights=new ArrayList<Light>();
        lights.add(new Light("1"));
        lights.add(new Light("2"));
        lights.add(new Light("3"));
        rooms=new ArrayList<Room>();
        rooms.add(new Room("room 1",lights,null));
        ArrayList<Light> lights2=new ArrayList<Light>();
        lights2.add(new Light("4"));
        rooms.add(new Room("room 2",lights2,null));
        rooms.add(new Room("room 3",null,null));
        newLights=new ArrayList<Light>();
        newLights.add(new Light("10"));
        newLights.add(new Light("20"));
        String []roomsName= {"room 1","room2","room3"};
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,roomsName);
        ListView roomView =(ListView)findViewById(R.id.list_view);
        roomView.setAdapter(adapter);
        roomView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent myIntent=new Intent(MainActivity.this,LighManualtController.class);
              startActivity(myIntent);
              BeaconApplication app= (BeaconApplication) getApplication();
              app.setCurrentLights(rooms.get(i).getLights());
           }
       }
       );
    }
    public Button.OnClickListener buttonListener=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
        if(newLights!=null){



        }
        }
    };
}
