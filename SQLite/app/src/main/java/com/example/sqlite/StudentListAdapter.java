package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class StudentListAdapter extends ArrayAdapter<Student> {

    private Context context;
    private List<Student> students;

    public StudentListAdapter(Context context, List<Student> students) {
        super(context, 0, students);
        this.context = context;
        this.students = students;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.student_list_item, parent, false);
        }

        Student student = students.get(position);

        TextView nameTextView = view.findViewById(R.id.name_textview);
        TextView lastNameTextView = view.findViewById(R.id.last_name_textview);
        TextView gradeTextView = view.findViewById(R.id.grade_textview);

        nameTextView.setText(student.getName());
        lastNameTextView.setText(student.getLastName());
        gradeTextView.setText(String.valueOf(student.getGrade()));

        return view;
    }
}

