package com.example.emorocontrol;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

public class popUp extends Activity {
    ListView lista;
    BluetoothAdapter BtAdapter;
    TextView infor, adr;
    public static String address; // Postavljamo adresu u public string kako bismo mogli iz svih activity-a mogu dohvatiti adresu uređaja

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        // Lista i BtAdapter
        lista = (ListView) findViewById(R.id.Lista);
        BtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Bluetooth dohvaća uređaje i dodaje ih na listu koju ispisujemo
        ArrayList<String> list = new ArrayList<String>();
        Set<BluetoothDevice> pairedDevices = BtAdapter.getBondedDevices();
        for(BluetoothDevice bt: pairedDevices){
            list.add(bt.getName()+"\n"+bt.getAddress());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.row, list);
        // Listu uređaja koju smo dohvatili povezujemo sa listom u XML-u
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(customClickListener);

        // Prilagodba prozora da bude kao popup
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .9));


    }

    // Ovisno i pristisnutom uređaj daje nam njegovu adresu i postavlja je u public string te pokreće main activity
    public AdapterView.OnItemClickListener customClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object item = parent.getItemAtPosition(position);
            String info = item.toString();
            address = info.substring(info.length()-17);
            Intent intent = new Intent(popUp.this, MainActivity.class);
            intent.putExtra("StrValue", info);
            startActivity(intent);
        }
    };
}
