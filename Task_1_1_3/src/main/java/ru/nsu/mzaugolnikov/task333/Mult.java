package ru.nsu.mzaugolnikov.task333;

public class Mult extends Expression {
    private final Expression right;
    private final Expression left;

    public Mult(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(String values) {
        return left.eval(values) * right.eval(values);
    }

    @Override
    public Expression derivative(String var) {
        Expression lef = left.derivative(var);
        Expression rig = right.derivative(var);
        return new Add(new Mult(lef, right.clone()), new Mult(left.clone(), rig));
    }

    @Override
    public Expression simplify() {
        Expression lef = left.simplify();
        Expression rig = right.simplify();

        if (lef.isNumber() && rig.isNumber()) {
            return new Number(lef.getValue() * rig.getValue());
        }

        if ((lef.isNumber() && lef.getValue() == 0) || (rig.isNumber() && rig.getValue() == 0)) {
            return new Number(0);
        }

        if (lef.isNumber() && lef.getValue() == 1) return rig;
        if (rig.isNumber() && rig.getValue() == 1) return lef;

        return new Mult(lef, rig);
    }

    @Override
    public Expression clone() {
        return new Mult(left.clone(), right.clone());
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }
}
