package ru.nsu.mzaugolnikov.task333;

import ru.nsu.mzaugolnikov.task333.Exeptions.StrangeArgumentException;
import ru.nsu.mzaugolnikov.task333.Exeptions.StrangeOperationException;

import java.util.HashMap;
import java.util.Map;

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
     * @return кидаем StrangeOperationException если переменной нет
     */
    @Override
    public double eval(String values) {
        Map<String, Double> vars = new HashMap<>();
        for (String pair : values.split(";")) {
            String[] kv = pair.trim().split("=");
            if (kv.length == 2) {
                vars.put(kv[0].trim(), Double.parseDouble(kv[1].trim()));
            }
        }
        if (vars.containsKey(name)) {
            return vars.get(name);
        } else {
            throw new StrangeOperationException(name + " is not defined");
        }
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
