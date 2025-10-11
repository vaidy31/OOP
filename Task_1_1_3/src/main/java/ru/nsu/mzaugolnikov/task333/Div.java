package ru.nsu.mzaugolnikov.task333;

import ru.nsu.mzaugolnikov.task333.exeptions.DivisionByZeroException;

/**
 * Класс для деления.
 */
public class Div extends Expression {
    /** Верхний операнд. */
    private final Expression chislitel;
    /** Нижний операнд. */
    private final Expression znamenatel;

    /**
     * Конструктор для делени.
     *
     * @param chislitel сверху
     * @param znamenatel снизу
     */
    public Div(Expression chislitel, Expression znamenatel) {
        this.chislitel = chislitel;
        this.znamenatel = znamenatel;
    }

    /**
     * Считает значение при заданных переменных.
     *
     * @param values строка
     * @return результат
     */
    @Override
    public double eval(String values) {
        double znamVal = znamenatel.eval(values);
        if (znamVal == 0) {
            throw new DivisionByZeroException();
        }
        return chislitel.eval(values) / znamVal;
    }

    /**
     * Дифференцирование.
     *
     * @param var имя
     * @return новое выражение
     */
    @Override
    public Expression derivative(String var) {
        Expression a = chislitel.derivative(var);
        Expression b = znamenatel.derivative(var);

        Expression chis = new Sub(new Mult(a, znamenatel.clone()),
                new Mult(chislitel.clone(), b));
        Expression znam = new Mult(znamenatel.clone(), znamenatel.clone());

        return new Div(chis, znam);
    }

    /**
     * Упрощение выражения по базе матана.
     *
     * @return новое упрощённое выражение
     */
    @Override
    public Expression simplify() {
        Expression c = chislitel.simplify();
        Expression z = znamenatel.simplify();

        // если обе части числа, вычисляем результат
        if (c.isNumber() && z.isNumber()) {
            Number nc = (Number) c;
            Number nz = (Number) z;
            if (((Number) z).equals(new Number(0))) {
                throw new DivisionByZeroException();
            }
            return new Number(nc.getValue() / nz.getValue());
        }

        // 0 / x = 0
        if (c.isNumber() && c.equals(new Number(0))) {
            return new Number(0);
        }

        // x / 1 = x
        if (z.isNumber() && z.equals(new Number(1))) {
            return c;
        }

        // иначе оставляем деление как есть
        return new Div(c, z);
    }

    /**
     * Создание копий выражения.
     *
     * @return новое выражение Div с клонами операндов
     */
    @Override
    public Expression clone() {
        return new Div(chislitel.clone(), znamenatel.clone());
    }

    /**
     * Преобразует выражение в строку.
     *
     * @return строка
     */
    @Override
    public String toString() {
        return "(" + chislitel.toString() + " / " + znamenatel.toString() + ")";
    }
}

