package com.example.emorocontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;


import io.github.controlwear.virtual.joystick.android.JoystickView;

public class Joystick extends AppCompatActivity {
    // Stvari za komunikaciju sa eMoro
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ConnectBT().execute();
        setContentView(R.layout.activity_joystick);

        // Stvaranje joysticka i povezivanje s joystickom unutar XML-a
        JoystickView joystick = (JoystickView) findViewById(R.id.joystick);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                /*
                    Provjerava kut tj. smjer i snagu joystica tj. koliko smo daleko povukli
                    i na osnovu toga šalje signal za pokretanje robota u određenom smjeru
                */
                if(strength<70) {if (btSocket!=null)
                {
                    try
                    {
                        btSocket.getOutputStream().write("S".toString().getBytes());
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                }}
                else {
                    if ((angle < 30 && angle >= 0) || (angle < 360 && angle >= 330))
                    {if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("R".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }}
                    if (angle < 70 && angle >= 30) {if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("I".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }}
                    if (angle < 110 && angle >= 70) {
                        if (btSocket!=null)
                        {
                            try
                            {
                                btSocket.getOutputStream().write("F".toString().getBytes());
                            }
                            catch (IOException e)
                            {
                                msg("Error");
                            }
                        }
                    }
                    if (angle < 150 && angle >= 110) {if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("G".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }}
                    if (angle < 210 && angle >= 150) {
                        if (btSocket!=null)
                        {
                            try
                            {
                                btSocket.getOutputStream().write("L".toString().getBytes());
                            }
                            catch (IOException e)
                            {
                                msg("Error");
                            }
                        }
                    }
                    if (angle < 250 && angle >= 210) {if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("H".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }}
                    if (angle < 290 && angle >= 250) {
                        if (btSocket!=null)
                        {
                            try
                            {
                                btSocket.getOutputStream().write("B".toString().getBytes());
                            }
                            catch (IOException e)
                            {
                                msg("Error");
                            }
                        }
                    }
                    if (angle < 330 && angle >= 290) {if (btSocket!=null)
                    {
                        try
                        {
                            btSocket.getOutputStream().write("J".toString().getBytes());
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }}
                }
            }
        });
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  
    {
        private boolean ConnectSuccess = true; 
        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Joystick.this, "Connecting...", "Please wait!!!");  
        }
        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(popUp.address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) 
        {
            super.onPostExecute(result);
            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    @Override
    public void finish() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
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