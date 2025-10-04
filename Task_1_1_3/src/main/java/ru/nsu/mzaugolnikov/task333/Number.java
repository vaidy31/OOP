package ru.nsu.mzaugolnikov.task333;

public class Number extends Expression {
    private final double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double eval(String values) {
        return value;
    }

    @Override
    public Expression derivative(String var) {
        return  new Number(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public Expression clone() {
        return new Number(value);
    }

    @Override
    public String toString() {
        return  Double.toString(value);
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public double getValue() {
        return value;
    }

}
