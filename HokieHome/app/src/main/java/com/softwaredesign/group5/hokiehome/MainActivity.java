package com.softwaredesign.group5.hokiehome;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private Button addButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton=(Button)findViewById(R.id.button) ;
        addButton.setOnClickListener(buttonListener);
        app= (BeaconApplication) getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        app.getManager().checkForRooms(this);
    }

    public void passRooms (final ArrayList<Room> rooms)
    {
        ArrayList<String> listItem= new ArrayList<String>();
        app.setRooms(rooms);
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
                Intent myIntent=new Intent(MainActivity.this,LightManualActivity.class);
                startActivity(myIntent);
             }}
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app = (BeaconApplication) getApplication();
            app.getB().unbind();
    }
    public Button.OnClickListener buttonListener=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent myIntent=new Intent(MainActivity.this,AddLightActivity.class);
            startActivity(myIntent);
        }
    };
}
