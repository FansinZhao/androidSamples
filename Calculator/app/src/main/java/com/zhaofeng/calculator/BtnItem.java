package com.zhaofeng.calculator;

/**
 * Created by zhaofeng on 17-2-9.
 */

public class BtnItem {

    private int Type;
    private double value;

    public BtnItem(int type, double value) {
        Type = type;
        this.value = value;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
