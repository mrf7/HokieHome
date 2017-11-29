package com.softwaredesign.group5.hokiehome;


import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;


public class LighManualtController extends Activity{

    ArrayList<Light> l;
    private LinearLayout layout;
    private BeaconApplication app;


    @Override
    public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.room_info);
       layout= (LinearLayout)findViewById(R.id.lists);
       app=(BeaconApplication) getApplication();
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
            bar.setOnSeekBarChangeListener(barListener);
        }}else{
            text.setText(" Sorry\n There is no light you can control");
        }
    }

    /**
     * private SeekBar listener to handle brightness adjustment
     */
    public SeekBar.OnSeekBarChangeListener barListener =new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b){

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            System.out.println("Light "+(int)seekBar.getId()+"  "+l.get((int)seekBar.getId()).currentBrightness+"  before");
            app.getManager().setLightBrightness(l.get(seekBar.getId()),seekBar.getProgress());
            System.out.println("Light "+(int)seekBar.getId()+"  "+l.get((int)seekBar.getId()).currentBrightness);
            
        }
    };


}
