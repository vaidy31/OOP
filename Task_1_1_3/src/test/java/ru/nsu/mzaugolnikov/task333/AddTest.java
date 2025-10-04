package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

/**
 * Тест для сложения.
 */
public class AddTest {

    @Test
    public void testEval() {
        Expression expr = new Add(new Number(100), new Number(40));
        assertEquals(140.0, expr.eval(""));
        Expression expr2 = new Add(new Variable("x"), new Number(3));
        assertEquals(8.0, expr2.eval("x=5"));
    }

    @Test
    public void testDerivative() {
        Expression expr = new Sub(new Variable("x"), new Number(456));
        Expression deriv = expr.derivative("x");
        assertEquals(1.0, deriv.eval("x=10"));

        Expression expr2 = new Sub(new Number(6747), new Variable("y"));
        Expression deriv2 = expr2.derivative("y");
        assertEquals(-1.0, deriv2.eval("y=3"));
    }

    @Test
    public void testSimplify() {
        Expression expr = new Sub(new Number(0), new Variable("x"));
        Expression simplified = expr.simplify();
        assertEquals("(-1.0 * x)", simplified.toString());

        Expression expr2 = new Sub(new Variable("y"), new Number(0));
        Expression simplified2 = expr2.simplify();
        assertEquals("y", simplified2.toString());

        Expression expr3 = new Sub(new Number(230), new Number(2));
        Expression simplified3 = expr3.simplify();
        assertTrue(simplified3.isNumber());
        assertEquals(228, simplified3.getValue());
    }

    @Test
    public void testDerivativeAdd() {
        Expression expr = new Add(new Variable("x"), new Number(456));
        Expression deriv = expr.derivative("x");
        assertEquals(1.0, deriv.eval("x=10"));

        Expression expr2 = new Add(new Number(6747), new Variable("y"));
        Expression deriv2 = expr2.derivative("y");
        assertEquals(1.0, deriv2.eval("y=3"));
    }

    @Test
    public void testSimplifyZeroLeft() {
        Expression expr = new Add(new Number(0), new Variable("x"));
        Expression simplified = expr.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    public void testSimplifyZeroRight() {
        Expression expr = new Add(new Variable("x"), new Number(0));
        Expression simplified = expr.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    public void testSimplifyVariables() {
        Expression expr = new Add(new Variable("y"), new Variable("x"));
        Expression simplified = expr.simplify();
        assertEquals("(y + x)", simplified.toString());
    }

    @Test
    public void testClone() {
        Expression expr = new Add(new Variable("l"), new Variable("o"));
        Expression clone = expr.clone();
        assertEquals(expr.toString(), clone.toString());
        assertNotSame(expr, clone);
    }
}
