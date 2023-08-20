package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.sqlitetest.StudentDbHelper;
import com.example.sqlitetest.R;
import com.example.sqlitetest.Student;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class UpdateOrDeleteActivity extends AppCompatActivity {

    private EditText mFirstNameEditText, mLastNameEditText, mGradeEditText;
    private Button mUpdateButton, mDeleteButton;
    private int mStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_or_delete);

        mFirstNameEditText = findViewById(R.id.name_edittext);
        mLastNameEditText = findViewById(R.id.last_name_edittext);
        mGradeEditText = findViewById(R.id.grade_edittext);
        mUpdateButton = findViewById(R.id.Update_button);
        mDeleteButton = findViewById(R.id.Delete_button);

        mStudentId = getIntent().getIntExtra("studentId", -1);
        if (mStudentId == -1) {
            Toast.makeText(this, "Unable to retrieve student", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            SQLiteDatabase db = new StudentDbHelper(this).getWritableDatabase();
            Cursor cursor = db.query(
                    "students",
                    new String[]{"_id", "name", "last_name", "grade"},
                    "_id = ?",
                    new String[]{String.valueOf(mStudentId)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                String firstName = cursor.getString(cursor.getColumnIndex("name"));
                String lastName = cursor.getString(cursor.getColumnIndex("last_name"));
                int grade = cursor.getInt(cursor.getColumnIndex("grade"));

                mFirstNameEditText.setText(firstName);
                mLastNameEditText.setText(lastName);
                mGradeEditText.setText(String.valueOf(grade));
            }
            cursor.close();
            db.close();
        }

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = mFirstNameEditText.getText().toString();
                String lastName = mLastNameEditText.getText().toString();
                int grade = Integer.parseInt(mGradeEditText.getText().toString());

                SQLiteDatabase db = new StudentDbHelper(UpdateOrDeleteActivity.this).getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", firstName);
                values.put("last_name", lastName);
                values.put("grade", grade);

                int numRowsUpdated = db.update("students", values, "_id = ?", new String[]{String.valueOf(mStudentId)});
                db.close();

                if (numRowsUpdated > 0) {
                    Toast.makeText(UpdateOrDeleteActivity.this, "Student updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateOrDeleteActivity.this, "Unable to update student", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = new StudentDbHelper(UpdateOrDeleteActivity.this).getWritableDatabase();
                int numRowsDeleted = db.delete("students", "_id = ?", new String[]{String.valueOf(mStudentId)});
                db.close();

                if (numRowsDeleted > 0) {
                    Toast.makeText(UpdateOrDeleteActivity.this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateOrDeleteActivity.this, "Unable to delete student", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}
