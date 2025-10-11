package ru.nsu.mzaugolnikov.task333.Exeptions;

/**
 * Класс для исключения.
 */
public class StrangeArgumentException extends ExpresionExeption {

    /**
     * Непредвиденный поведение с аргументом.
     */
    public StrangeArgumentException() {
        super("Unexpected argument");
    }
}
