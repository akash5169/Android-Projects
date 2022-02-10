package com.example.em2;

import android.provider.BaseColumns;

/**
 * Created by hp on 12-03-2017.
 */

public class TableData {
    public TableData()
    {

    }

    public  static abstract class TableInfo implements BaseColumns
    {

        public static final String DATABASE_NAME="user_info";
        public static final String BAL_NAME="Table_Balance";
        public static final String BALANCE="balance";
        public static final String DATE="date";
    }
}
