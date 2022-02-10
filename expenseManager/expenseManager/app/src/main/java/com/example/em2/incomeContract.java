package com.example.em2;

/**
 * Created by hp on 04-04-2017.
 */

public class incomeContract {
    incomeContract(){}

    public static  abstract class incomeEntry
    {
        public  static final String AMOUNT="amount";
        public static final String NOTE="note";
        public static final String TYPE="type";
        public static final String DATE="entrydate";
        public static final String TABLE_NAME="income_entry";

    }
}
