package com.example.em2;

/**
 * Created by hp on 04-04-2017.
 */

public final  class expenseContract {
    expenseContract(){}

    public static  abstract class expenseEntry
    {
       public  static final String AMOUNT="amount";
       public static final String NOTE="note";
       public static final String TYPE="type";
       public static final String DATE="expensedate";
       public static final String TABLE_NAME="expense_entry";

    }
}
