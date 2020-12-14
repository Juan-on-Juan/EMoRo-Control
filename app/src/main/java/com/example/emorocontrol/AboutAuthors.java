package com.example.emorocontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AboutAuthors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_authors);
    }

    // Menu
    public void openMenu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_up, R.anim.bottom_down);
    }

    public void OpenGyro(View view){
        Intent intent = new Intent(this, Gyroscope.class);
        startActivity(intent);
    }

    public void openVoice(View view){
        Intent intent = new Intent(this, Voice.class);
        startActivity(intent);
    }

    public void OpenNormal(View view){
        Intent intent = new Intent(this, Normall.class);
        startActivity(intent);
    }
}