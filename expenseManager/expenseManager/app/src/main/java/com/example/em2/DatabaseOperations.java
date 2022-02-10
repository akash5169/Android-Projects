package com.example.em2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.em2.TableData.TableInfo;

/**
 * Created by hp on 12-03-2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version=1;

    public String CREATE_QUERY1="CREATE TABLE "+expenseContract.expenseEntry.TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+expenseContract.expenseEntry.DATE+" date,"+expenseContract.expenseEntry.AMOUNT +" integer,"+expenseContract.expenseEntry.TYPE+" text,"+expenseContract.expenseEntry.NOTE+" text);";
    public String CREATE_QUERY2="CREATE TABLE "+incomeContract.incomeEntry.TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+incomeContract.incomeEntry.DATE+" date,"+incomeContract.incomeEntry.AMOUNT +" integer,"+incomeContract.incomeEntry.TYPE+" text,"+incomeContract.incomeEntry.NOTE+" text);";
    public String CREATE_QUERY="CREATE TABLE "+TableData.TableInfo.BAL_NAME+"(ID INTEGER,"+ TableInfo.BALANCE +" integer);";
    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null,database_version);
        Log.d("Databse operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_QUERY);
        Log.d("Databse operations", "BalanceTable created");
     sdb.execSQL(CREATE_QUERY1);
        Log.d("Databse operations", "ExpenseTable created");
     sdb.execSQL(CREATE_QUERY2);
        Log.d("Databse operations", "IncomeTable created");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       onCreate(db);
    }


    public  void addExpense(SQLiteDatabase sdb, String date, Integer amount, String type, String note)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(expenseContract.expenseEntry.DATE,date);
        contentValues.put(expenseContract.expenseEntry.AMOUNT,amount);
        contentValues.put(expenseContract.expenseEntry.TYPE,type);
        contentValues.put(expenseContract.expenseEntry.NOTE,note);
        sdb.insert(expenseContract.expenseEntry.TABLE_NAME,null,contentValues);
        Log.d("Databse operations", "One Row Inserted...");
    }
    public Cursor getInformationExpence(DatabaseOperations dop)
    {
        SQLiteDatabase SQ= dop.getReadableDatabase();
        String[] coloumns={"ID",expenseContract.expenseEntry.DATE,expenseContract.expenseEntry.AMOUNT,expenseContract.expenseEntry.TYPE,expenseContract.expenseEntry.NOTE};
        String orderBy=expenseContract.expenseEntry.DATE;
        Cursor CR=SQ.query(expenseContract.expenseEntry.TABLE_NAME,coloumns,null,null,null,null,orderBy);
        return CR;
    }
    public Cursor getInformationIncome(DatabaseOperations dop)
    {
        SQLiteDatabase SQ= dop.getReadableDatabase();
        String orderBy=incomeContract.incomeEntry.DATE;
        String[] coloumns={"ID",incomeContract.incomeEntry.DATE,incomeContract.incomeEntry.AMOUNT,expenseContract.expenseEntry.TYPE,incomeContract.incomeEntry.NOTE};
        Cursor CR=SQ.query(incomeContract.incomeEntry.TABLE_NAME,coloumns,null,null,null,null,orderBy);
        return CR;
    }

    public Cursor getInformationBalance(DatabaseOperations dop)
    {
        SQLiteDatabase SQ= dop.getReadableDatabase();
        String[] coloumns={TableInfo.BALANCE};
        Cursor CR=SQ.query(TableInfo.BAL_NAME,coloumns,null,null,null,null,null);
        System.out.println("This was called===========================");

        return CR;
    }


    public String updateBalance(DatabaseOperations dop,Integer Bal)
    {
        SQLiteDatabase SQ= dop.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        System.out.println("This was called update balance===========================");
        contentValues.put(TableInfo.BALANCE,Bal);
        SQ.update(TableInfo.BAL_NAME,contentValues,"ID=?",new String[]{"1"});
        return "Balance Updated";
    }

    public Cursor getInformationIncomePie(DatabaseOperations dop,String type,String startDate, String endDate)
    {
        SQLiteDatabase SQ= dop.getReadableDatabase();
        String[] coloumns={incomeContract.incomeEntry.AMOUNT,incomeContract.incomeEntry.DATE};
        /*String selection=incomeContract.incomeEntry.TYPE+" =? and "+incomeContract.incomeEntry.DATE+" between ?"+" and "+" ?";*/
        String selection=incomeContract.incomeEntry.TYPE+" LIKE ? and "+incomeContract.incomeEntry.DATE+" between '"+startDate+"' and '"+endDate+"'";
        System.out.println("Type: "+type+" Start Date: "+startDate+" End Date:"+endDate);
        String selection_args[]={type};
        String orderBy=incomeContract.incomeEntry.DATE;

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(incomeContract.incomeEntry.TABLE_NAME);
        String sql = qb.buildQuery(coloumns,selection, null, null, null, null);
        Log.d("Example", sql);
        /*System.out.println("=============start date in DBOPS "+startDate+" end date "+endDate+" type "+type);*/
        /*Cursor CR1=SQ.rawQuery("select "+incomeContract.incomeEntry.AMOUNT+" from "+incomeContract.incomeEntry.TABLE_NAME+" where type=? and "+incomeContract.incomeEntry.DATE+" between ? and ?",selection_args);*/
        /*Cursor CR=SQ.query(incomeContract.incomeEntry.TABLE_NAME,coloumns,selection,selection_args,null,null,null);*/
          Cursor CR=SQ.query(incomeContract.incomeEntry.TABLE_NAME,coloumns,selection,selection_args,null,null,null);
          return CR;
    }

    public Cursor getInformationExpencePie(DatabaseOperations dop,String type,String startDate, String endDate)
    {
        SQLiteDatabase SQ= dop.getReadableDatabase();
        String[] col={expenseContract.expenseEntry.AMOUNT,expenseContract.expenseEntry.DATE};
        /*System.out.println("===============start date in DBOPS "+startDate+" end date"+endDate+" type "+type);*/
       /* String selection=expenseContract.expenseEntry.TYPE+" =? and "+expenseContract.expenseEntry.DATE+" between ?"+" and "+" ?";*/
        /*String selection=expenseContract.expenseEntry.TYPE+" LIKE ? and "+expenseContract.expenseEntry.DATE+" between '"+startDate+"' and '"+endDate+"'";*/
        String selection=expenseContract.expenseEntry.TYPE+" LIKE ? and "+expenseContract.expenseEntry.DATE+" between '"+startDate+"' and '"+endDate+"'";
        String selection_args[]={type};
        /*System.out.println("Type: "+type+" Start Date: "+startDate+" End Date:"+endDate);*/
        String orderBy=expenseContract.expenseEntry.DATE;
        /*Cursor CR1=SQ.rawQuery("select "+expenseContract.expenseEntry.AMOUNT+" from "+expenseContract.expenseEntry.TABLE_NAME+" where type=? and "+expenseContract.expenseEntry.DATE+" between ? and ?",selection_args);*/
       /* Cursor CR=SQ.query(expenseContract.expenseEntry.TABLE_NAME,col,selection,selection_args,null,null,null);*/
        Cursor CR=SQ.query(expenseContract.expenseEntry.TABLE_NAME,col,selection,selection_args,null,null,null);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(expenseContract.expenseEntry.TABLE_NAME);
        String sql = qb.buildQuery(col,selection, null, null, null, null);
        Log.d("Example", sql);
        return CR;
    }
    public  void addIncome(SQLiteDatabase sdb, String date, Integer amount, String type, String note)
    {
        System.out.println("===================================in add income date "+date+" amount "+amount+" type "+type+" note "+note);
        ContentValues contentValues = new ContentValues();
        contentValues.put(incomeContract.incomeEntry.DATE,date);
        contentValues.put(incomeContract.incomeEntry.AMOUNT,amount);
        contentValues.put(incomeContract.incomeEntry.TYPE,type);
        contentValues.put(incomeContract.incomeEntry.NOTE,note);

        sdb.insert(incomeContract.incomeEntry.TABLE_NAME,null,contentValues);

        Log.d("Databse operations", "============================One Row Inserted...");

    }

    public void reset(DatabaseOperations dop)
    {
        SQLiteDatabase sdb= dop.getReadableDatabase();
         sdb.execSQL("DROP TABLE IF EXISTS "+expenseContract.expenseEntry.TABLE_NAME);
        Log.d("Databse operations", "ExpenseTable dropped");
        sdb.execSQL("DROP TABLE IF EXISTS "+incomeContract.incomeEntry.TABLE_NAME);
        Log.d("Databse operations", "IncomeTable dropped");
        sdb.execSQL("DROP TABLE IF EXISTS "+TableData.TableInfo.BAL_NAME);
        Log.d("Databse operations", "BalanceTable dropped");


        sdb.execSQL(CREATE_QUERY);
        Log.d("Databse operations", "BalanceTable created");
        sdb.execSQL(CREATE_QUERY1);
        Log.d("Databse operations", "ExpenseTable created");
        sdb.execSQL(CREATE_QUERY2);
        Log.d("Databse operations", "IncomeTable created");


        ContentValues contentValues1=new ContentValues();
        contentValues1.put("ID","1");
        contentValues1.put(TableInfo.BALANCE,"0");
        sdb.insert(TableInfo.BAL_NAME,null,contentValues1);
        Log.d("Databse operations", "Balance add ====================================================");
    }

}
