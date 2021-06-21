package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.calculator.databinding.ActivityMainBinding;

import android.content.Intent;

public class MainActivity extends AppCompatActivity implements Constants {

    private ActivityMainBinding binding;
    private Calculator calculator;
    private static SharedPreferences myTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myTheme = getSharedPreferences(THEME, MODE_PRIVATE);
        calculator = new Calculator();
        calculator.setMainActivity(this);
        initButton();
        initTheme();
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
        binding.buttonPlus.setOnClickListener(v -> calculator.operationArithmetic("+"));
        binding.buttonMinus.setOnClickListener(v -> calculator.operationArithmetic("-"));
        binding.buttonMultiply.setOnClickListener(v -> calculator.operationArithmetic("*"));
        binding.buttonDiv.setOnClickListener(v -> calculator.operationArithmetic("/"));
        binding.buttonEqual.setOnClickListener(v -> calculator.operationEqual());
        binding.buttonDot.setOnClickListener(v -> calculator.operationDot());
        binding.buttonClear.setOnClickListener(v -> calculator.operationClear());
        binding.buttonErase.setOnClickListener(v -> calculator.operationErase());
        binding.buttonReverse.setOnClickListener(v -> calculator.operationReverse());
        binding.buttonSetting.setOnClickListener(v -> startSettingActivity());
    }

    private void startSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void initTheme() {
        switch (getSavedTheme()) {
            case THEME_LIGHT:
            case THEME_UNDEFINED:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
    }

    private int getSavedTheme() {
        return myTheme.getInt(KEY_THEME, THEME_UNDEFINED);
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

    protected void printResult(StringBuilder string) {
        binding.textView.setText(string);
    }

    protected void printLog(StringBuilder string) {
        binding.textViewLog.setText(string);
    }

    @SuppressLint("SetTextI18n")
    protected void restorePrintResult() {
        if (calculator.getResult().length() == 0) binding.textView.setText(calculator.getValue1());
        else binding.textView.setText(calculator.getResult());
    }
}