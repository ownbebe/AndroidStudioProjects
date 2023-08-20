package com.example.mortgagecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private EditText purchasePriceEditText;
    private EditText downPaymentEditText;
    private EditText interestRateEditText;
    private SeekBar loanDurationSeekBar;
    private TextView loanDurationValueTextView;
    private TextView monthlyPayment10TextView;
    private TextView monthlyPayment20TextView;
    private TextView monthlyPayment30TextView;
    private TextView monthlyPaymentCustomTextView;
    private Button calculateButton;

    private double purchasePrice;
    private double downPayment;
    private double interestRate;
    private int loanDuration;

    private static final int MONTHS_IN_YEAR = 12;
    private static final int HUNDRED = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        purchasePriceEditText = findViewById(R.id.purchase_price);
        downPaymentEditText = findViewById(R.id.down_payment);
        interestRateEditText = findViewById(R.id.interest_rate);
        loanDurationSeekBar = findViewById(R.id.loan_duration);
        loanDurationValueTextView = findViewById(R.id.loan_duration_value);
        monthlyPayment10TextView = findViewById(R.id.monthly_payment_10);
        monthlyPayment20TextView = findViewById(R.id.monthly_payment_20);
        monthlyPayment30TextView = findViewById(R.id.monthly_payment_30);
        monthlyPaymentCustomTextView = findViewById(R.id.monthly_payment_custom);
        calculateButton = findViewById(R.id.calculate_button);

        loanDurationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                loanDuration = progress;
                loanDurationValueTextView.setText("Loan Duration: " + loanDuration + " years");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchasePrice = Double.parseDouble(purchasePriceEditText.getText().toString());
                downPayment = Double.parseDouble(downPaymentEditText.getText().toString());
                interestRate = Double.parseDouble(interestRateEditText.getText().toString());
                interestRate = Double.parseDouble(interestRateEditText.getText().toString());
                double loanAmount = purchasePrice - downPayment;
                double monthlyInterestRate = interestRate / MONTHS_IN_YEAR / HUNDRED;

                double monthlyPayment10 = calculateMonthlyPayment(loanAmount, monthlyInterestRate, 10);
                double monthlyPayment20 = calculateMonthlyPayment(loanAmount, monthlyInterestRate, 20);
                double monthlyPayment30 = calculateMonthlyPayment(loanAmount, monthlyInterestRate, 30);
                double monthlyPaymentCustom = calculateMonthlyPayment(loanAmount, monthlyInterestRate, loanDuration);

                NumberFormat currency = NumberFormat.getCurrencyInstance();

                monthlyPayment10TextView.setText("10 Years: " + currency.format(monthlyPayment10));
                monthlyPayment20TextView.setText("20 Years: " + currency.format(monthlyPayment20));
                monthlyPayment30TextView.setText("30 Years: " + currency.format(monthlyPayment30));
                monthlyPaymentCustomTextView.setText(loanDuration + " Years: " + currency.format(monthlyPaymentCustom));
            }
        });
    }

    private double calculateMonthlyPayment(double loanAmount, double monthlyInterestRate, int loanDuration) {
        int numberOfPayments = loanDuration * MONTHS_IN_YEAR;
        double monthlyPayment = loanAmount * monthlyInterestRate /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        return monthlyPayment;
    }
}

