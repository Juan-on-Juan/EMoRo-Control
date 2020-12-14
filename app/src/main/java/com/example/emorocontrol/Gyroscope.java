package com.example.emorocontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.Math;
import java.util.UUID;

import static android.util.Half.EPSILON;

public class Gyroscope extends AppCompatActivity {
    private Gyro gyroscope;
    // Stvari za komunikaciju sa eMoro
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket mySocket = null;
    private boolean isBluetoothConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;

    // stvari za rad Žiroskopa
    public ImageView gyropointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);
        gyropointer = (ImageView) findViewById(R.id.gyro_pointer);
        gyroscope = new Gyro(this);
        new ConnectBT().execute();

        gyroscope.setListener(new Gyro.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if(rx > 1f){
                    if(ry > 1f){
                        try
                        {
                            mySocket.getOutputStream().write("H".toString().getBytes());
                            gyropointer.setImageResource(R.drawable.arrow_southwest);
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    } else if (ry < -1f){
                        try
                        {
                            mySocket.getOutputStream().write("J".toString().getBytes());
                            gyropointer.setImageResource(R.drawable.arrow_southeast);
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    } else {
                        try
                        {
                            mySocket.getOutputStream().write("B".toString().getBytes());
                            gyropointer.setImageResource(R.drawable.arrow_south);
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                } else if (rx < -1f){
                    if(ry > 1f){
                        try
                        {
                            mySocket.getOutputStream().write("I".toString().getBytes());
                            gyropointer.setImageResource(R.drawable.arrow_northeast);
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    } else if(ry < -1f){
                        try
                        {
                            mySocket.getOutputStream().write("G".toString().getBytes());
                            gyropointer.setImageResource(R.drawable.arrow_northwest);
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    } else {
                        try
                        {
                            mySocket.getOutputStream().write("F".toString().getBytes());
                            gyropointer.setImageResource(R.drawable.arrow_north);
                        }
                        catch (IOException e)
                        {
                            msg("Error");
                        }
                    }
                } else if(ry > 1f){
                    try
                    {
                        mySocket.getOutputStream().write("L".toString().getBytes());
                        gyropointer.setImageResource(R.drawable.arrow_east);
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                } else if(ry < -1f){
                    try
                    {
                        mySocket.getOutputStream().write("R".toString().getBytes());
                        gyropointer.setImageResource(R.drawable.arrow_west);
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                } else {
                    try
                    {
                        mySocket.getOutputStream().write("S".toString().getBytes());
                        gyropointer.setImageResource(R.drawable.still);
                    }
                    catch (IOException e)
                    {
                        msg("Error");
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscope.register();
    }

    @Override
    protected void onPause(){
        super.onPause();
        gyroscope.unregister();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> 
    {
        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Gyroscope.this, "Connecting...", "Please wait!!!");
        }
        @Override
        /*
            Dok se prikazuje "Connecting..." ne želimo da app prestane s povezivanjem pa nastavljamo povezivanje u pozadini 
        */
        protected Void doInBackground(Void... devices) 
        {
            try
            {
                if (mySocket == null || !isBluetoothConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(popUp.address);
                    mySocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    mySocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false; // Povezivanje nije uspjelo
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) // Provjera uspjeha povezivanja
        {
            super.onPostExecute(result);
            if (!ConnectSuccess) // Spajanje nije uspjelo
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else // Spajanje je uspjelo
            {
                msg("Connected.");
                isBluetoothConnected = true;
            }
            progress.dismiss();
        }
    }

    // Funkcija za lakši ispis toast poruke
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void finish() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Menu
    public void OpenJoy(View view){
        Intent intent = new Intent(this, Joystick.class);
        startActivity(intent);
    }

    public void openMenu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_up, R.anim.bottom_down);
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







