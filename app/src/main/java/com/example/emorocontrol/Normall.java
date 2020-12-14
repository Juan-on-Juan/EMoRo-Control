package com.example.emorocontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Normall extends AppCompatActivity {
    // Stvari za komunikaciju sa eMoro
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog progress;

    public ImageButton ul;
    public ImageButton u;
    public ImageButton ur;
    public ImageButton l;
    public ImageButton stop;
    public ImageButton r;
    public ImageButton bl;
    public ImageButton b;
    public ImageButton br;
    public TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ConnectBT().execute();
        setContentView(R.layout.activity_normall);

        ul      = (ImageButton)findViewById(R.id.ul);
        u       = (ImageButton)findViewById(R.id.u);
        ur      = (ImageButton)findViewById(R.id.ur);
        l       = (ImageButton)findViewById(R.id.l);
        stop    = (ImageButton)findViewById(R.id.stop2);
        r       = (ImageButton)findViewById(R.id.r);
        bl      = (ImageButton)findViewById(R.id.bl);
        b       = (ImageButton)findViewById(R.id.b);
        br      = (ImageButton)findViewById(R.id.br);
        text    = (TextView)findViewById(R.id.text);

        ul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("G".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("F".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        });
        ur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("I".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("L".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("S".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("R".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        });
        bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("H".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("B".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        });
        br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    btSocket.getOutputStream().write("J".toString().getBytes());
                }
                catch (IOException e)
                {
                    msg("Error");
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
            progress = ProgressDialog.show(Normall.this, "Connecting...", "Please wait!!!");
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
