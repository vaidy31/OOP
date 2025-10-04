package ru.nsu.mzaugolnikov.task333;

public class Add extends Expression {
    private final Expression right;
    private final Expression left;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(String values) {
        return left.eval(values) + right.eval(values);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    @Override
    public Expression simplify() {
        Expression lef = left.simplify();
        Expression rig = right.simplify();

        if (lef instanceof Number && ((Number) lef).eval("") == 0) {
            return rig;
        }
        if (rig instanceof Number && ((Number) rig).eval("") == 0) {
            return lef;
        }

        return new Add(lef, rig);
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }

    @Override
    public Expression clone() {
        return new Add(left.clone(), right.clone());
    }
}
