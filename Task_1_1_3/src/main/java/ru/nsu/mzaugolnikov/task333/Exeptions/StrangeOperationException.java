package ru.nsu.mzaugolnikov.task333.Exeptions;

/**
 * Класс для исключения.
 */
public class StrangeOperationException extends ExpresionExeption {

    /**
     * Исключение когда поступает не число.
     */
    public StrangeOperationException() {
        super("Not a number");
    }

    /**
     * Другие случаи.
     * 
     * @param message сообщение
     */
    public StrangeOperationException(String message) {
        super(message);
    }
}