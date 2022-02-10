package com.example.em2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class PieChartPage extends AppCompatActivity {

    Context CTX=this;
    TextView date1,date2;
    String d1="",d2="",d3,d4,d,m;
    int sum,temp;
    Button expPieBtn,incPieBtn;
    ArrayList<Integer> arrayList=new ArrayList<>();
    Map<String,Integer> hm=new HashMap();
    Map<String,Integer> hm1=new HashMap();
    Map<String,Float> hm2=new HashMap();
    Date date3,date4;
    PieChart pieChart;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_page);
        Button startDateBtn = findViewById(R.id.startDateBtn);
        date1 = findViewById(R.id.d1);
        Button endDateBtn = findViewById(R.id.endDateBtn);
        date2 = findViewById(R.id.d2);
        pieChart=(PieChart)findViewById(R.id.myPieChart);
        img=(ImageView)findViewById(R.id.img);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog1 = new DatePickerDialog(CTX,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date1.setText(day + "-" + (month + 1) + "-" + year);

                        if(day<10)
                        {
                            d="0"+day;
                        }
                        else{
                            d=String.valueOf(day);
                        }
                        if(month<9)
                        {
                            m="0"+String.valueOf(month + 1);
                            System.out.println("this was executed=======");
                            d1 = String.valueOf(year) + "-" + m + "-" + d;
                        }
                        else {
                            System.out.println("else was executed=======");
                            d1 = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + d;
                        }
                        d3=d1;
                    }
                }, year, month, dayOfMonth);

        final DatePickerDialog datePickerDialog2 = new DatePickerDialog(CTX,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date2.setText(day + "-" + (month + 1) + "-" + year);

                        if(day<10)
                        {
                            d="0"+day;
                        }
                        else{
                            d=String.valueOf(day);
                        }
                        if(month<9)
                        {
                            m="0"+String.valueOf(month + 1);
                            System.out.println("this was executed=======");
                            d2 = String.valueOf(year) + "-" + m + "-" + d;
                        }
                        else {
                            System.out.println("else was executed=======");
                            d2 = String.valueOf(year) + "-" + String.valueOf(month + 1) + "-" + d;
                        }
                        d4=d2;
                    }
                }, year, month, dayOfMonth);

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog1.show();
            }
        });

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog2.show();
            }
        });
        expPieBtn = (Button) findViewById(R.id.expPieBtn);
        incPieBtn = (Button) findViewById(R.id.incomePieBtn);


        expPieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseOperations databaseOperations = new DatabaseOperations(CTX);
                    if (d1.length() == 0 || d2.length() == 0) {
                        Toast.makeText(CTX, "Please select start and enddate", Toast.LENGTH_LONG).show();

                    } else {
                        date3 = new SimpleDateFormat("yyyy-MM-dd").parse(d1);
                        date4 = new SimpleDateFormat("yyyy-MM-dd").parse(d2);
                        if (date3.compareTo(date4) > 0) {
                            Toast.makeText(CTX, "Start date should be smaller than end date", Toast.LENGTH_LONG).show();
                        } else {
                            hm1.clear();
                            for (String type : getResources().getStringArray(R.array.expenceType)) {
                                Cursor cr = databaseOperations.getInformationExpencePie(databaseOperations, type, d3, d4);
                                int count = cr.getCount();
                                System.out.println("count====================" + count);
                                sum = 0;
                                if (count > 0) {
                                    if (cr.isBeforeFirst()) {
                                        while (cr.moveToNext()) {
                                            temp = Integer.parseInt(cr.getString(0));
                                            System.out.println("date from tanble"+cr.getString(1));
                                            sum = sum + temp;

                                        }
                                    }
                                    hm1.put(type, sum);
                                } else {

                                    hm1.put(type, 0);
                                }

                            }
                        }
                    }
                } catch (Exception ex) {
                    String z = "Exceptions" + ex;
                    Log.d("Exception:", z);
                }

                showPieCh(hm1,"Expense Chart");
                dispImg(0);
            }
        });

        incPieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    DatabaseOperations databaseOperations = new DatabaseOperations(CTX);
                    if (d1.length() == 0 || d2.length() == 0) {
                        Toast.makeText(CTX, "Please select start and enddate", Toast.LENGTH_LONG).show();
                    } else {
                        date3 = new SimpleDateFormat("yyyy-MM-dd").parse(d1);
                        date4 = new SimpleDateFormat("yyyy-MM-dd").parse(d2);
                        if (date3.compareTo(date4) > 0) {
                            Toast.makeText(CTX, "Start date should be smaller than end date", Toast.LENGTH_LONG).show();
                        } else {
                            hm.clear();
                            for (String type : getResources().getStringArray(R.array.incomeType)) {
                                Cursor cr = databaseOperations.getInformationIncomePie(databaseOperations, type, d3, d4);
                                int count = cr.getCount();
                                System.out.println("count===================="+count);
                                sum = 0;
                                if (count > 0) {
                                    if (cr.isBeforeFirst()) {
                                        while (cr.moveToNext()) {
                                            temp = Integer.parseInt(cr.getString(0));
                                            System.out.println("date from tanble"+cr.getString(1));
                                            sum = sum + temp;
                                        }
                                    }
                                    hm.put(type, sum);
                                } else {

                                    hm.put(type, 0);
                                }

                            }
                        }
                    }
                } catch (Exception ex) {
                    String z = "Exceptions" + ex;
                    Log.d("Exception:", z);
                }
                showPieCh(hm,"Income Chart");
                dispImg(1);
                /*System.out.println("created hash map is" + hm);*/
            }
        });

    }

    void showPieCh(Map map,String str)
    {
        Float sumOfAll=0.0f,temp;
        hm2.clear();
        ArrayList<String> type = new ArrayList<>();
        ArrayList<Float> values=new ArrayList<>();
        ArrayList<PieEntry> entry = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            temp=Float.valueOf(pair.getValue());
            sumOfAll=sumOfAll+temp;
        }

        System.out.println("sum of all-"+sumOfAll);

        Iterator<Map.Entry<String, Integer>> it2 = map.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it2.next();
            /*Map.Entry<String, Double> pair1 = (Map.Entry<String, Double>) it1.next();*/
            hm2.put(pair.getKey(),pair.getValue()/sumOfAll);
        }
        System.out.println("this is product set");
        Iterator<Map.Entry<String, Float>> it1 = hm2.entrySet().iterator();
        while (it1.hasNext()) {

            Map.Entry<String, Float> pair1 = (Map.Entry<String, Float>) it1.next();
            type.add(pair1.getKey());
            values.add(pair1.getValue());
            System.out.println(pair1.getKey() + " = " + pair1.getValue());
        }

        Description desc=new Description();
        desc.setText("Pie Chart");
        pieChart.setDescription(desc);
        pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText(str);
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!


        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        for(int i=0;i<hm2.size();i++)
        {
            entry.add(i,new PieEntry(values.get(i)));
        }

        PieDataSet pieDataSet = new PieDataSet(entry, "Pie Chart");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    void dispImg(int a)
    {
        if(a==0)
        {
            img.setImageResource(R.drawable.exp);
        }

        else{
            img.setImageResource(R.drawable.inc);
        }
    }
    }