package com.example.sqlite;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class StudentDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "students";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_LAST_NAME = "last_name";
    public static final String COL_GRADE = "grade";


    public StudentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE student (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "last_name TEXT, " +
                "grade INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS student");
        onCreate(db);
    }

    public void insertData(String name, String lastName, int grade) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("last_name", lastName);
        values.put("grade", grade);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("student", null, values);
        db.close();
    }

    public void updateData(int id, String name, String lastName, int grade) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_LAST_NAME, lastName);
        values.put(COL_GRADE, grade);

        db.update(TABLE_NAME, values, COL_ID + "=?", new String[] { String.valueOf(id) });
        db.close();
    }


    public void deleteData(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = { String.valueOf(id) };
        SQLiteDatabase db = getWritableDatabase();
        db.delete("student", whereClause, whereArgs);
        db.close();
    }

    public List<Student> selectAllData() {
        List<Student> students = new ArrayList<>();
        String[] columns = { "id", "name", "last_name", "grade" };
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("student", columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
            int grade = cursor.getInt(cursor.getColumnIndex("grade"));
            Student student = new Student(id, name, lastName, grade);
            students.add(student);
        }
        cursor.close();
        db.close();
        return students;
    }
    public Student selectData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COL_ID, COL_NAME, COL_LAST_NAME, COL_GRADE },
                COL_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));

            cursor.close();
            db.close();
            return student;
        }

        db.close();
        return null;
    }


}


