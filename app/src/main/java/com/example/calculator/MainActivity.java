package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    StringBuilder value1 = new StringBuilder("");
    StringBuilder value2 = new StringBuilder("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()
        );
        setContentView(binding.getRoot());

        binding.button0.setOnClickListener(v -> setField("0"));
        binding.button1.setOnClickListener(v -> setField("1"));
        binding.button2.setOnClickListener(v -> setField("2"));
        binding.button3.setOnClickListener(v -> setField("3"));
        binding.button4.setOnClickListener(v -> setField("4"));
        binding.button5.setOnClickListener(v -> setField("5"));
        binding.button6.setOnClickListener(v -> setField("6"));
        binding.button7.setOnClickListener(v -> setField("7"));
        binding.button8.setOnClickListener(v -> setField("8"));
        binding.button9.setOnClickListener(v -> setField("9"));
        binding.buttonPlus.setOnClickListener(v -> operationAdd());
        binding.buttonMinus.setOnClickListener(v -> operationSubtract());
        binding.buttonMultiply.setOnClickListener(v -> operationMultiply());
        binding.buttonDiv.setOnClickListener(v -> operationDivide());
        binding.buttonEqual.setOnClickListener(v -> operationResult());
    }

    @SuppressLint("SetTextI18n")
    private void setField(String symbol) {
        binding.result.setText(value1 + symbol);
        value1.append(symbol);
    }

    @SuppressLint("SetTextI18n")
    private void operationSubtract() {
        binding.result.setText(value1 + "-");
        value2 = value1;
        value1.append("-");
    }

    @SuppressLint("SetTextI18n")
    private void operationMultiply() {
        binding.result.setText(value1 + "*");
        value2 = value1;
        value1.append("*");
    }

    @SuppressLint("SetTextI18n")
    private void operationDivide() {
        binding.result.setText(value1 + "/");
        value2 = value1;
        value1.append("/");
    }

    @SuppressLint("SetTextI18n")
    private void operationAdd() {
        binding.result.setText(value1 + "+");
        value2 = value1;
        value1.append("+");
    }

    private void operationResult() {
        double num1 = Double.parseDouble(String.valueOf(value1));
        double num2 = Double.parseDouble(String.valueOf(value2));
        double result = num1 + num2;
        binding.result.setText((int) result);
    }


}
