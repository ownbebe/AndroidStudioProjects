package com.example.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sqlitetest.StudentContract.StudentEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.*;

public class StudentDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Student.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StudentContract.StudentEntry.TABLE_NAME + " (" +
                    StudentContract.StudentEntry._ID + " INTEGER PRIMARY KEY," +
                    StudentContract.StudentEntry.COLUMN_NAME + " TEXT," +
                    StudentContract.StudentEntry.COLUMN_LAST_NAME + " TEXT," +
                    StudentContract.StudentEntry.COLUMN_GRADE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StudentContract.StudentEntry.TABLE_NAME;

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void createTable(SQLiteDatabase db) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + StudentContract.StudentEntry.TABLE_NAME + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() == 0) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        cursor.close();
    }


    public void deleteTable(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentEntry._ID, student.getId());
        values.put(StudentContract.StudentEntry.COLUMN_NAME, student.getName());
        values.put(StudentContract.StudentEntry.COLUMN_LAST_NAME, student.getLastName());
        values.put(StudentContract.StudentEntry.COLUMN_GRADE, student.getGrade());

        db.insert(StudentContract.StudentEntry.TABLE_NAME, null, values);
    }

    public boolean updateStudent(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(StudentContract.StudentEntry.COLUMN_NAME, student.getName());
        values.put(StudentContract.StudentEntry.COLUMN_LAST_NAME, student.getLastName());
        values.put(StudentContract.StudentEntry.COLUMN_GRADE, student.getGrade());

        String selection = StudentContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(student.getId()) };

        db.update(StudentContract.StudentEntry.TABLE_NAME, values, selection, selectionArgs);
        return true;
    }

    public boolean deleteStudent(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String selection = StudentContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.delete(StudentContract.StudentEntry.TABLE_NAME, selection, selectionArgs);
        return true;
    }

    public Student getStudentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                StudentContract.StudentEntry._ID,
                StudentContract.StudentEntry.COLUMN_NAME,
                StudentContract.StudentEntry.COLUMN_LAST_NAME,
                StudentContract.StudentEntry.COLUMN_GRADE
        };

        String selection = StudentContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = { Integer.toString(id) };

        Cursor cursor = db.query(
                StudentContract.StudentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Student student = null;

        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_LAST_NAME));
            int grade = cursor.getInt(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_GRADE));
            student = new Student(id, name, lastName, grade);
        }

        cursor.close();
        db.close();

        return student;
    }


    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                StudentContract.StudentEntry._ID,
                StudentContract.StudentEntry.COLUMN_NAME,
                StudentContract.StudentEntry.COLUMN_LAST_NAME,
                StudentContract.StudentEntry.COLUMN_GRADE
        };

        String sortOrder = StudentContract.StudentEntry.COLUMN_LAST_NAME + " DESC";

        Cursor cursor = db.query(
                StudentContract.StudentEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(StudentContract.StudentEntry._ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_LAST_NAME));
            int grade = cursor.getInt(cursor.getColumnIndexOrThrow(StudentContract.StudentEntry.COLUMN_GRADE));

            Student student = new Student(id, firstName, lastName, grade);
            students.add(student);
        }

        cursor.close();

        return students;
    }
    public boolean studentExists(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = { StudentContract.StudentEntry._ID };

        String selection = StudentContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
                StudentContract.StudentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return exists;
    }

}


