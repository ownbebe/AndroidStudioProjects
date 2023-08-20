package com.example.sqlite;

import android.provider.BaseColumns;

public final class StudentContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private StudentContract() {}

    /* Inner class that defines the table contents */
    public static class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME = "students";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_GRADE = "grade";
    }
}
