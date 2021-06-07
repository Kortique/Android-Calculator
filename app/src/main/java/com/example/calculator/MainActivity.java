package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.calculator.databinding.ActivityMainBinding;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Arithmetic arithmetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        arithmetic = new Arithmetic();
        arithmetic.setMainActivity(this);
        initButton();
    }

    private void initButton() {
        binding.button0.setOnClickListener(v -> arithmetic.setField("0"));
        binding.button1.setOnClickListener(v -> arithmetic.setField("1"));
        binding.button2.setOnClickListener(v -> arithmetic.setField("2"));
        binding.button3.setOnClickListener(v -> arithmetic.setField("3"));
        binding.button4.setOnClickListener(v -> arithmetic.setField("4"));
        binding.button5.setOnClickListener(v -> arithmetic.setField("5"));
        binding.button6.setOnClickListener(v -> arithmetic.setField("6"));
        binding.button7.setOnClickListener(v -> arithmetic.setField("7"));
        binding.button8.setOnClickListener(v -> arithmetic.setField("8"));
        binding.button9.setOnClickListener(v -> arithmetic.setField("9"));
        binding.buttonPlus.setOnClickListener(v -> arithmetic.arithmeticOperation("+"));
        binding.buttonMinus.setOnClickListener(v -> arithmetic.arithmeticOperation("-"));
        binding.buttonMultiply.setOnClickListener(v -> arithmetic.arithmeticOperation("*"));
        binding.buttonDiv.setOnClickListener(v -> arithmetic.arithmeticOperation("/"));
        binding.buttonEqual.setOnClickListener(v -> arithmetic.operationEqual());
        binding.buttonDot.setOnClickListener(v -> arithmetic.operationDot());
        binding.buttonClear.setOnClickListener(v -> arithmetic.operationClear());
        binding.buttonErase.setOnClickListener(v -> arithmetic.operationErase());
        binding.buttonReverse.setOnClickListener(v -> arithmetic.operationReverse());
    }

    protected void printResult(String string) {
        binding.textView.setText(string);
    }
}