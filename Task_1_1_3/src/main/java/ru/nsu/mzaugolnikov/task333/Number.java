package ru.nsu.mzaugolnikov.task333;

/**
 * Представление константы в выражении.
 */
public class Number extends Expression {
    /** Значение числа. */
    private final double value;

    /**
     * Конструктор для создания числа.
     *
     * @param value числовое значение
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Вычисляет значение числа.
     *
     * @param values строка
     * @return значение числа
     */
    @Override
    public double eval(String values) {
        return value;
    }

    /**
     * Производная равна 0.
     *
     * @param var имя переменной
     * @return новое выражение c 0
     */
    @Override
    public Expression derivative(String var) {
        return  new Number(0);
    }

    /**
     * Упрощение числа не меняет его.
     *
     * @return это же число
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * Создает копию числа.
     *
     * @return новое выражение с тем же значением
     */
    @Override
    public Expression clone() {
        return new Number(value);
    }

    /**
     * Преобразует число в строку.
     *
     * @return строковое представление числа
     */
    @Override
    public String toString() {
        return  Double.toString(value);
    }

    /**
     * Проверяет, является ли выражение числом.
     *
     * @return true
     */
    @Override
    public boolean isNumber() {
        return true;
    }

    /**
     * Возвращает значение числа.
     *
     * @return числовое значение
     */
    @Override
    public double getValue() {
        return value;
    }

}
