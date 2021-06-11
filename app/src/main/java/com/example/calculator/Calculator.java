package com.example.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {

    private StringBuilder value1 = new StringBuilder("");
    private StringBuilder value2 = new StringBuilder("");
    private Double result = 0.0;
    private String sign;
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

    protected void arithmeticOperation(String s) {
        if (value2.length() != 0) operationEqual();
        hasDot = false;
        sign = s;
        value2.append(value1);
        value1.delete(0, value1.length());
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
        hasDot = false;
        clearValue();
        printValue(value1);
    }

    protected void operationErase() {
        if (value1.length() != 0) {
            if (value1.charAt(value1.length() - 1) == '.') hasDot = false;
            value1.deleteCharAt(value1.length() - 1);
            printValue(value1);
        }
    }

    private void clearValue() {
        value1.delete(0, value1.length());
        value2.delete(0, value2.length());
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

    protected void operationEqual() {
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
            printResult();
            clearValue();
            value2.append(result);
            hasDot = false;
            sign = null;
        }
    }

    protected void printResult() {
        if (result % 1 == 0) {
            int intValue = (int) (Math.round(result));
            main.printResult(String.valueOf(intValue));
        } else main.printResult(String.valueOf(result));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (result == null) dest.writeByte((byte) 0);
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
