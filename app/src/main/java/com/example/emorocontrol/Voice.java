package com.example.emorocontrol;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class Voice extends AppCompatActivity {
    // Stvari za komunikaciju sa eMoro
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;

    // Ispis naredbe koju smo izgovorili
    public TextView naredba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ConnectBT().execute();
        setContentView(R.layout.activity_voice);
        naredba = (TextView)findViewById(R.id.naredba);
    }

    public void getSpeech(View view){
        // Funkcija koja dohvaća govor
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Vaš uređaj Ne Podržava Glasovno Upravljanje", Toast.LENGTH_SHORT).show();
            // Treba vratiti korisnika na home screen zato što ne može koristiti ovu funkciju
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Dohvaća riječi koje smo izgovorili
        if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                recognizeAction(result.get(0));
            }
        }
    }

    private void recognizeAction(String s) {
        s = s.toLowerCase();
        // prepoznaje riječi koje smo izgovorili i šalje signal za pokretanje u željenom smjeru
        switch (s) {
            case "go forward":
            case "move forward":
            case "go":
            case "forward":
            case "go ahead":
                try{
                    btSocket.getOutputStream().write("F".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            case "to the left":
            case "to left":
            case "left":
            case "turn left":
            case "move left":
            case "go left":
                try{
                    btSocket.getOutputStream().write("L".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            case "to the right":
            case "to right":
            case "right":
            case "turn right":
            case "move right":
            case "go right":
                try{
                    btSocket.getOutputStream().write("R".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            case "go back":
            case "come back":
            case "move back":
            case "go backward":
            case "backward":
            case "backwards":
            case "go backwards":
            case "move backwards":
            case "move backward":
                try{
                    btSocket.getOutputStream().write("B".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            case "right up":
            case "up right":
            case "right and up":
            case "up and right":
            case "right forward":
            case "forward right":
            case "right and forward":
            case "forward and right":
            case "up then right":
            case "upright":
                try{
                    btSocket.getOutputStream().write("I".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            case "right back":
            case "back right":
            case "right and back":
            case "back and right":
            case "right backward":
            case "backward right":
            case "right and backward":
            case "backward and right":
            case "backward then right":
            case "backwards right":
                try{
                    btSocket.getOutputStream().write("J".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            case "left up":
            case "up left":
            case "left and up":
            case "up and left":
            case "left forward":
            case "forward left":
            case "left and forward":
            case "forward and left":
            case "up then left":
            case "upleft":
                try{
                    btSocket.getOutputStream().write("G".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            case "left back":
            case "back left":
            case "left and back":
            case "back and left":
            case "left backward":
            case "backward left":
            case "left and backward":
            case "backward and left":
            case "backward then left":
            case "backwards left":
                try{
                    btSocket.getOutputStream().write("H".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            case "stop":
            case "abort":
            case "mayday":
                try{
                    btSocket.getOutputStream().write("S".toString().getBytes());
                } catch (IOException e) {
                    msg("Error");
                }
                break;
            default:
                Toast.makeText(this, "Ili nemamo tu opciju ili vas program nije dobro razumio probajte opet!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Voice.this, "Connecting...", "Please wait!!!");
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
                    btSocket.connect();//start connection
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

    // Funkcija za lakši ispis toast poruke
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    //Menu
    public void openMenu(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.bottom_up, R.anim.bottom_down);
    }

    public void OpenJoy(View view){
        Intent intent = new Intent(this, Joystick.class);
        startActivity(intent);
    }

    public void OpenGyro(View view){
        Intent intent = new Intent(this, Gyroscope.class);
        startActivity(intent);
    }

    public void OpenNormal(View view){
        Intent intent = new Intent(this, Normall.class);
        startActivity(intent);
    }
}