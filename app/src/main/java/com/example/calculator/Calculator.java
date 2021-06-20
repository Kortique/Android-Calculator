package com.example.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {

    private StringBuilder value1 = new StringBuilder("");
    private StringBuilder value2 = new StringBuilder("");
    private StringBuilder log = new StringBuilder("");
    private Double result = 0.0;
    private String sign;
    private String currentSign;
    private boolean hasDot = false;
    private MainActivity main;

    protected Double getResult() {
        return result;
    }

    protected StringBuilder getValue1() {
        return value1;
    }

    protected Calculator() {
    }

    protected void setMainActivity(MainActivity a) {
        main = a;
    }

    protected void removeActivity() {
        main = null;
    }

    protected Calculator(Parcel in) {
        if (in.readByte() == 0) result = null;
        else result = in.readDouble();
        if (in.readByte() == 0) value1 = null;
        else value1 = (StringBuilder) in.readValue(getClass().getClassLoader());
        if (in.readByte() == 0) value2 = null;
        else value2 = (StringBuilder) in.readValue(getClass().getClassLoader());
        hasDot = in.readByte() != 0;
    }

    protected static final Parcelable.Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    protected void setField(String symbol) {
        value1.append(symbol);
        printValue(value1);
    }

    private void printValue(StringBuilder numbers) {
        main.printResult(numbers.toString());
    }

    protected void operationArithmetic(String s) {
        if (value1.length() != 0) {
            hasDot = false;
            result = 0.0;

            getArithmeticResult();
            setValue2();
            if ((s.equals("=")) && (value2.length()) != 0) {
                operationEqual();
            }

            currentSign = s;
            value1.delete(0, value1.length());


//            log.append(result);

        }
    }

    private void getArithmeticResult() {
        if (value2.length() != 0) {
            sign = currentSign;
            arithmetic();
        }
    }

    private void arithmetic() {
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
        value2.delete(0, value2.length());
        printResult();
    }

    protected void printResult() {
        if (result % 1 == 0) {
            int intValue = (int) (Math.round(result));
            main.printResult(String.valueOf(intValue));
        } else main.printResult(String.valueOf(result));
    }

    private void setValue2() {
        if (result == 0.0) {
            value2.append(value1);
        } else {
            value2.append(result);
        }
    }

    protected void operationEqual() {
        printResult();
        setDefault();
    }

    private void setDefault() {
        value1.delete(0, value1.length());
        value2.delete(0, value2.length());
        hasDot = false;
        sign = null;
        currentSign = null;
    }

    protected void operationDot() {
        if (!hasDot) {
            if (value1.length() == 0) {
                value1.append("0.");
            } else {
                value1.append(".");
            }
            hasDot = true;
        }
    }

    protected void operationClear() {
        setDefault();
        printValue(value1);
    }

    protected void operationErase() {
        if (value1.length() != 0) {
            if (value1.charAt(value1.length() - 1) == '.') hasDot = false;
            value1.deleteCharAt(value1.length() - 1);
            printValue(value1);
        }
    }

    protected void operationReverse() {
        if (value1.length() != 0) {
            if (value1.charAt(0) != '-') value1.insert(0, '-');
            else if (value1.charAt(0) == '-') value1.deleteCharAt(0);
        } else {
            value1.insert(0, '-');
        }
        printValue(value1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (result == 0.0) dest.writeByte((byte) 0);
        else {
            dest.writeByte((byte) 1);
            dest.writeDouble(result);
        }
        if (value1 == null) dest.writeByte((byte) 0);
        else {
            dest.writeByte((byte) 1);
            dest.writeValue(value1);
        }
        if (value2 == null) dest.writeByte((byte) 0);
        else {
            dest.writeByte((byte) 1);
            dest.writeValue(value2);
        }
        dest.writeByte((byte) (hasDot ? 1 : 0));
    }
}
