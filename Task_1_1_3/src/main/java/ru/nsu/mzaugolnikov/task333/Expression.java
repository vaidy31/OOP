package ru.nsu.mzaugolnikov.task333;

/**
 * Абстрактный класс для представления математического выражения.
 */
public abstract class Expression {
    /**
     * Символьное дифференцирование выражения по заданной переменной.
     *
     * @param var имя переменной, по которой берется производная
     * @return новое выражение, являющееся производной данного выражения
     */
    public abstract Expression derivative(String var);

    /**
     * Вычисляет численное значение выражения при заданных значениях переменных.
     *
     * @param val строка с присвоениями переменных
     * @return результат вычисления
     */
    public abstract double eval(String val);

    /**
     * Создает копию выражения.
     *
     * @return новый объект Expression, идентичный текущему
     */
    public abstract Expression clone();

    /**
     * Возвращает упрощённое выражение.
     *
     * @return новое упрощённое выражение
     */
    public abstract Expression simplify();

    /**
     * Проверяет, является ли выражение числом.
     *
     * @return true/false
     */
    public boolean isNumber() {
        return false;
    }

    /**
     * Возвращает значение выражения, если это число.
     *
     * @return значение числа
     * @throws UnsupportedOperationException если выражение не является числом
     */
    public double getValue() {
        throw new UnsupportedOperationException("Not a number");
    }

    /**
     * isVariable or not.
     * @return false
     */
    public boolean isVariable() {
        return false;
    }
}
