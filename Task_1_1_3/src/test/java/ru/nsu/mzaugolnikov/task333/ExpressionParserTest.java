package ru.nsu.mzaugolnikov.task333;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * tesst.
 */
class ExpressionParserTest {
    @Test
    public void testParseNumberAndVariable() {
        Expression n = ExpressionParser.parse("123123");
        Expression v = ExpressionParser.parse("x");
        assertTrue(n.isNumber());
        assertEquals(123123, ((Number) n).getValue());
        assertEquals("x", v.toString());
    }

    @Test
    public void testParseSimpleExpressions() {
        Expression expr = ExpressionParser.parse("23+32");
        assertEquals("(23.0 + 32.0)", expr.toString());

        Expression expr2 = ExpressionParser.parse("x*y");
        assertEquals("(x * y)", expr2.toString());
    }

    public void testBalanced() {
        assertTrue(ExpressionParser.check("(x + y)"));
        assertTrue(ExpressionParser.check("((a + b) * (c - d))"));
        assertTrue(ExpressionParser.check("a + b"));
        assertTrue(ExpressionParser.check(""));
    }

    @Test
    public void test3232() {
        Expression expr = ExpressionParser.parse("((a + b) * (c - d))");
        assertEquals("((a + b) * (c - d))", expr.toString());
        assertEquals(12.0, expr.eval("a = 2; b = 2; c = 5; d = 2"), 1e-6);
    }

}