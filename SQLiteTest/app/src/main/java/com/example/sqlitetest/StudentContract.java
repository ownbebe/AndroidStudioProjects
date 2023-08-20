package com.example.sqlitetest;

import android.provider.BaseColumns;

public final class StudentContract {

    private StudentContract() {}

    public static class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME = "students";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_GRADE = "grade";
        public static final String COLUMN_ID = "id";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StudentEntry.TABLE_NAME + " (" +
                    StudentEntry._ID + " INTEGER PRIMARY KEY," +
                    StudentEntry.COLUMN_NAME + " TEXT," +
                    StudentEntry.COLUMN_LAST_NAME + " TEXT," +
                    StudentEntry.COLUMN_GRADE + " INTEGER," +
                    StudentEntry.COLUMN_ID + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudentEntry.TABLE_NAME;
}


