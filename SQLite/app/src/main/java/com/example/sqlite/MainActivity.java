package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.util.List;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;







public class MainActivity extends AppCompatActivity {

    private StudentDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new StudentDBHelper(this);
    }

    public void onAddDataButtonClick(View view) {
        EditText nameEditText = findViewById(R.id.name_edittext);
        EditText lastNameEditText = findViewById(R.id.last_name_edittext);
        EditText gradeEditText = findViewById(R.id.grade_edittext);

        String name = nameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        int grade = Integer.parseInt(gradeEditText.getText().toString());

        dbHelper.insertData(name, lastName, grade);

        nameEditText.setText("");
        lastNameEditText.setText("");
        gradeEditText.setText("");
    }

    public void onViewAllButtonClick(View view) {
        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);
    }


    public void onUpdateButtonClick(View view) {
        EditText idEditText = findViewById(R.id.id_edittext);
        String idString = idEditText.getText().toString();

        if (idString.isEmpty()) {
            Toast.makeText(this, "Please enter a student ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idString);

        // Open the database connection
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + StudentContract.StudentEntry.TABLE_NAME + " WHERE " + StudentContract.StudentEntry._ID + "=?", new String[]{String.valueOf(id)});

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No student found with ID " + id, Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToFirst();

            String name = cursor.getString(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_LAST_NAME));
            int grade = cursor.getInt(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_GRADE));

            // Prompt user for new information
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Update Student Info");

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            EditText nameEditText = new EditText(this);
            nameEditText.setHint("Name");
            nameEditText.setText(name);
            layout.addView(nameEditText);

            EditText lastNameEditText = new EditText(this);
            lastNameEditText.setHint("Last Name");
            lastNameEditText.setText(lastName);
            layout.addView(lastNameEditText);

            EditText gradeEditText = new EditText(this);
            gradeEditText.setHint("Grade");
            gradeEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            gradeEditText.setText(String.valueOf(grade));
            layout.addView(gradeEditText);

            builder.setView(layout);

            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = nameEditText.getText().toString();
                    String lastName = lastNameEditText.getText().toString();
                    int grade = Integer.parseInt(gradeEditText.getText().toString());

                    // Update the student record
                    ContentValues values = new ContentValues();
                    values.put(StudentContract.StudentEntry.COLUMN_NAME, name);
                    values.put(StudentContract.StudentEntry.COLUMN_LAST_NAME, lastName);
                    values.put(StudentContract.StudentEntry.COLUMN_GRADE, grade);
                    db.update(StudentContract.StudentEntry.TABLE_NAME, values, StudentContract.StudentEntry._ID + "=?", new String[]{String.valueOf(id)});

                    Toast.makeText(MainActivity.this, "Student data updated", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }

        // Close the database connection
        db.close();
    }




    public void onDeleteButtonClick(View view) {
        EditText idEditText = findViewById(R.id.id_edittext);

        int id = Integer.parseInt(idEditText.getText().toString());

        dbHelper.deleteData(id);

        idEditText.setText("");
    }
}




