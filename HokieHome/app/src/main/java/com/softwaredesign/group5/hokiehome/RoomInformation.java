package com.softwaredesign.group5.hokiehome;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;


public class RoomInformation extends Activity{
    TextView name,light;
    SeekBar bar;
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.room_info);
            name=(TextView) findViewById(R.id.name);
            String roomNumber=getIntent().getStringExtra("RoomNumber");
            name.setText("Room "+roomNumber);
            light=(TextView)findViewById(R.id.brightness);
            bar =(SeekBar)findViewById(R.id.seekBar);
            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
