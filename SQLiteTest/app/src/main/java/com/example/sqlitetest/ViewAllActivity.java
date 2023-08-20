package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewAllActivity extends AppCompatActivity {

    ListView studentListView;
    ArrayList<Student> studentList;
    StudentListAdapter studentListAdapter;
    StudentDbHelper studentDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        studentListView = findViewById(R.id.student_listview);

        studentDbHelper = new StudentDbHelper(this);
        studentList = studentDbHelper.getAllStudents();

        if (studentList.size() > 0) {
            studentListAdapter = new StudentListAdapter(getApplicationContext(), R.layout.student_list_item, studentList);

            studentListView.setAdapter(studentListAdapter);
        } else {
            Toast.makeText(this, "No students found", Toast.LENGTH_SHORT).show();
        }
    }
}
