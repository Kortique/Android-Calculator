package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import com.example.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private StringBuilder value1 = new StringBuilder("");
    private StringBuilder value2 = new StringBuilder("");
    private String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
        binding.buttonEqual.setOnClickListener(v -> operationEqual());
    }

    @SuppressLint("SetTextI18n")
    private void setField(String symbol) {
        binding.textView.setText(value1 + symbol);
        value1.append(symbol);
        binding.textView2.setText(value1);
        binding.textView3.setText(value2);
    }

    @SuppressLint("SetTextI18n")
    private void operationAdd() {
        sign = "+";
        value2.append(value1);
        value1.delete(0,value1.length());
    }

    @SuppressLint("SetTextI18n")
    private void operationSubtract() {
        sign = "-";
        value2.append(value1);
        value1.delete(0,value1.length());
    }

    @SuppressLint("SetTextI18n")
    private void operationMultiply() {
        sign = "*";
        value2.append(value1);
        value1.delete(0,value1.length());
    }

    @SuppressLint("SetTextI18n")
    private void operationDivide() {
        sign = "/";
        value2.append(value1);
        value1.delete(0,value1.length());
    }

    private void operationEqual() {
        double num1 = Double.parseDouble(String.valueOf(value1));
        double num2 = Double.parseDouble(String.valueOf(value2));
        double result = num1 + num2;
        binding.textView.setText(String.valueOf(result));
        value1.delete(0,value1.length());
        value2.delete(0,value2.length());
        value2.append(result);
    }


}
