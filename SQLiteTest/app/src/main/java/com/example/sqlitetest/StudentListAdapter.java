package com.example.sqlitetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentListAdapter extends ArrayAdapter<Student> {

    public StudentListAdapter(Context context, int resource, List<Student> students) {
        super(context, resource, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_list_item, parent, false);
        }

        TextView studentId = convertView.findViewById(R.id.student_id);
        TextView studentName = convertView.findViewById(R.id.student_name);
        TextView studentLastName = convertView.findViewById(R.id.student_last_name);
        TextView studentGrade = convertView.findViewById(R.id.student_grade);

        studentId.setText(String.valueOf(student.getId()));
        studentName.setText(student.getName());
        studentLastName.setText(student.getLastName());
        studentGrade.setText(String.valueOf(student.getGrade()));

        return convertView;
    }
}

