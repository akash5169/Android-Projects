package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SecondActivity extends AppCompatActivity {
    EditText editText;
    Button d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editText =(EditText)findViewById(R.id.editText);

        d=(Button) findViewById(R.id.done);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endSecondActivity();
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) ) {
                    // Perform action on key press
                    if (keyCode == KeyEvent.KEYCODE_ENTER ) {
                        // Perform action on key press
                        endSecondActivity();
                        return true;
                    }
                }



                return false;
            }
        });

    }
    protected void endSecondActivity()
    {

        String str=editText.getText().toString();
        String s=str;
        int result=RESULT_CANCELED;

        if(s.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please type something then press enter",Toast.LENGTH_SHORT).show();
        }
        else{
            s=s.replaceAll("\\s{2,}", " ").trim();

            CharSequence inputStr = s;
            Pattern pattern = Pattern.compile(new String ("^[a-zA-Z\\s]*$"));
            Matcher matcher = pattern.matcher(inputStr);
            if(matcher.matches())
            {
                String arr[]=s.split(" ");
                if (arr.length<2 || arr.length>3)
                {
                    Toast.makeText(getApplicationContext(),"Name should have at least 2 or at most 3 words",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    result=RESULT_OK;
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Name should have only alphabets and spaces",Toast.LENGTH_SHORT).show();

            }
        }
        Intent data = new Intent();
        data.putExtra("enteredString", str);

        // Activity finished ok, return the data
        setResult(result, data);
        finish();
    }
}