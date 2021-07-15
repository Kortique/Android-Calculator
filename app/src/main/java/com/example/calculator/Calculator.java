package com.example.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {

    private StringBuilder value1 = new StringBuilder("");
    private final StringBuilder value2 = new StringBuilder("");
    private StringBuilder log = new StringBuilder("");
    private StringBuilder savedLog = new StringBuilder("");
    private final StringBuilder result = new StringBuilder("");
    private StringBuilder savedResult = new StringBuilder("");
    private String sign;
    private String currentSign;
    private boolean hasDot = false;
    private MainActivity main;

    protected StringBuilder getValue1() {
        return value1;
    }

    public StringBuilder getLog() {
        return log;
    }

    public StringBuilder getSavedResult() {
        return savedResult;
    }

    public StringBuilder getSavedLog() {
        return savedLog;
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
        if (in.readByte() == 0) savedResult = null;
        else savedResult = (StringBuilder) in.readValue(getClass().getClassLoader());
        if (in.readByte() == 0) log = null;
        else log = (StringBuilder) in.readValue(getClass().getClassLoader());
        if (in.readByte() == 0) savedLog = null;
        else savedLog = (StringBuilder) in.readValue(getClass().getClassLoader());
        if (in.readByte() == 0) value1 = null;
        else value1 = (StringBuilder) in.readValue(getClass().getClassLoader());
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
        if (checkExtraNull(symbol)) {
            checkExtraSavedInfo();
            value1.append(symbol);
            printValue(value1);
            updateLog(symbol);
        }
    }

    private boolean checkExtraNull(String symbol) {
        if (value1.length() == 1 && value1.charAt(0) == '0') {
            setDotAfterNull(symbol);
            return false;
        }
        if (value1.length() == 0 && symbol.equals("0")) {
            setNullAndDotAfterPressNull();
            return false;
        }
        return true;
    }

    private void setDotAfterNull(String symbol) {
        value1.append(".").append(symbol);
        printValue(value1);
        log.append(".").append(symbol);
        main.printLog(log);
        hasDot = true;
    }

    private void setNullAndDotAfterPressNull() {
        value1.append("0.");
        printValue(value1);
        updateLog("0.");
        hasDot = true;
    }

    private void checkExtraSavedInfo() {
        if (savedResult.length() != 0) {
            savedLog.delete(0, savedLog.length());
            savedResult.delete(0, savedResult.length());
        }
    }

    private void printValue(StringBuilder numbers) {
        main.printResult(numbers);
    }

    private void updateLog(Object s) {
        log.append(s);
        main.printLog(log);
    }

    protected void operationArithmetic(String s) {
        if ((value1.length() != 0) && (value1.charAt(value1.length() - 1) != '.')) {
            hasDot = false;

            getArithmeticResult();
            setValue2();

            currentSign = s;
            value1.delete(0, value1.length());
            updateLog(s);
        }
    }

    private void getArithmeticResult() {
        if (value2.length() != 0) {
            sign = currentSign;
            double num1 = Double.parseDouble(String.valueOf(value2));
            double num2 = Double.parseDouble(String.valueOf(value1));
            double summary = 0.0;
            switch (sign) {
                default:
                    break;
                case "+":
                    summary = num1 + num2;
                    break;
                case "-":
                    summary = num1 - num2;
                    break;
                case "*":
                    summary = num1 * num2;
                    break;
                case "/":
                    summary = num1 / num2;
                    break;
            }
            casting(summary);
            main.printResult(result);
            value2.delete(0, value2.length());
        }
    }

    private void casting(double summary) {
        result.delete(0, result.length());
        if (summary % 1 == 0) {
            int intValue = (int) (Math.round(summary));
            result.append(intValue);
        } else {
            result.append(summary);
        }
    }

    protected void printResult() {
        main.printResult(result);
        updateLog(result);
    }

    private void setValue2() {
        if (result.length() == 0) {
            value2.append(value1);
        } else {
            value2.append(result);
        }
    }

    protected void operationEqual() {
        if (value2.length() != 0) {
            getArithmeticResult();
            updateLog("=");
            printResult();
            saveResultAndLog();
            setDefault();
        }
    }

    private void saveResultAndLog() {
        savedResult.append(result);
        savedLog.append(log);
    }

    private void setDefault() {
        value1.delete(0, value1.length());
        value2.delete(0, value2.length());
        log.delete(0, log.length());
        result.delete(0, result.length());
        hasDot = false;
        sign = "";
        currentSign = "";
    }

    protected void operationDot() {
        if (!hasDot) {
            if (value1.length() == 0) {
                value1.append("0.");
                updateLog("0.");
            } else {
                value1.append(".");
                updateLog(".");
            }
            hasDot = true;
            printValue(value1);
        }
    }

    protected void operationClear() {
        setDefault();
        printValue(value1);
        updateLog("");
    }

    protected void operationErase() {
        if (value1.length() != 0) {
            if (value1.charAt(value1.length() - 1) == '.') hasDot = false;
            value1.deleteCharAt(value1.length() - 1);
            log.deleteCharAt(log.length() - 1);
            updateLog("");
            printValue(value1);
        }
    }

    protected void operationReverse() {
        if (value1.length() != 0) {
            if ((value1.charAt(0) != '-')) {
                value1.insert(0, '-');
                log.insert(log.length() - value1.length() + 1, '-');
            } else {
                value1.deleteCharAt(0);
                log.deleteCharAt(log.length() - value1.length() - 1);
            }
        }
        printValue(value1);
        updateLog("");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (savedResult.length() == 0) dest.writeByte((byte) 0);
        else {
            dest.writeByte((byte) 1);
            dest.writeValue(savedResult);
        }
        if (log.length() == 0) dest.writeByte((byte) 0);
        else {
            dest.writeByte((byte) 1);
            dest.writeValue(log);
        }
        if (savedLog.length() == 0) dest.writeByte((byte) 0);
        else {
            dest.writeByte((byte) 1);
            dest.writeValue(savedLog);
        }
        if (value1 == null) dest.writeByte((byte) 0);
        else {
            dest.writeByte((byte) 1);
            dest.writeValue(value1);
        }
        dest.writeByte((byte) (hasDot ? 1 : 0));
    }
}
