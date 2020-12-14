package com.example.emorocontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // Start menu koji sadrži ikone za izbor načina rada
    public void openMenu(View view){ // Otvaranje glavnog menu-a sa svim ostalim podacima
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_up, R.anim.bottom_down);
    }

    public void OpenJoy(View view){ // Pokratanje Joystick načina rada
        Intent intent = new Intent(this, Joystick.class);
        startActivity(intent);
    }

    public void OpenGyro(View view){ // Pokretanje Gyroscope načina rada
        Intent intent = new Intent(this, Gyroscope.class);
        startActivity(intent);
    }

    public void openVoice(View view){ // Pokretanje Voice Control načina rada
        Intent intent = new Intent(this, Voice.class);
        startActivity(intent);
    }

    public void OpenNormal(View view){
        Intent intent = new Intent(this, Normall.class);
        startActivity(intent);
    }


}