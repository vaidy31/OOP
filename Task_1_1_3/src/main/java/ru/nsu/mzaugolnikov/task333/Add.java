package ru.nsu.mzaugolnikov.task333;

/**
 * Класс для сложения.
 */
public class Add extends Expression {
    /** Левый операнд. */
    private final Expression left;
    /** Правый операнд. */
    private final Expression right;

    /**
     * Конструктор для сложения двух выражений.
     *
     * @param left  левый
     * @param right правый
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Вычисляет значение сложения при заданных значениях переменных.
     *
     * @param values строка с присвоениями переменных
     * @return результат сложения
     */
    @Override
    public double eval(String values) {
        return left.eval(values) + right.eval(values);
    }

    /**
     * Производная суммы равна сумме производных.
     *
     * @param var имя переменной
     * @return новое выражение
     */
    @Override
    public Expression derivative(String var) {
        return new Add(left.derivative(var), right.derivative(var));
    }

    /**
     * Упрощает выражение.
     *
     * @return новое упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression lef = left.simplify();
        Expression rig = right.simplify();
        Number zero = new Number(0);

        if (zero.equals(lef)) {
            return rig;
        }
        if (zero.equals(rig)) {
            return lef;
        }

        return new Add(lef, rig);
    }

    /**
     * Преобразует выражение в строку.
     *
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " + " + right.toString() + ")";
    }


    /**
     * Создает копию выражения.
     *
     * @return новое выражение
     */
    @Override
    public Expression clone() {
        return new Add(left.clone(), right.clone());
    }
}
