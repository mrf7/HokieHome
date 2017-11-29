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
    private ArrayList<Light> newLights;
    private ArrayList<Room> rooms;
    private TextView text;
    ArrayList<String> listName;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newly_lights);
        layout= (ListView) findViewById(R.id.newLightsList);
        text =(TextView) this.findViewById(R.id.textView);
        app=(BeaconApplication) getApplication();
        newLights=app.getNewLights();
        rooms=app.getRooms();
        listName=new ArrayList<String>();
        for (Room room :rooms){
            listName.add(room.getName());
        }
        display(newLights);
        }

    /**
     * Private method to display all the possible lights that are needed to be added
     * @param lights the available lights to be added
     */
    private void display(final ArrayList<Light> lights){
        if(lights!=null&&lights.size()!=0){
            ArrayList<String> listItem=new ArrayList<String>();
            for (Light temp:newLights){
                listItem.add("Light "+temp.getMAC());
            }
            CustomAdapter adapter=new CustomAdapter();
            layout.setAdapter(adapter);
        }else{
            text.setText("Sorry. \nThere are no light you can add");
        }
    }

    /**
     * showDialogListView method to pop up the possible room options where user can pick to add the light(by calling createListWindow method)
     * @param position the index of light in the parent view
     */
    public void showDialogListView (int position){
        AlertDialog.Builder builder =new AlertDialog.Builder(AddLightActivity.this);
        builder.setCancelable(false);
        builder.setView(createListWindow(position));
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
    private View createListWindow(final int position){
        ListView popView =new ListView(AddLightActivity.this);
        if(rooms!=null&&rooms.size()!=0){
            ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listName);
            popView.setAdapter(adapter);
            popView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("Room "+position+" "+newLights.get(position).getMAC());
                    app.getManager().addLight(rooms.get(i),newLights.get(position));
                    TextView t=new TextView(AddLightActivity.this);
                    t.setText("You successfully added light "+newLights.get(position).getMAC()+" into room"+rooms.get(i).getName());
                    newLights.remove(position);
                    Toast toast=Toast.makeText(AddLightActivity.this,t.getText().toString(),Toast.LENGTH_SHORT);
                    toast.show();

                }
            });
        }return popView;
    }

    /**
     * private class CustomAdapter that we use to display the content in the custom view(select lights to add)
     */
    private class CustomAdapter extends BaseAdapter {
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
            v.setText("Light "+newLights.get(i).getMAC());
            v.setTextSize(26);
             final Button onButton = (Button)view.findViewById(R.id.onButton);
            onButton.setText("ON");
            onButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onButton.getText().toString().equals("ON")){
                        System.out.println("i: "+ i +"brightness of the light before on "+newLights.get(i).getCurrentBrightness());
                        app.getManager().setLightBrightness(newLights.get(i),10);
                        System.out.println(newLights.get(i).getCurrentBrightness());
                        onButton.setText("OFF");
                    }else{
                        System.out.println("i: "+ i +"brightness of the light before off "+newLights.get(i).getCurrentBrightness());
                        app.getManager().setLightBrightness(newLights.get(i),0);
                        System.out.println(newLights.get(i).getCurrentBrightness());
                        onButton.setText("ON");
                    }
                }
            });
            final Button addButton = (Button)view.findViewById(R.id.addButton);
            addButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialogListView(i);
                }
            });
            return view;
        }

    }
}
