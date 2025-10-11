package ru.nsu.mzaugolnikov.task333.Exeptions;

/**
 * Класс для исключения.
 */
public class ExpresionExeption extends RuntimeException {

    /**
     * база.
     *
     * @param message сообщение строковое
     */
    public ExpresionExeption(String message) {
        super(message);
    }
}

