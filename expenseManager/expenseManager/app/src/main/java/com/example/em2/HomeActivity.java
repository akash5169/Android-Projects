package com.example.em2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Button signin,reset;
    EditText USERNAME, USERPASS;
    boolean success;
    String z;
    Context CTX=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        signin=(Button)findViewById(R.id.signin);

        USERNAME=(EditText)findViewById(R.id.username);

        USERPASS=(EditText)findViewById(R.id.password);

        signin.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                if (USERNAME.getText().toString().trim().equals("") || USERPASS.getText().toString().trim().equals("")) {
                    z = "Please enter all fields....";
                    success=false;
                }
                else if (USERNAME.getText().toString().equals("abc") && USERPASS.getText().toString().equals("abc")) {
                    success=true;
                }
                else{
                    z="wrong username or password";
                    Log.d("Vaules "," "+USERNAME.getText().toString()+" "+USERPASS.getText().toString());
                    success=false;
                }

                if(success)
                {
                    Intent intent=new Intent(CTX,mainPage.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(),""+z+" "+USERNAME.getText().toString()+" "+USERPASS.getText().toString(),Toast.LENGTH_LONG).show();
                    Log.d("Vaules "," "+USERNAME.getText().toString()+" "+USERPASS.getText().toString());
                }

            }
        });

        reset=(Button)findViewById(R.id.resetBtn);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseOperations databaseOperations=new DatabaseOperations(CTX);
                databaseOperations.reset(databaseOperations);
                Toast.makeText(getBaseContext(),"App is reset",Toast.LENGTH_LONG).show();
            }
        });

    }


}
