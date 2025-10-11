package ru.nsu.mzaugolnikov.task333;

/**
 * Представление константы в выражении.
 */
public class Number extends Expression {
    /** Значение числа. */
    private final double value;

    /**
     * Возвращает значение числа.
     *
     * @return числовое значение
     */
    public double getValue() {
        return value;
    }

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
     * Сравнивает это число с другим объектом.
     *
     * @param obj объект для сравнения
     * @return true/false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Number other = (Number) obj;
        return Math.abs(this.value - other.value) < 1e-10; // для случаев с 3.00000000000004
    }

    /**
     * Возвращает хэш-код числа.
     * Переопределяем для того, чтобы сравнивать значения чисел в equals, а не их хэшкод
     *
     * @return хэш-код
     */
    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    /**
     * Является ли числом.
     *
     * @param val число
     * @return true/false
     */
    public boolean isValue(double val) {
        return Math.abs(value - val) < 1e-10;
    }
}
