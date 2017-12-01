package com.softwaredesign.group5.hokiehome;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private User currentUser;
    private Room currentRoom;
    private  BeaconApplication app;
    private Button addButton;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton=(Button)findViewById(R.id.button) ;
        addButton.setOnClickListener(buttonListener);
        app= (BeaconApplication) getApplication();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check 
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        app.getManager().checkForRooms(this);
    }

    /**
     * passRooms method that will be called by manager to pass the rooms
     * @param rooms are the rooms that are set up
     */
    public void passRooms (final ArrayList<Room> rooms)
    {
        ArrayList<String> listItem= new ArrayList<String>();
        app.setRooms(rooms);
        if(rooms==null){
            TextView t=new TextView(this);
            t.setText("There is no room set up in the system");
            Toast toast=Toast.makeText(this,t.getText().toString(),Toast.LENGTH_LONG);
            toast.show();
        }else {
            if (rooms != null && rooms.size() != 0) {
                for (Room temp : rooms) {
                    listItem.add(temp.getName());
                }
            }
            ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
            ListView roomView = (ListView) findViewById(R.id.list_view);
            roomView.setAdapter(adapter);
            roomView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    app.setCurrentLights(rooms.get(i).getLights());
                                                    Intent myIntent = new Intent(MainActivity.this, LightManualActivity.class);
                                                    startActivity(myIntent);
                                                }
                                            }
            );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app = (BeaconApplication) getApplication();
            app.getB().unbind();
    }

    /**
     * private buttonListener to start the addLight activity
     */
    private Button.OnClickListener buttonListener=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent myIntent=new Intent(MainActivity.this,AddLightActivity.class);
            startActivity(myIntent);
        }
    };
}
