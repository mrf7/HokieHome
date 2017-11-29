package com.softwaredesign.group5.hokiehome;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddLightController extends AppCompatActivity{
    private ListView layout;
    private BeaconApplication app;
    private ArrayList<Light> newLights;
    private ArrayList<Room> rooms;
    private TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newly_lights);
        layout= (ListView) findViewById(R.id.newLightsList);
        text =(TextView) this.findViewById(R.id.textView);
        app=(BeaconApplication) getApplication();
        newLights=app.getNewLights();
        rooms=app.getRooms();
        display(newLights);

        }

    /**
     * Private method to display scroll bars for each light and users can adjust the brightness by moving the ball
     * @param lights the lights belonging to this room
     */
    private void display(final ArrayList<Light> lights){
        if(lights!=null&&lights.size()!=0){
            ArrayList<String> listItem=new ArrayList<String>();
            for (Light temp:newLights){
                listItem.add("Light "+temp.getMAC());
            }
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItem);
            layout.setAdapter(adapter);
            layout.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("Light i "+i+"  "+lights.get(i).getMAC());
                    showDialogListView(i);
                }
            }
            );
        }else{
            text.setText("Sorry. \nThere are no light you can add");
        }
    }
    private View createPopWindow(final int position){
        ListView popView =new ListView(AddLightController.this);
        if(rooms!=null&&rooms.size()!=0){
            ArrayList<String> list=new ArrayList<String>();
            for (Room room :rooms){
                list.add(room.getName());
            }
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            popView.setAdapter(adapter);
            popView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("Room "+position+" "+newLights.get(position).getMAC());
                    rooms.get(i).addLight(newLights.get(position));
                    TextView t=new TextView(AddLightController.this);
                    t.setText("You successfully added light "+newLights.get(position).getMAC()+" into room"+rooms.get(i).getName());
                    newLights.remove(position);
                    Toast toast=Toast.makeText(AddLightController.this,t.getText().toString(),Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }return popView;
    }
    public void showDialogListView (int position){
        AlertDialog.Builder builder =new AlertDialog.Builder(AddLightController.this);
        builder.setCancelable(true);
        builder.setView(createPopWindow(position));
        final AlertDialog dialog =builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                AddLightController.this.finish();
            }
        }, 3000);
    }

}
