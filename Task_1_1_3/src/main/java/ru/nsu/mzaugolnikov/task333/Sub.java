package ru.nsu.mzaugolnikov.task333;

/**
 * Класс для вычитания.
 */
public class Sub extends Expression {
    /** Левый операнд вычитания. */
    private final Expression left;
    /** Правый операнд вычитания. */
    private final Expression right;

    /**
     * Конструктор.
     *
     * @param left левое вырадение
     * @param right правое выражение
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Вычисление значения вычитания.
     *
     * @param values строка  с присвоениями перменных.
     * @return result
     */
    @Override
    public double eval(String values) {
        return left.eval(values) - right.eval(values);
    }

    /**
     * Берет производную.
     *
     * @param var имя переменной
     * @return новое выражение
     */
    @Override
    public Expression derivative(String var) {
        return new Sub(left.derivative(var), right.derivative(var));
    }

    /**
     * Упрощение выражения.
     * База матана.
     *
     * @return новое упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();
        Number zero = new Number(0);

        // если обе части числа, вычисляем разность
        if (l.isNumber() && r.isNumber()) {
            Number ln = (Number) l;
            Number rn = (Number) r;
            return new Number(ln.getValue() - rn.getValue());
        }

        // x - 0 = x
        if (r.isNumber() && r.equals(zero)) {
            return l;
        }

        // 0 - x = -x
        if (l.isNumber() && l.equals(zero)) {
            return new Mult(new Number(-1), r);
        }

        // иначе оставляем как есть
        return new Sub(l, r);
    }

    /**
     * Создает копию выражения.
     *
     * @return новое выражение
     */
    @Override
    public Expression clone() {
        return new Sub(left.clone(), right.clone());
    }

    /**
     * Преобразует выражение в строку.
     *
     * @return строка
     */
    @Override
    public String toString() {
        return "(" + left.toString() + " - " + right.toString() + ")";
    }
}
