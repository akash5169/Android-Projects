package com.example.cs478.a2app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TOAST_INTENT =
            "com.example.cs478.a2app.myRequest";

    BroadcastReceiver mReceiver = new IntentReceiver() ;
    IntentFilter mFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onResume() {
        super.onResume() ;

        mFilter=new IntentFilter(TOAST_INTENT) ;
        mFilter.setPriority(1);
        registerReceiver(mReceiver, mFilter);
        System.out.println("====I registered receiver===");

    }
    public void onDestroy() {
        super.onDestroy();
        System.out.println("====I destroyed receiver===");
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.attraction) {
            Intent intent = new Intent(this, AttractionsActivity.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.restaurant) {
            Intent intent = new Intent(this, RestaurantActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.option_menu,menu);
        return true;

    }
}