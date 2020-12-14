package com.example.emorocontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    // Cjeli/Veliki Menu

    public void homeSc(View view){ // Povratak na početni zaslon
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, R.anim.bottom_down);
    }

    public void OpenJoy(View view){ // Pokretanje Joystick načina rada
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

    public void OpenNormal(View view) {
        Intent intent = new Intent(this, Normall.class);
        startActivity(intent);
    }

    public void openAT(View view){
        Intent intent = new Intent(this, AboutApp.class);
        startActivity(intent);
    }

    public void openAUTH(View view){
        Intent intent = new Intent(this, AboutAuthors.class);
        startActivity(intent);
    }

    // Potrebno dodati NORMAL, ABOUT AUTHORS, ABOUT APP

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_up, R.anim.bottom_down);
    }

    public void finishes(View view) { // PROVJERITI ŠTO TOČNO OVO RADI JER MI DJELUJE DA NIJE POTREBNO WTH
        super.finish();
        overridePendingTransition(R.anim.bottom_up, R.anim.bottom_down);
    }
}