package ru.nsu.mzaugolnikov.task333.exeptions;

/**
 * Класс для исключения.
 */
public class DivisionByZeroException extends ExpresionExeption {

    /**
     * Исключение деления на ноль.
     */
    public DivisionByZeroException() {
        super("You can`t division by zero");
    }
}
