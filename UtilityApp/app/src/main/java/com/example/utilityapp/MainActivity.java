package com.example.utilityapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calculatorButton = findViewById(R.id.calculator_button);
        Button notesButton = findViewById(R.id.notes_button);
        Button calendarButton = findViewById(R.id.calendar_button);

        calculatorButton.setOnClickListener(this);
        notesButton.setOnClickListener(this);
        calendarButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.calculator_button:
                // Launch the CalculatorActivity
                intent = new Intent(MainActivity.this, CalculatorActivity.class);
                break;
            case R.id.notes_button:
                // Launch the NotesActivity
                intent = new Intent(MainActivity.this, NotesActivity.class);
                break;
            case R.id.calendar_button:
                // Launch the CalendarActivity
                intent = new Intent(MainActivity.this, CalendarActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
