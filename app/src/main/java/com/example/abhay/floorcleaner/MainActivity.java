package com.example.abhay.floorcleaner;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*this part of code will create a new handler that,
         handles a splash screen and delay activity_main.xml for five hundred milliseconds seconds*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent DeviceList = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(DeviceList);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
