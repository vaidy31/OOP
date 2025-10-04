package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MultTest {
    private Expression num0;
    private Expression num1;
    private Expression varX;
    private Expression varY;

    @BeforeEach
    void setUp() {
        num0 = new Number(23);
        num1 = new Number(3);
        varX = new Variable("x");
        varY = new Variable("y");
    }

    @Test
    void testEval_TwoNumbers() {
        Expression mult = new Mult(num0, num1);
        double result = mult.eval("x=5; y=10");
        assertEquals(69.0, result);
    }

    @Test
    void testEval_NumberAndVariable() {
        Expression mult = new Mult(num0, varX);
        double result = mult.eval("x=5; y=10");
        assertEquals(115.0, result);
    }

    @Test
    public void testDerivative() {
        Expression expr = new Mult(new Variable("x"), new Variable("y"));
        Expression derivativeX = expr.derivative("x").simplify();
        Expression derivativeY = expr.derivative("y").simplify();
        assertEquals("y", derivativeX.toString());
        assertEquals("x", derivativeY.toString());
    }

    @Test
    void testEval_TwoVariables() {
        Expression mult = new Mult(varX, varY);
        double result = mult.eval("x=10; y=10");
        assertEquals(100.0, result);
    }

    @Test
    public void testClone() {
        Expression expr = new Mult(new Variable("a"), new Variable("b"));
        Expression clone = expr.clone();
        assertEquals(expr.toString(), clone.toString());
        assertNotSame(expr, clone);
    }

    @Test
    public void testToString() {
        Expression expr = new Mult(new Number(2), new Number(3));
        assertEquals("(2.0 * 3.0)", expr.toString());
    }

    @Test
    public void testSimplifyNumbers() {
        Expression expr = new Mult(new Number(12), new Number(12));
        Expression simplified = expr.simplify();
        assertTrue(simplified.isNumber());
        assertEquals(144.0, simplified.getValue());
    }

    @Test
    public void testSimplifyZeroLeft() {
        Expression expr = new Mult(new Number(0), new Variable("y"));
        Expression simplified = expr.simplify();
        assertTrue(simplified.isNumber());
        assertEquals(0.0, simplified.getValue());
    }

    @Test
    public void testSimplifyZeroRight() {
        Expression expr = new Mult(new Variable("y"), new Number(0));
        Expression simplified = expr.simplify();
        assertTrue(simplified.isNumber());
        assertEquals(0.0, simplified.getValue());
    }

    @Test
    public void testSimplifyOneLeft() {
        Expression expr = new Mult(new Number(1), new Variable("y"));
        Expression simplified = expr.simplify();
        assertEquals("y", simplified.toString());
    }

    @Test
    public void testSimplifyOneRight() {
        Expression expr = new Mult(new Variable("x"), new Number(1));
        Expression simplified = expr.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    public void testSimplifyVariables() {
        Expression expr = new Mult(new Variable("l"), new Variable("o"));
        Expression simplified = expr.simplify();
        assertEquals("(l * o)", simplified.toString());
    }


}