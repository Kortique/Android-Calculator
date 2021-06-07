package com.example.calculator;

public class Arithmetic {

    private StringBuilder value1 = new StringBuilder("");
    private StringBuilder value2 = new StringBuilder("");
    private String sign;
    private Double result;
    private boolean hasDot = false;
    private MainActivity main;
    private Parameters parameters;

    public void setMainActivity(MainActivity a) {
        main = a;
        parameters = new Parameters();
    }

    void setField(String symbol) {
        value1.append(symbol);
        main.printResult(value1.toString());
        parameters.setValue1(value1);
    }

    void arithmeticOperation(String s) {
        hasDot = false;
        sign = s;
        value2.append(value1);
        value1.delete(0, value1.length());
        parameters.setHasDot(false);
        parameters.setValue1(value1);
        parameters.setValue2(value2);
    }

    void operationDot() {
        if (!hasDot) {
            if (value1.length() == 0) {
                value1.append("0.");
            } else {
                value1.append(".");
            }
            hasDot = true;
            parameters.setHasDot(true);
        }
    }

    void operationClear() {
        hasDot = false;
        clearValue();
        main.printResult("");
        new Parameters();
    }

    void operationErase() {
        if (value1.length() != 0) {
            if (value1.charAt(value1.length() - 1) == '.') {
                hasDot = false;
                parameters.setHasDot(false);
            }
            value1.deleteCharAt(value1.length() - 1);
            main.printResult(value1.toString());
            parameters.setValue1(value1);
        }
    }

    private void clearValue() {
        value1.delete(0, value1.length());
        value2.delete(0, value2.length());
        parameters.setValue1(value1);
        parameters.setValue1(value2);
    }

    void operationReverse() {
        if (value1.charAt(0) != '-') value1.insert(0, '-');
        else if (value1.charAt(0) == '-') value1.deleteCharAt(0);
        main.printResult(value1.toString());
        parameters.setValue1(value1);
    }

    void operationEqual() {
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
            result();
            clearValue();
            value2.append(result);
            new Parameters();
            parameters.setResult(result);
        }
    }

    private void result() {
        if (result % 1 == 0) {
            int intValue = (int) (Math.round(result));
            main.printResult(String.valueOf(intValue));
        } else main.printResult(String.valueOf(result));
    }
}
