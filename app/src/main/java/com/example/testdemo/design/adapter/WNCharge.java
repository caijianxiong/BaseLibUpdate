package com.example.testdemo.design.adapter;

public class WNCharge {

    private ICharge iCharge;

    public WNCharge(ICharge iCharge) {
        this.iCharge = iCharge;
    }

    public void charge() {
        iCharge.charge();
    }
}
