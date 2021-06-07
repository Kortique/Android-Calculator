package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.calculator.databinding.ActivityMainBinding;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Parcelable {

    private ActivityMainBinding binding;
    private StringBuilder value1 = new StringBuilder("");
    private StringBuilder value2 = new StringBuilder("");
    private String sign;
    private Double result;
    private boolean hasDot = false;

    protected MainActivity(Parcel in) {
        sign = in.readString();
        if (in.readByte() == 0) {
            result = null;
        } else {
            result = in.readDouble();
        }
        hasDot = in.readByte() != 0;
    }

    public static final Creator<MainActivity> CREATOR = new Creator<MainActivity>() {
        @Override
        public MainActivity createFromParcel(Parcel in) {
            return new MainActivity(in);
        }

        @Override
        public MainActivity[] newArray(int size) {
            return new MainActivity[size];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        initButton();
    }

    private void initButton() {
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
        binding.buttonPlus.setOnClickListener(v -> arithmeticOperation("+"));
        binding.buttonMinus.setOnClickListener(v -> arithmeticOperation("-"));
        binding.buttonMultiply.setOnClickListener(v -> arithmeticOperation("*"));
        binding.buttonDiv.setOnClickListener(v -> arithmeticOperation("/"));
        binding.buttonEqual.setOnClickListener(v -> operationEqual());
        binding.buttonDot.setOnClickListener(v -> operationDot());
        binding.buttonClear.setOnClickListener(v -> operationClear());
        binding.buttonErase.setOnClickListener(v -> operationErase());
        binding.buttonReverse.setOnClickListener(v -> operationReverse());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(getString());
    }

    @SuppressLint("SetTextI18n")
    private void setField(String symbol) {
        binding.textView.setText(value1 + symbol);
        value1.append(symbol);
//        binding.textView2.setText(value1);
//        binding.textView3.setText(value2);
    }

    private void arithmeticOperation(String s) {
        hasDot = false;
        sign = s;
        value2.append(value1);
        value1.delete(0, value1.length());
    }

    private void operationDot() {
        if (!hasDot) {
            if (value1.length() == 0) {
                value1.append("0.");
            } else {
                value1.append(".");
            }
            hasDot = true;
        }
    }

    private void operationClear() {
        hasDot = false;
        clearValue();
        binding.textView.setText("");
//        binding.textView2.setText("");
//        binding.textView3.setText("");
    }

    private void operationErase() {
        if (value1.length() != 0) {
            if (value1.charAt(value1.length() - 1) == '.') hasDot = false;
            value1.deleteCharAt(value1.length() - 1);
            binding.textView.setText(value1);
//            binding.textView2.setText(value1);
        }
    }

    private void clearValue() {
        value1.delete(0, value1.length());
        value2.delete(0, value2.length());
    }

    private void operationReverse() {
        if (value1.charAt(0) != '-') value1.insert(0, '-');
        else if (value1.charAt(0) == '-') value1.deleteCharAt(0);
        binding.textView.setText(value1);
    }

    private void operationEqual() {
        if (sign != null) {
            double num1 = Double.parseDouble(String.valueOf(value2));
            double num2 = Double.parseDouble(String.valueOf(value1));
            switch (sign) {
                default:
                    break;
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
            }
            hasDot = false;
            printResult();
            clearValue();
            value2.append(result);
        }
    }

    private void printResult() {
        if (result % 1 == 0) {
            int intValue = (int)(Math.round(result));
            binding.textView.setText(String.valueOf(intValue));
        } else binding.textView.setText(String.valueOf(result));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sign);
        if (result == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(result);
        }
        dest.writeByte((byte) (hasDot ? 1 : 0));
        dest.writeValue(value1);
        dest.writeValue(value2);
    }
}