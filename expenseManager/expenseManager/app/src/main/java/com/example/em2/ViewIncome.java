package com.example.em2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewIncome extends AppCompatActivity {

    Context CTX=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income);

        TableLayout table = (TableLayout) findViewById(R.id.table1);
        List<String> arrayList=new ArrayList<String>();
        List<String> arrayList2=new ArrayList<String>();
        List<String> arrayList3=new ArrayList<String>();
        List<String> arrayList4=new ArrayList<String>();
        arrayList.add("Date");
        arrayList2.add("Amount");
        arrayList3.add("Type");
        arrayList4.add("Note");
        try {

            DatabaseOperations databaseOperations=new DatabaseOperations(CTX);
            Cursor cr=databaseOperations.getInformationIncome(databaseOperations);

       /* while (cr.moveToNext()) {
            String Date = cr.getString(0);
            String Amount = cr.getString(1);
            String Type = cr.getString(2);
            String Note = cr.getString(3);

            System.out.println("Date "+Date+" Amount"+Amount+" Type"+Type+" Note"+Note);
        }*/
            if (cr.isBeforeFirst()) {

                while (cr.moveToNext()) {
                    String Date = cr.getString(1);
                    String Amount= cr.getString(2);
                    String Type = cr.getString(3);
                    String Note = cr.getString(4);
                    System.out.println("Date "+Date+" Amount"+Amount+" Type"+Type+" Note"+Note);
                    arrayList.add(Date);
                    arrayList2.add(Amount);
                    arrayList3.add(Type);
                    arrayList4.add(Note);
                }
            }
        }catch (Exception ex)
        {
            String  z = "Exceptions" + ex;
            Log.d("Exception:",z);
        }
        String Date,Amount,Type,Note;
        TextView date1,amount1,type1,note1;

        for(int i=0;i<arrayList.size();i++)
        {
            TableRow row=new TableRow(this);

            if(i==0) {
                Date = arrayList.get(i);
                Amount = arrayList2.get(i);
                Type = arrayList3.get(i);
                Note = arrayList4.get(i);

                date1 = new TextView(this);
                date1.setText("" + Date);
                date1.setBackgroundResource(R.drawable.b2);
                date1.setTextColor(Color.parseColor("#FF0000"));
                date1.setTextSize(20);

                amount1 = new TextView(this);
                amount1.setText("" + Amount);
                amount1.setTextSize(20);
                amount1.setBackgroundResource(R.drawable.b2);
                amount1.setTextColor(Color.parseColor("#0000FF"));

                type1 = new TextView(this);
                type1.setText("" + Type);
                type1.setTextSize(20);
                type1.setTextColor(Color.parseColor("#00FF00"));
                type1.setBackgroundResource(R.drawable.b2);

                note1 = new TextView(this);
                note1.setText("" + Note);
                note1.setTextSize(20);
                note1.setTextColor(Color.parseColor("#A0FF0A"));
                note1.setBackgroundResource(R.drawable.b2);

                row.addView(date1);
                row.addView(amount1);
                row.addView(type1);
                row.addView(note1);
            }
            else
            {
                Date = arrayList.get(i);
                Amount = arrayList2.get(i);
                Type = arrayList3.get(i);
                Note = arrayList4.get(i);
                date1 = new TextView(this);
                date1.setText("" + Date);
                date1.setBackgroundResource(R.drawable.b2);
                date1.setTextSize(20);

                amount1 = new TextView(this);
                amount1.setText("" + Amount);
                amount1.setTextSize(20);
                amount1.setBackgroundResource(R.drawable.b2);


                type1 = new TextView(this);
                type1.setText("" + Type);
                type1.setTextSize(20);
                type1.setBackgroundResource(R.drawable.b2);

                note1 = new TextView(this);
                note1.setText("" + Note);
                note1.setTextSize(20);
                note1.setBackgroundResource(R.drawable.b2);

                row.addView(date1);
                row.addView(amount1);
                row.addView(type1);
                row.addView(note1);

            }
            table.addView(row);
            table.setStretchAllColumns(true);
        }

    }
}
