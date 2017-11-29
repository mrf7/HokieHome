package com.softwaredesign.group5.hokiehome;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.Manager;

import java.util.ArrayList;


public class LighManualtController extends Activity{
    ArrayList<Light> l;
    private LinearLayout layout;
   public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.room_info);
       layout= (LinearLayout)findViewById(R.id.lists);
       BeaconApplication app=(BeaconApplication) getApplication();
       l=app.getCurrentLights();
       display(l);
   }


    /**
     * Private method to display scroll bars for each light and users can adjust the brightness by moving the ball
     * @param lights the lights belonging to this room
     */
    private void display(ArrayList<Light> lights){
        layout.removeAllViews();
        TextView text= new TextView(this);
        text.setText("   Move the scrollbars to adjust the brightness of each light");
        layout.addView(text);
        if(lights!=null){
        for(int i=0;i<lights.size();i++){
            SeekBar  bar=new SeekBar(this);
            bar.setId(i);
            layout.addView(bar);
            bar.setOnSeekBarChangeListener(barListner);
        }}else{
            text.setText(" Sorry\n There is no light you can control");
        }
    }

    /**
     * private seekbar listener to handle brightness adjustment
     */
    public SeekBar.OnSeekBarChangeListener barListner=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b){

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            l.get((int)seekBar.getId()).setCurrentBrightness(seekBar.getProgress());
            System.out.println("Light "+(int)seekBar.getId()+"  "+l.get((int)seekBar.getId()).currentBrightness);
            
        }
    };


}
