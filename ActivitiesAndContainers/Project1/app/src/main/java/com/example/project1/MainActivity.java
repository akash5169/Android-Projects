package com.example.project1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button2;

    ActivityResultLauncher<Intent> startSecondActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                //this is where the result is returned and the logic of what to do with it goes
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.i("MainActivity: ", "Returned result is: " + result.getResultCode()) ;

                    if (result.getResultCode()==-1){
                        button2.setEnabled(true);

                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(),"I was clicked",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Intent.ACTION_INSERT);
                                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                                intent.putExtra(ContactsContract.Intents.Insert.NAME, result.getData().getExtras().getString("enteredString"));

                                //+result.getData().getExtras().getString("enteredString")
                                if(intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                    else{
                        button2.setEnabled(false);
                        Log.i("MainActivity",result.getData().getExtras().getString("enteredString"));
                        Toast.makeText(getApplicationContext(),"Invalid name entered -"+result.getData().getExtras().getString("enteredString"), Toast.LENGTH_LONG).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1=(Button) findViewById(R.id.button1);
        button2=(Button) findViewById(R.id.button2);

        //second button is disabled. It will be enabled after we receive valid name from second activity
        button2.setEnabled(false);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,SecondActivity.class);
                startSecondActivity.launch(i);
            }
        });


    }

}