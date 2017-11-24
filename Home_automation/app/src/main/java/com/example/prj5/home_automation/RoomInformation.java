package com.example.prj5.home_automation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Yibing Zhang on 11/20/2017.
 */

public class RoomInformation extends Activity {
    private TextView roomName,light;
    SeekBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_info);
        roomName=(TextView) findViewById(R.id.name);
        light=(TextView)findViewById(R.id.brightness);
        bar =(SeekBar)findViewById(R.id.brightnessBar);
    }
}
