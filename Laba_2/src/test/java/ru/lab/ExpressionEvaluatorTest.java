package ru.lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorTest {

    private ExpressionEvaluator eval;

    @BeforeEach
    void setUp() {
        eval = new ExpressionEvaluator(Map.of());
    }

    @Test
    void basicAddition() {
        assertEquals(5.0, eval.evaluate("2+3"));
    }

    @Test
    void basicSubtraction() {
        assertEquals(6.0, eval.evaluate("10-4"));
    }

    @Test
    void basicMultiplication() {
        assertEquals(12.0, eval.evaluate("3*4"));
    }

    @Test
    void basicDivision() {
        assertEquals(5.0, eval.evaluate("10/2"));
    }

    @Test
    void operatorPrecedence() {
        assertEquals(14.0, eval.evaluate("2+3*4"));
    }

    @Test
    void parenthesesOverridePrecedence() {
        assertEquals(20.0, eval.evaluate("(2+3)*4"));
    }

    @Test
    void unaryMinus() {
        assertEquals(-2.0, eval.evaluate("-5+3"));
    }

    @Test
    void nestedParentheses() {
        assertEquals(10.0, eval.evaluate("((2+3)*4)/2"));
    }

    @Test
    void decimalNumbers() {
        assertEquals(0.3, eval.evaluate("0.1+0.2"), 1e-9);
    }

    @Test
    void functionSqrt() {
        assertEquals(2.0, eval.evaluate("sqrt(4)"), 1e-9);
    }

    @Test
    void functionSin() {
        assertEquals(0.0, eval.evaluate("sin(0)"), 1e-9);
    }

    @Test
    void functionCos() {
        assertEquals(1.0, eval.evaluate("cos(0)"), 1e-9);
    }

    @Test
    void functionPow() {
        assertEquals(8.0, eval.evaluate("pow(2,3)"), 1e-9);
    }

    @Test
    void functionAbs() {
        assertEquals(5.0, eval.evaluate("abs(-5)"), 1e-9);
    }

    @Test
    void variableSubstitution() {
        ExpressionEvaluator withVars = new ExpressionEvaluator(Map.of("x", 5.0, "y", 3.0));
        assertEquals(8.0, withVars.evaluate("x+y"));
    }

    @Test
    void variableInComplexExpression() {
        ExpressionEvaluator withVars = new ExpressionEvaluator(Map.of("x", 4.0));
        assertEquals(2.0, withVars.evaluate("sqrt(x)"));
    }

    @Test
    void divisionByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> eval.evaluate("5/0"));
    }

    @Test
    void unmatchedParenthesisThrows() {
        assertThrows(IllegalArgumentException.class, () -> eval.evaluate("(2+3"));
    }

    @Test
    void unknownVariableThrows() {
        assertThrows(IllegalArgumentException.class, () -> eval.evaluate("x+1"));
    }

    @Test
    void invalidExpressionThrows() {
        assertThrows(IllegalArgumentException.class, () -> eval.evaluate("2+*3"));
    }

    @Test
    void extractVariablesSkipsFunctions() {
        Set<String> vars = Main.extractVariables("sin(x) + y * cos(z)");
        assertEquals(Set.of("x", "y", "z"), vars);
    }
}
