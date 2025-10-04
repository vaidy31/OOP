package ru.nsu.mzaugolnikov.task333;

/**
 * представляет переменную в математическом выражении.
 */
public class Variable extends Expression {
    /**
     * Имя переменной.
     */
    private final String name;

    /**
     * Конструктор для создания переменной с заданным именем.
     *
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Вычисляет значения переменной по строке.
     *
     * @param values значение переменной.
     * @return кидаем IlliargExecption если переменной нет
     */
    @Override
    public double eval(String values) {
        String[] pairsOfValues = values.split(";");
        for (String pair : pairsOfValues) {
            String[] valsAndSigns = pair.trim().split("=");
            if (valsAndSigns.length == 2 && valsAndSigns[0].trim().equals(name)) {
                return Double.parseDouble(valsAndSigns[1].trim());
            }
        }
        throw new IllegalArgumentException("Variable " + name + " is not defined");
    }

    /**
     * Производная от переменной.
     *
     * @param var имя переменной
     * @return выражение, с производной 1 или 0
     */
    @Override
    public Expression derivative(String var) {
        return new Number(name.equals(var) ? 1 : 0);
    }

    /**
     * упрощение переменной.
     *
     * @return this
     */
    @Override
    public Expression simplify() {
        return this;
    }

    /**
     * создает копию переменной.
     *
     * @return new obj с этим же именем
     */
    @Override
    public Expression clone() {
        return new Variable(name);
    }

    /**
     * геттер для строки.
     *
     * @return строка
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * is variable or not.
     *
     * @return true
     */
    @Override
    public boolean isVariable() {
        return true;
    }

}
