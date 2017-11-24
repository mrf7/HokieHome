package com.example.prj5.home_automation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private Button room1,room2,room3;
    private TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome=(TextView)findViewById(R.id.welcomeText);
        welcome.setText("Welcome to our Home_Automation APP!\nPlease select your current location!");
        room1=(Button)findViewById(R.id.button);
        room2=(Button)findViewById(R.id.button2);
        room3=(Button)findViewById(R.id.button3);
        room1.setOnClickListener(buttonListener);
        room2.setOnClickListener(buttonListener);
        room3.setOnClickListener(buttonListener);
    }
    public View.OnClickListener buttonListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button:
                    System.out.println("1");
                    //we can pass the room information into room class so then display whatever speaker and light contain in that room
                    RoomInformation r=new RoomInformation();
                    Intent myIntent =new Intent(MainActivity.this,r.getClass());
                    startActivity(myIntent);
                    break;
                case R.id.button2:
                    System.out.println("2");
                    break;
                case R.id.button3:
                    System.out.println("3");
                    break;
            }
        }
    };
}
