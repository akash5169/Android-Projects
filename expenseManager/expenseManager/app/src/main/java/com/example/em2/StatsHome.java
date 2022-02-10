package com.example.em2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatsHome extends AppCompatActivity {
Button b1,b2,b3,b4;
TextView balance;
    String Balance;
    Context CTX=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_home);

        balance=findViewById(R.id.balance);

        try {

            DatabaseOperations databaseOperations=new DatabaseOperations(CTX);
            Cursor cr=databaseOperations.getInformationBalance(databaseOperations);

            if (cr.isBeforeFirst()) {
                int count=cr.getCount();

                while (cr.moveToNext()) {
                  Balance = cr.getString(0);
                }
            }
        }catch (Exception ex)
        {
            String  z = "Exceptions" + ex;
            Log.d("Exception:",z);
        }
        balance.setText(Balance);
        b1 = (Button) findViewById(R.id.expenseBtn);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CTX,ViewIncome.class);
                startActivity(intent);
            }
        });
        b2 = (Button) findViewById(R.id.incomeBtn);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CTX,ViewExpense.class);
                startActivity(intent);
            }
        });

        b3=(Button) findViewById(R.id.grapBtn);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CTX,PieChartPage.class);
                startActivity(intent);
            }
        });
    }
}
