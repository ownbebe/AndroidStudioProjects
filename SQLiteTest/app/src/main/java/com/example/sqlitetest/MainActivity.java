package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;


public class MainActivity extends AppCompatActivity {
    private EditText nameEditText, lastNameEditText, gradeEditText, idEditText;
    private StudentDbHelper studentDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.name_edittext);
        lastNameEditText = findViewById(R.id.last_name_edittext);
        gradeEditText = findViewById(R.id.grade_edittext);
        idEditText = findViewById(R.id.id_edittext);
        studentDatabaseHelper = new StudentDbHelper(this);
    }

    public void onAddDataButtonClick(View view) {
        String name = nameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String gradeStr = gradeEditText.getText().toString();
        String idStr = idEditText.getText().toString();

        if (name.isEmpty() || lastName.isEmpty() || gradeStr.isEmpty() || idStr.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int grade = Integer.parseInt(gradeStr);
        int id = Integer.parseInt(idStr);

        if (studentDatabaseHelper.studentExists(id)) {
            Toast.makeText(this, "Student with this ID already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(id, name, lastName, grade);
        studentDatabaseHelper.addStudent(student);

        Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show();

        nameEditText.setText("");
        lastNameEditText.setText("");
        gradeEditText.setText("");
        idEditText.setText("");
    }

    public void onViewAllButtonClick(View view) {
        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);
    }

    public void onSearchButtonClick(View view) {
        EditText idEditText = findViewById(R.id.id_edittext);
        int studentId = Integer.parseInt(idEditText.getText().toString());

        // Query the database based on the student ID
        StudentDbHelper dbHelper = new StudentDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                StudentContract.StudentEntry._ID,
                StudentContract.StudentEntry.COLUMN_NAME,
                StudentContract.StudentEntry.COLUMN_LAST_NAME,
                StudentContract.StudentEntry.COLUMN_GRADE
        };

        String selection = StudentContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(studentId) };

        Cursor cursor = db.query(
                StudentContract.StudentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // If the cursor has a result, create a new Student object and pass its ID to the UpdateOrDeleteActivity
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(StudentContract.StudentEntry._ID);
            int firstNameIndex = cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_NAME);
            int lastNameIndex = cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_LAST_NAME);
            int gradeIndex = cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_GRADE);

            int id = cursor.getInt(idIndex);
            String firstName = cursor.getString(firstNameIndex);
            String lastName = cursor.getString(lastNameIndex);
            int grade = cursor.getInt(gradeIndex);

            Student student = new Student(id, firstName, lastName, grade);

            Intent intent = new Intent(this, UpdateOrDeleteActivity.class);
            intent.putExtra("studentId", student.getId());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Unable to retrieve student", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        studentDatabaseHelper.close();
    }
}

