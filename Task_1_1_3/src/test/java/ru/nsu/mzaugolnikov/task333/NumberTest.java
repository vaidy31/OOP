package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NumberTest {

    @Test
    public void testEval() {
        Number n = new Number(123123123.5);
        assertEquals(123123123.5, n.eval("x=12; y=3"));
    }

    @Test
    public void testDerivative() {
        Number n = new Number(2222220);
        Expression deriv = n.derivative("x");
        assertTrue(deriv.isNumber());
        assertEquals(0.0, deriv.getValue());
    }

    @Test
    public void testSimplify() {
        Number n = new Number(3.3);
        Expression simplified = n.simplify();

        assertSame(n, simplified);
    }

    @Test
    public void testClone() {
        Number n = new Number(4.2);
        Expression clone = n.clone();
        assertNotSame(n, clone);
        assertEquals(n.getValue(), clone.getValue());
    }

    @Test
    public void testToString() {
        Number n = new Number(2.5);
        assertEquals("2.5", n.toString());
    }

    @Test
    public void testIsNumber() {
        Number n = new Number(1.0);
        assertTrue(n.isNumber());
        assertEquals(1.0, n.getValue());
    }
}
