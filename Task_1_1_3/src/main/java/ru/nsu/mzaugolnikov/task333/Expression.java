package ru.nsu.mzaugolnikov.task333;

public abstract class Expression {
    public abstract Expression derivative(String var);

    public abstract double eval(String val);

    public abstract Expression clone();

    public abstract Expression simplify();

    public boolean isNumber() {
        return false;
    }

    public double getValue() {
        throw new UnsupportedOperationException("Not a number");
    }
}
