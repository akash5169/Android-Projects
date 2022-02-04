package com.example.cs478.a2app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class IntentReceiver extends BroadcastReceiver {
    private String st1 = "attractions";
    private String str2 = "restaurant";
    String newString;


    @Override
    public void onReceive(Context con, Intent arg) {
        // TODO Auto-generated method stub
        //Gets the extras of intent passed

        System.out.println("I received a intent======================");

//        if (con.checkCallingPermission("edu.uic.cs478.fall2021.project3")
//                == PackageManager.PERMISSION_GRANTED) {

            Bundle extras = arg.getExtras();
            if (extras != null) {

                newString = extras.getString("RequestedActivity");
            }

            if (newString.equalsIgnoreCase(st1)) {
                Toast.makeText(con, "Opening Attractions",
                        Toast.LENGTH_LONG).show();


                Intent intent = new Intent(con, AttractionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                con.startActivity(intent);
            } else {
                Toast.makeText(con, "Opening Restaurants",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(con, RestaurantActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                con.startActivity(intent);

            }

//        }
//        else
//            {
//        Toast.makeText(con, "Receiving app says : No permission", Toast.LENGTH_SHORT)
//                .show(); }
}
}
