package ru.nsu.mzaugolnikov.task333;

/**
 * Класс для умножения.
 */
public class Mult extends Expression {
    /** Левый операнд. */
    private final Expression left;
    /** Правый операнд. */
    private final Expression right;

    /**
     * Конструктор для умножения двух выражений.
     *
     * @param left  левый операнд
     * @param right правый операнд
     */
    public Mult(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Значение произведения при заданных значнеиях.
     *
     * @param values  строка с присвоениями
     * @return результат умножения
     */
    @Override
    public double eval(String values) {
        return left.eval(values) * right.eval(values);
    }

    /**
     * Дифференцирование выражения.
     *
     * @param var имя
     * @return новое выражение
     */
    @Override
    public Expression derivative(String var) {
        Expression lef = left.derivative(var);
        Expression rig = right.derivative(var);
        return new Add(new Mult(lef, right.clone()), new Mult(left.clone(), rig));
    }

    /**
     * Упрощение выражения по базе матана.
     *
     * @return новое упрощённое выражение
     */
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

        if (lef.isNumber() && lef.getValue() == 1) {
            return rig;
        }
        if (rig.isNumber() && rig.getValue() == 1) {
            return lef;
        }

        return new Mult(lef, rig);
    }

    /**
     * Создание копий выражения.
     *
     * @return новое выражение Mult с клонами операндов
     */
    @Override
    public Expression clone() {
        return new Mult(left.clone(), right.clone());
    }

    /**
     * Преобразует выражение в строку.
     *
     * @return строка
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " * " + right.toString() + ")";
    }
}
