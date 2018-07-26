package com.example.abhay.floorcleaner;
import java.io.IOException;

import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ArduinoMain extends Activity {

    //Declare buttons & editText
    Button FWD,BCK,LFT,RGT,MPON,MPOFF,PUMP,S1,S2,S3,S4;

    //Memeber Fields
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    // UUID service - This is the type of Bluetooth device that the BT module is
    // It is very likely yours will be the same, if not google UUID for your manufacturer
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module
    public String newAddress = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino_main);

        //Initialising buttons in the view
        //mDetect = (Button) findViewById(R.id.mDetect);
        FWD = (Button) findViewById(R.id.FWD);
        BCK = (Button) findViewById(R.id.BCK);
        LFT = (Button) findViewById(R.id.LFT);
        RGT = (Button) findViewById(R.id.RGT);
        MPON = (Button) findViewById(R.id.MPON);
        MPOFF = (Button) findViewById(R.id.MPOFF);
        PUMP = (Button) findViewById(R.id.PUMP);
        S1 = (Button) findViewById(R.id.S1);
        S2 = (Button) findViewById(R.id.S2);
        S3 = (Button) findViewById(R.id.S3);
        S4 = (Button) findViewById(R.id.S4);

        //getting the bluetooth adapter value and calling checkBTstate function
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();

        /**************************************************************************************************************************8
         *  Buttons are set up with onclick listeners so when pressed a method is called
         *  In this case send data is called with a value and a toast is made
         *  to give visual feedback of the selection made
         */

        /*this part of code will create a new handler that checks state of pressed button every five hundred milliseconds
        * if the button will keep pressed that part of code will continuously sends the particular string to the connected
        * bluetooth adapter and it can also send the particular string if the button state is MotionUp */

        /* we can also create an object of such kind that will do our desirable work ,in case we want to save few lines in
         our code*/

        FWD.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(mHandler!= null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction,500);
                        sendData("F");
                        Toast.makeText(getBaseContext(), "Forward", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mHandler== null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        sendData("S");
                        break;
                }
                return false;
            }
                Runnable mAction = new Runnable() {
                    @Override
                    public void run() {
                        mHandler.postDelayed(this, 500);
                        sendData("F");
                    }
                };
        });

        BCK.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(mHandler!= null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction,500);
                        sendData("B");
                        Toast.makeText(getBaseContext(), "Back", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mHandler== null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        sendData("S");
                        break;
                }
                return false;
            }
            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    mHandler.postDelayed(this, 500);
                    sendData("B");
                }
            };
        });

       LFT.setOnTouchListener(new View.OnTouchListener() {
        private Handler mHandler;
        @Override public boolean onTouch(View v, MotionEvent event)
        {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(mHandler!= null) return true;
                    mHandler = new Handler();
                    mHandler.postDelayed(mAction,500);
                    sendData("L");
                    break;
                case MotionEvent.ACTION_UP:
                    if(mHandler== null) return true;
                    mHandler.removeCallbacks(mAction);
                    mHandler = null;
                    sendData("S");
                    Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
        Runnable mAction = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 500);
                sendData("L");
            }
        };
    });
        RGT.setOnTouchListener(new View.OnTouchListener() {
        private Handler mHandler;
        @Override public boolean onTouch(View v, MotionEvent event)
        {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if(mHandler!= null) return true;
                    mHandler = new Handler();
                    mHandler.postDelayed(mAction,500);
                    sendData("R");
                    Toast.makeText(getBaseContext(), "Right", Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_UP:
                    if(mHandler== null) return true;
                    mHandler.removeCallbacks(mAction);
                    mHandler = null;
                    sendData("S");
                    break;
            }
            return false;
        }
        Runnable mAction = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 500);
                sendData("R");
            }
        };
    });
        MPON.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(mHandler!= null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction,500);
                        sendData("M");
                        Toast.makeText(getBaseContext(), "Mopper On", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mHandler== null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }
            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    mHandler.postDelayed(this, 500);
                    sendData("M");
                }
            };
        });
        MPOFF.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(mHandler!= null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction,500);
                        sendData("m");
                        Toast.makeText(getBaseContext(), "Mopper Off", Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mHandler== null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }
            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    mHandler.postDelayed(this, 500);
                    sendData("m");
                }
            };
        });
        PUMP.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @Override public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(mHandler!= null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction,500);
                        sendData("P");
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mHandler== null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        sendData("p");
                        break;
                }
                return false;
            }
            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    mHandler.postDelayed(this, 500);
                    sendData("P");
                }
            };
        });
        S1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("1");
                Toast.makeText(getBaseContext(), "speed = 25", Toast.LENGTH_SHORT).show();
            }
        });
        S2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("2");
                Toast.makeText(getBaseContext(), "speed = 50", Toast.LENGTH_SHORT).show();
            }
        });
        S3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("3");
                Toast.makeText(getBaseContext(), "speed = 75", Toast.LENGTH_SHORT).show();
            }
        });
        S4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sendData("4");
                Toast.makeText(getBaseContext(), "speed = 100", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // connection methods are best here in case program goes into the background etc

        //Get MAC address from DeviceListActivity
        Intent intent = getIntent();
        newAddress = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        // Set up a pointer to the remote device using its address.
        BluetoothDevice device = btAdapter.getRemoteDevice(newAddress);

        //Attempt to create a bluetooth socket for comms
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e1) {
            Toast.makeText(getBaseContext(), "ERROR - Could not create Bluetooth socket", Toast.LENGTH_SHORT).show();
        }

        // Establish the connection.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();        //If IO exception occurs attempt to close socket
            } catch (IOException e2) {
                Toast.makeText(getBaseContext(), "ERROR - Could not close Bluetooth socket", Toast.LENGTH_SHORT).show();
            }
        }

        // Create a data stream so we can talk to the device
        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "ERROR - Could not create bluetooth outstream", Toast.LENGTH_SHORT).show();
        }
        //When activity is resumed, attempt to send a piece of junk data ('x') so that it will fail if not connected
        // i.e don't wait for a user to press button to recognise connection failure
        sendData("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Pausing can be the end of an app if the device kills it or the user doesn't open it again
        //close all connections so resources are not wasted

        //Close BT socket to device
        try     {
            btSocket.close();
        } catch (IOException e2) {
            Toast.makeText(getBaseContext(), "ERROR - Failed to close Bluetooth socket", Toast.LENGTH_SHORT).show();
        }
    }
    //takes the UUID and creates a comms socket
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    //same as in device list activity
    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "ERROR - Device does not support bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    // Method to send data
    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        try {
            //attempt to place data on the outstream to the BT device
            outStream.write(msgBuffer);
        } catch (IOException e) {
            //if the sending fails this is most likely because device is no longer there
            Toast.makeText(getBaseContext(), "ERROR - Device not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}