package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testEval_TwoVariables() {
        Expression mult = new Mult(varX, varY);
        double result = mult.eval("x=10; y=10");
        assertEquals(100.0, result);
    }

}