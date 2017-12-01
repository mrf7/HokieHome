package com.softwaredesign.group5.hokiehome;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddLightActivity extends AppCompatActivity{
    private ListView layout;
    private BeaconApplication app;
    private Manager m;
    private TextView text;
    private ArrayList<Room> rooms;
    private ArrayList<String> listRoomName;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newly_lights);
        layout= (ListView) findViewById(R.id.newLightsList);
        text =(TextView) this.findViewById(R.id.textView);
        app=(BeaconApplication) getApplication();

        text.setText("  Wait for loading the lights");
        m = app.getManager();
        m.checkForLights(this);
    }

    /**
     * PassNewLights method that will be called by the manager to get the lights that are not set up
     * @param newLights the newly lights needed to be added into the room
     */
    public void passNewLights(ArrayList<Light>newLights){
        rooms=app.getRooms();
        for (Room room :rooms){
            listRoomName.add(room.getName());
        }
        display(newLights);
    }
    /**
     * Public method to display all the possible lights that are needed to be added
     * @param newLights the available lights to be added
     */
    public void display(ArrayList<Light>newLights){

        if(newLights!=null && newLights.size()!=0){
            text.setText("select the possible lights to add");
            ArrayList<String> listItem=new ArrayList<String>();
            for (Light temp:newLights){
                listItem.add("Light "+temp.getId());
            }
            CustomAdapter adapter=new CustomAdapter(newLights);
            layout.setAdapter(adapter);
        }else{
            text.setText("Sorry. \nThere are no light you can add");
        }
    }

    /**
     * private class CustomAdapter that we use to display the content in the custom view(select lights to add)
     */
    private class CustomAdapter extends BaseAdapter {
        private ArrayList<Light> newLights;
        public CustomAdapter(ArrayList<Light>newLights){
            this.newLights=newLights;
        }
        TextView v;
        @Override
        public int getCount() {
            return newLights.size();
        }

        @Override
        public Object getItem(int i) {
            return v;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.custom_layout,null);
            v=(TextView)view.findViewById(R.id.roomText);
            v.setText("Light "+newLights.get(i).getId());
            v.setTextSize(26);
             final Button onButton = (Button)view.findViewById(R.id.onButton);
            onButton.setText("ON");
            onButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onButton.getText().toString().equals("ON")){
                        m.setLightBrightness(newLights.get(i),10);
                        onButton.setText("OFF");
                    }else{
                        m.setLightBrightness(newLights.get(i),0);
                        onButton.setText("ON");
                    }
                }
            });
            final Button addButton = (Button)view.findViewById(R.id.addButton);
            addButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogListView(i,newLights);
                }
            });
            return view;
        }

    }

    /**
     * showDialogListView method to pop up the possible room options where user can pick to add the light(by calling createListWindow method)
     * @param position the index of light in the parent view
     */
    public void showDialogListView (int position,ArrayList<Light>newLights){
        AlertDialog.Builder builder =new AlertDialog.Builder(AddLightActivity.this);
        builder.setCancelable(false);
        builder.setView(createListWindow(position,newLights));
        final AlertDialog dialog =builder.create();
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
                AddLightActivity.this.finish();
            }
        }, 3000);
    }

    /**
     * private createListWindow method to generate a list view for the rooms
     * @param position index of light in the parent view
     * @return view that contains the possible rooms
     */
    private View createListWindow(final int position, final ArrayList<Light>newLights){
        ListView popView =new ListView(AddLightActivity.this);
        if(rooms!=null&&rooms.size()!=0){
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listRoomName);
            popView.setAdapter(adapter);
            popView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("Room "+position+" "+newLights.get(position).getId());
                    app.getManager().addLight(listRoomName.get(i),newLights.get(position));
                    TextView t=new TextView(AddLightActivity.this);
                    t.setText("You successfully added light "+newLights.get(position).getId()+" into room"+rooms.get(i).getName());
                    Toast toast=Toast.makeText(AddLightActivity.this,t.getText().toString(),Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }return popView;
    }

}
