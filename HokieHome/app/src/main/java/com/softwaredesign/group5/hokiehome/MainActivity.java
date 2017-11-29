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
    private  BeaconApplication app;
    ArrayList<Room> rooms;
    private Button addButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton=(Button)findViewById(R.id.button) ;
        addButton.setOnClickListener(buttonListener);
        app= (BeaconApplication) getApplication();
        rooms=app.getRooms();
        ArrayList<String> listItem= new ArrayList<String>();
        if(rooms!=null&&rooms.size()!=0){
            for(Room temp:rooms){
                listItem.add(temp.getName());
            }
        }
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItem);
        ListView roomView =(ListView)findViewById(R.id.list_view);
        roomView.setAdapter(adapter);
        roomView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               app.setCurrentLights(rooms.get(i).getLights());
              Intent myIntent=new Intent(MainActivity.this,LighManualtController.class);
              startActivity(myIntent);

           }
       }
       );
    }
    public Button.OnClickListener buttonListener=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent myIntent=new Intent(MainActivity.this,AddLightController.class);
            startActivity(myIntent);
        }
    };
}
