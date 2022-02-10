package com.example.em2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class mainPage extends AppCompatActivity {
    Button b1,b4;
    ImageButton b2,b3;
    Context CTX=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        b1 = (Button) findViewById(R.id.logout);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CTX,HomeActivity.class);
                startActivity(intent);
            }
        });
        b2 = (ImageButton) findViewById(R.id.expense);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CTX,ExpenseForm.class);
                startActivity(intent);
            }
        });
        b3 = (ImageButton) findViewById(R.id.income);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CTX,IncomeForm.class);
                startActivity(intent);
            }
        });
        b4 = (Button) findViewById(R.id.stats);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CTX,StatsHome.class);
                startActivity(intent);
            }
        });
    }
}
