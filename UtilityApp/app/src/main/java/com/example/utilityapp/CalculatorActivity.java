package com.example.utilityapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText number1EditText, number2EditText;
    private TextView resultTextView;
    private Button addButton, subtractButton, multiplyButton, divideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        number1EditText = findViewById(R.id.number1);
        number2EditText = findViewById(R.id.number2);
        resultTextView = findViewById(R.id.result);
        addButton = findViewById(R.id.add_button);
        subtractButton = findViewById(R.id.subtract_button);
        multiplyButton = findViewById(R.id.multiply_button);
        divideButton = findViewById(R.id.divide_button);

        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        double result = 0.0;

        switch (v.getId()) {
            case R.id.add_button:
                result = Double.parseDouble(number1EditText.getText().toString()) + Double.parseDouble(number2EditText.getText().toString());
                resultTextView.setText(String.valueOf(result));
                break;
            case R.id.subtract_button:
                result = Double.parseDouble(number1EditText.getText().toString()) - Double.parseDouble(number2EditText.getText().toString());
                resultTextView.setText(String.valueOf(result));
                break;
            case R.id.multiply_button:
                result = Double.parseDouble(number1EditText.getText().toString()) * Double.parseDouble(number2EditText.getText().toString());
                resultTextView.setText(String.valueOf(result));
                break;
            case R.id.divide_button:
                double divisor = Double.parseDouble(number2EditText.getText().toString());
                if (divisor == 0) {
                    resultTextView.setText("");
                } else {
                    result = Double.parseDouble(number1EditText.getText().toString()) / divisor;
                    resultTextView.setText(String.valueOf(result));
                }
                break;
        }
    }
    public void onBackClick(View view) {
        finish();
    }
}

