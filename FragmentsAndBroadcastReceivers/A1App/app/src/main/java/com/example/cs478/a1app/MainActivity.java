package com.example.cs478.a1app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Button attractionBtn ;
    private Button restBtn ;
    private String str="";

    private static final String TOAST_INTENT =
            "com.example.cs478.a2app.myRequest";

    private static final String UGOS_PERMISSION =
            "edu.uic.cs478.fall2021.project3" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attractionBtn = (Button) findViewById(R.id.attract_btn) ;
        attractionBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                str="attractions";
                checkPermissionAndBroadcast();

            }
        }) ;



        restBtn = (Button) findViewById(R.id.restro_btn) ;
        restBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                str="restaurant";
                checkPermissionAndBroadcast();

            }
        }) ;
    }

    private void checkPermissionAndBroadcast() {
        if (ActivityCompat.checkSelfPermission(this, UGOS_PERMISSION) == PackageManager.PERMISSION_GRANTED)
        {
            System.out.println("I already have permission============");
            Intent aIntent = new Intent(TOAST_INTENT) ;
            aIntent.putExtra("RequestedActivity", str);
            sendBroadcast(aIntent) ;
        }
        else {
            System.out.println("===========I tried to request permission===========");
            ActivityCompat.requestPermissions(this, new String[]{UGOS_PERMISSION}, 0);
        }

    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(code,permissions,results);
        System.out.println("=====But the results were==="+results.length);
        if (results.length > 0) {
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                Intent aIntent = new Intent(TOAST_INTENT) ;
                aIntent.putExtra("RequestedActivity", str);
                sendBroadcast(aIntent)  ;
                System.out.println("====Sent a broadcast====");
            }
            else {
                Toast.makeText(this, "Bummer: No permission", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}