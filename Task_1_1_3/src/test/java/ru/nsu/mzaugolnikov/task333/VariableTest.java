package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;


class VariableTest {

    @Test
    public void testEval() {
        Variable x = new Variable("x");
        assertEquals(10.0, x.eval("x=10; y=5"));
        assertEquals(5.0, x.eval("y=5; x=5"));
    }

    @Test
    public void testClone() {
        Variable x = new Variable("x");
        Expression clone = x.clone();
        assertNotSame(x, clone);
        assertEquals(x.toString(), clone.toString());
    }

    @Test
    public void testEvalUndefined() {
        Variable x = new Variable("x"); // da eto josko 0_0
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            x.eval("y=3");
        });
        assertTrue(exception.getMessage().contains("x is not defined"));
    }

    @Test
    public void testDerivative() { // берем производные по разныи перменным
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Expression dx = x.derivative("x");
        assertTrue(dx.isNumber());
        assertEquals(1.0, dx.getValue());
        Expression dy = x.derivative("y");
        assertTrue(dy.isNumber());
        assertEquals(0.0, dy.getValue());
    }

    @Test
    public void testSimplify() {
        Variable y = new Variable("x");
        Expression simplified = y.simplify();
        assertSame(y, simplified);
    }

    @Test
    public void testToString() {
        Variable x = new Variable("x");
        assertEquals("x", x.toString());
    }
}