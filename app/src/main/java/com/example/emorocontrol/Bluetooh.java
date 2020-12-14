package com.example.emorocontrol;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Set;

public class Bluetooh extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    // Popup Window Lista
    TextView lista;
    // Bluetooth on/off switch
    SwitchCompat sBt;
    // Status tekst (Available/Unavailable)
    TextView btStat;
    // Bluetooth ikona (on/off)
    ImageView btIco;
    // Button za otkrivanje uređaja
    AppCompatButton discBtn;
    // Bluetooth adapter
    BluetoothAdapter BtAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooh);

        // Definiranje java varijabli za XML elemente
        btStat      = (TextView)findViewById(R.id.btStatus);
        btIco       = (ImageView)findViewById(R.id.btIco);
        discBtn     = (AppCompatButton)findViewById(R.id.discoverButt);
        sBt         = (SwitchCompat)findViewById(R.id.switchBlueT);
        //lista       = (TextView)findViewById(R.id.Lista);

        // Bluetooth adapter
        BtAdapter   = BluetoothAdapter.getDefaultAdapter();

        // Pri pokretanju aplikacije program provjerava bluetooth adapter
        if (BtAdapter.isEnabled()){
            btIco.setImageResource(R.drawable.bluetooth);
            sBt.setChecked(true);
        } else {
            btIco.setImageResource(R.drawable.bluetooth_no);
            sBt.setChecked(false);
        }

        // Provjerava status bluetooth-a
        if(BtAdapter == null){
            btStat.setText("Unavailable");
        } else {
            btStat.setText("Available");
        }

        // Bluetooth on/off switch
        sBt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!BtAdapter.isEnabled()){
                        // Ako adapter nije aktiviran
                        showToast("Turning on Bluetooth...");
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_ENABLE_BT);
                    } else {
                        // Ako je adapter vec aktiviran **ovaj slucaj je gotovo nemoguc
                        showToast("Bluetooth is already on...");
                    }
                } else {
                    // Iskljucuje adapter ako je aktiviran
                    if(BtAdapter.isEnabled()){
                        BtAdapter.disable();
                        showToast("Turning Bluetooth off...");
                        btIco.setImageResource(R.drawable.bluetooth_no);
                    } else {
                        // Iskljucuje adapter ako je aktiviran **ovaj slucaj je gotovo nemoguc
                        showToast("Bluetooth is already off...");
                    }
                }
            }
        });

        discBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!BtAdapter.isDiscovering()){
                    // Posavlja uređaj da bude vidljiv i počinje israživanje
                    showToast("Making your device discoverable!");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
                if(BtAdapter.isEnabled()){
                    // Otvara popup s popisom uređaja koji su dostupni
                    startActivity(new Intent(Bluetooh.this, popUp.class));
                } else {
                    // Ako bluetooth nije uključen daje upozorenje da se prvo uključi bluetooth
                    showToast("First turn on your Bluetooth!");
                }

            }
        });
    }

    private void showToast(String msg){ // ova funkcija samo olakšava stvaranje toast poruke
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                // Bluetooth is ON
                btIco.setImageResource(R.drawable.bluetooth);
                showToast("Bluetooth is ON");
            } else {
                // Upozorava da nije uspjelo uključivanje bluetootha
                showToast("Couldn't turn bluetooth ON");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}