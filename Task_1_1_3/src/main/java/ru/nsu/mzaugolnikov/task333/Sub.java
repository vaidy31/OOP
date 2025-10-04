package ru.nsu.mzaugolnikov.task333;

public class Sub extends Expression {
    /** Левый операнд вычитания */
    private final Expression left;
    /** Правый операнд вычитания */
    private final Expression right;

    /**
     * Конструктор.
     * @param left левое вырадение
     * @param right правое выражение
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(String values) {
        return left.eval(values) - right.eval(values);
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }

    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        // если обе части числа, вычисляем разность
        if (l.isNumber() && r.isNumber()) {
            return new Number(l.getValue() - r.getValue());
        }

        // x - 0 = x
        if (r.isNumber() && r.getValue() == 0) {
            return l;
        }

        // 0 - x = -x
        if (l.isNumber() && l.getValue() == 0) {
            return new Mult(new Number(-1), r);
        }

        // иначе оставляем как есть
        return new Sub(l, r);
    }

    @Override
    public Expression clone() {
        return new Sub(left.clone(), right.clone());
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " - " + right.toString() + ")";
    }
}
