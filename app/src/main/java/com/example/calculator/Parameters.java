package com.example.calculator;

import android.os.Parcel;
import android.os.Parcelable;

public class Parameters implements Parcelable {

    private StringBuilder value1;
    private StringBuilder value2;
    private Double result;
    private boolean hasDot;

    public Parameters() {
        StringBuilder value1 = new StringBuilder("");
        StringBuilder value2 = new StringBuilder("");
        Double result;
        boolean hasDot = false;
    }

    protected Parameters(Parcel in) {
        if (in.readByte() == 0) result = null;
        else result = in.readDouble();
        if (in.readByte() == 0) value1 = null;
        else value1 = (StringBuilder) in.readValue(getClass().getClassLoader());
        if (in.readByte() == 0) value2 = null;
        else value2 = (StringBuilder) in.readValue(getClass().getClassLoader());
        hasDot = in.readByte() != 0;
    }

    public static final Creator<Parameters> CREATOR = new Creator<Parameters>() {
        @Override
        public Parameters createFromParcel(Parcel in) {
            return new Parameters(in);
        }

        @Override
        public Parameters[] newArray(int size) {
            return new Parameters[size];
        }
    };

    public StringBuilder getValue1() {
        return value1;
    }

    public void setValue1(StringBuilder value1) {
        this.value1 = value1;
    }

    public StringBuilder getValue2() {
        return value2;
    }

    public void setValue2(StringBuilder value2) {
        this.value2 = value2;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public boolean isHasDot() {
        return hasDot;
    }

    public void setHasDot(boolean hasDot) {
        this.hasDot = hasDot;
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
