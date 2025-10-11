package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.mzaugolnikov.task333.exeptions.StrangeOperationException;


/**
 * ВНИМАНИЕ МОЗГОВЗРЫВАТЕЛЬНЫЕ ТЕСТЫЕ, НЕ ДЛЯ СЛАБОНЕРВНЫХ.
 */
class ExpressionTest {
    @Test
    public void testIsNumberAndGetValue() {
        Expression num = new Number(123.0);
        Expression var = new Variable("x");

        assertTrue(num.isNumber());
        assertEquals(123.0, num.getValue());

        assertFalse(var.isNumber());
        assertThrows(StrangeOperationException.class, var::getValue);
    }

}