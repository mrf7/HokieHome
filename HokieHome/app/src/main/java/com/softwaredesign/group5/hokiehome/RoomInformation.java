package com.softwaredesign.group5.hokiehome;


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


public class RoomInformation extends Fragment{
    View view;
    ArrayList<Light> l;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                    Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.room_info, container, false);
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Set the lights that belong to this room and then display
     * @param lights the lights belonging to this room
     */
    public void setRoomLight(ArrayList<Light> lights){
        l=lights;
        display(l);
    }

    /**
     * Private method to display scroll bars for each light and users can adjust the brightness by moving the ball
     * @param lights the lights belonging to this room
     */
    private void display(ArrayList<Light> lights){
        LinearLayout layout= (LinearLayout) view.findViewById(R.id.lists);
        layout.removeAllViews();
        TextView text= new TextView(getActivity());
        text.setText("   Move the scrollbars to adjust the brightness of each light");
        layout.addView(text);
        if(lights!=null){
        for(int i=0;i<lights.size();i++){
            SeekBar  bar=new SeekBar(getActivity());
            bar.setId(i);
            System.out.println("id   "+bar.getId());
             layout.addView(bar);
             bar.setOnSeekBarChangeListener(barListner);
        }}
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
