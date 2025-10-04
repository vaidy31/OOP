package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * ТЕсты ради деления.
 */
class DivTest {
    @Test
    public void testEval() {
        Expression expr = new Div(new Number(6), new Number(2));
        assertEquals(3.0, expr.eval(""));

        Expression expr2 = new Div(new Variable("x"), new Number(2));
        assertEquals(2.5, expr2.eval("x=5"));
    }

    @Test
    public void testDerivative() {
        Expression expr = new Div(new Variable("x"), new Variable("y"));
        Expression derX = expr.derivative("x");
        Expression derY = expr.derivative("y");

        assertEquals("(((1.0 * y) - (x * 0.0)) / (y * y))", derX.toString());
        assertEquals("(((0.0 * y) - (x * 1.0)) / (y * y))", derY.toString());
    }

    @Test
    public void testSimplify() {
        Expression expr = new Div(new Number(0), new Variable("x"));
        assertEquals("0.0", expr.simplify().toString());

        Expression expr2 = new Div(new Variable("x"), new Number(1));
        assertEquals("x", expr2.simplify().toString());
    }

    @Test
    public void testCloneAndToString() {
        Expression expr = new Div(new Variable("x"), new Number(2));
        Expression clone = expr.clone();
        assertEquals(expr.toString(), clone.toString());
    }
}