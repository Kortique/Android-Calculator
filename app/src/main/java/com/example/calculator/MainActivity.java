package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import com.example.calculator.databinding.ActivityMainBinding;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Calculator calculator;
    private final static String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        calculator = new Calculator();
        calculator.setMainActivity(this);
        initButton();
    }

    private void initButton() {
        binding.button0.setOnClickListener(v -> calculator.setField("0"));
        binding.button1.setOnClickListener(v -> calculator.setField("1"));
        binding.button2.setOnClickListener(v -> calculator.setField("2"));
        binding.button3.setOnClickListener(v -> calculator.setField("3"));
        binding.button4.setOnClickListener(v -> calculator.setField("4"));
        binding.button5.setOnClickListener(v -> calculator.setField("5"));
        binding.button6.setOnClickListener(v -> calculator.setField("6"));
        binding.button7.setOnClickListener(v -> calculator.setField("7"));
        binding.button8.setOnClickListener(v -> calculator.setField("8"));
        binding.button9.setOnClickListener(v -> calculator.setField("9"));
        binding.buttonPlus.setOnClickListener(v -> calculator.arithmeticOperation("+"));
        binding.buttonMinus.setOnClickListener(v -> calculator.arithmeticOperation("-"));
        binding.buttonMultiply.setOnClickListener(v -> calculator.arithmeticOperation("*"));
        binding.buttonDiv.setOnClickListener(v -> calculator.arithmeticOperation("/"));
        binding.buttonEqual.setOnClickListener(v -> calculator.operationEqual());
        binding.buttonDot.setOnClickListener(v -> calculator.operationDot());
        binding.buttonClear.setOnClickListener(v -> calculator.operationClear());
        binding.buttonErase.setOnClickListener(v -> calculator.operationErase());
        binding.buttonReverse.setOnClickListener(v -> calculator.operationReverse());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(DATA, calculator);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculator = savedInstanceState.getParcelable(DATA);
        restorePrintResult();
        calculator.setMainActivity(this);
    }

    @Override
    protected void onDestroy() {
        calculator.removeActivity();
        super.onDestroy();
    }

    protected void printResult(String string) {
        binding.textView.setText(string);
    }

    @SuppressLint("SetTextI18n")
    protected void restorePrintResult() {
        if (calculator.getResult() == 0.0) binding.textView.setText(calculator.getValue1());
        else binding.textView.setText(calculator.getResult().toString());
    }
}