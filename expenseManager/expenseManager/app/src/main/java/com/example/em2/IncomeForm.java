package com.example.em2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class IncomeForm extends AppCompatActivity {
    ArrayAdapter<CharSequence> adapter;
    String date, type, amount, note,d,m;
    EditText note1, amount1;
    DatePicker simpleDatePicker;
    Button add;
    Context CTX = this;
    Handler myHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_form);

        simpleDatePicker = (DatePicker) findViewById(R.id.simpleDatePicker);
        Calendar c = Calendar.getInstance();

        simpleDatePicker.setMaxDate(new Date().getTime());

        adapter = ArrayAdapter.createFromResource(this, R.array.incomeType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sp1 = (Spinner) findViewById(R.id.typeInc);
        sp1.setAdapter(adapter);

        amount1 = (EditText) findViewById(R.id.amount);
        note1 = (EditText) findViewById(R.id.note);


        add = (Button) findViewById(R.id.add);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int dayd = simpleDatePicker.getDayOfMonth();
                int monthd = simpleDatePicker.getMonth();
                int yeard = simpleDatePicker.getYear();

                if(dayd<10)
                {
                    d="0"+dayd;
                }
                else{
                    d=String.valueOf(dayd);
                }
                if(monthd<9)
                {
                    m="0"+String.valueOf(monthd + 1);
                    date = String.valueOf(yeard) + "-" + m + "-" + d;
                }
                else {
                    System.out.println("i am heare=========");
                date = String.valueOf(yeard) + "-" + String.valueOf(monthd + 1) + "-" + d;
                }

                amount = amount1.getText().toString();
                note = note1.getText().toString();
                Log.d("this is value of amount" + amount, " note " + note + " date " + date + " type " + type + " ");
                SaveIncome saveIncome = new SaveIncome();
                saveIncome.execute(date, amount, type, note);
            }
        });

    }

    public class SaveIncome extends AsyncTask<String, Void, String> {

        String b;
        Integer Balance;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            DatabaseOperations databaseOperations = new DatabaseOperations(CTX);


            String Date = params[0];
            int Amount = Integer.parseInt(params[1]);
            String Type = params[2];
            String Note = params[3];

            SQLiteDatabase db = databaseOperations.getWritableDatabase();
            databaseOperations.addIncome(db, Date, Amount, Type, Note);

            Cursor cr = databaseOperations.getInformationBalance(databaseOperations);
            if (cr.isBeforeFirst()) {
                int count = cr.getCount();
                System.out.println("inside if============================Count " + count);
                while (cr.moveToNext()) {
                    b = cr.getString(0);
                }
            }
            ;
            Balance = Integer.parseInt(b);

            Balance = Balance + Amount;

            puDialog(Date,Balance,Amount,Type,Note);
            databaseOperations.updateBalance(databaseOperations, Balance);
            return "one entry added";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(CTX, result, Toast.LENGTH_LONG).show();
        }


    }

    public void puDialog(final String Date, final Integer Balance, final Integer amount, final String type, final String note) {

        myHandler.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(CTX);
                dialog.setContentView(R.layout.entrydialog);
                dialog.setTitle("Entry Details!!!");

                // set the custom dialog components - text, image and button
                TextView text1 = (TextView) dialog.findViewById(R.id.text1);
                text1.setText("Date: " + Date);

                TextView text0 = (TextView) dialog.findViewById(R.id.text0);
                text0.setText(" Amount Added: " + amount);

                TextView text2 = (TextView) dialog.findViewById(R.id.text2);
                text2.setText("Income Type: " + type);

                TextView text3 = (TextView) dialog.findViewById(R.id.text3);
                text3.setText("Note: " + note);

                TextView text4 = (TextView) dialog.findViewById(R.id.text4);
                text4.setText("Current Balance: " + Balance);


                Button dialogButton = (Button) dialog.findViewById(R.id.button1);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(CTX, mainPage.class);
                        startActivity(in);
                    }
                });

                dialog.show();

            }
        });

    }
}
