package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тест для Number.
 */
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
        assertEquals(0.0, ((Number) deriv).getValue());
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
        assertEquals(n.getValue(), ((Number) clone).getValue());
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

    @Test
    void testEqualsSameObject() {
        Number num = new Number(5.0);
        assertEquals(num, num);
    }

    @Test
    void testEqualsWithStrangeSituation() {
        Number n1 = new Number(3.0);
        Number n2 = new Number(3.00000000002);
        assertEquals(n1, n2);
    }

    @Test
    void testHashCode() {
        Number num1 = new Number(5.0);
        Number num2 = new Number(5.0);
        assertEquals(num1.hashCode(), num2.hashCode());

        Number num3 = new Number(7.0);
        assertNotEquals(num1.hashCode(), num3.hashCode());
    }

    @Test
    void testIsValue() {
        Number num = new Number(3.0);
        assertTrue(num.isValue(3.0));
        assertFalse(num.isValue(2.9));
    }
}
