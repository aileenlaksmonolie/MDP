package com.example.tutorial_java;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    BluetoothAdapter mBluetoothAdapter;

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);
                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create variable
        Switch bluetoothSwitch = (Switch) findViewById(R.id.bluetoothSwitch);

        //bluetooth Adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //Bluetooth on switch method
        bluetoothSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            Boolean switchState = bluetoothSwitch.isChecked();
            enableDisableBT(switchState);
            }
            });


            }

    public void enableDisableBT(Boolean switchState) {
        if(mBluetoothAdapter==null){
            Log.d(TAG,"enableDisableBT : does not have BT capabilities.");
        }

        if(switchState == Boolean.TRUE){
            mBluetoothAdapter.enable();
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
            Toast.makeText(getApplicationContext(), "Turned On",Toast.LENGTH_LONG).show();
        }
        else {
            mBluetoothAdapter.disable();
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
            Toast.makeText(getApplicationContext(), "Turned Off",Toast.LENGTH_LONG).show();
        }
    }


}



//button OnClick method
//    Button addButton = (Button) findViewById(R.id.addButton);
//        addButton.setOnClickListener(new View.OnClickListener(){
//        @Override
//        public void onClick(View view){
//            EditText firstNumEditText = (EditText) findViewById(R.id.firstNumEditText);
//            EditText secondNumEditText = (EditText) findViewById(R.id.secondNumEditText);
//            TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
//            int num1 = Integer.parseInt(firstNumEditText.getText().toString());
//            int num2 = Integer.parseInt(secondNumEditText.getText().toString());
//            int result= num1+num2;
//            resultTextView.setText(result + "");
//        }
//    });