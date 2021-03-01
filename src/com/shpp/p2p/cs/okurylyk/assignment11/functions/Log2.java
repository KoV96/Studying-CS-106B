package com.shpp.p2p.cs.okurylyk.assignment11.functions;

public class Log2 extends IAction{
    @Override
    double calculate(double number) {
        return Math.log(number) / Math.log(2);
    }
}
