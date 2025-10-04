package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class SubTest {

    @Test
    public void testEval() {
        Expression expr = new Sub(new Number(100), new Number(40));
        assertEquals(60.0, expr.eval(""), 0.001);
        Expression expr2 = new Sub(new Variable("x"), new Number(3));
        assertEquals(2.0, expr2.eval("x=5"), 0.001);
    }

    @Test
    public void testDerivative() {
        Expression expr = new Sub(new Variable("x"), new Number(456));
        Expression deriv = expr.derivative("x");
        assertEquals(1.0, deriv.eval("x=10"), 0.001);

        Expression expr2 = new Sub(new Number(6747), new Variable("y"));
        Expression deriv2 = expr2.derivative("y");
        assertEquals(-1.0, deriv2.eval("y=3"), 0.001);
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
        assertEquals(228, simplified3.getValue(), 0.001);
    }
}
