package com.example.sqlite;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        StudentDBHelper dbHelper = new StudentDBHelper(this);

        List<Student> students = dbHelper.selectAllData();
        StudentListAdapter adapter = new StudentListAdapter(this, students);
        ListView listView = findViewById(R.id.student_listview);
        listView.setAdapter(adapter);
    }
}

