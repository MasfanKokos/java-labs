package ru.lab;

import java.util.Map;
import java.util.Set;

/**
 * Evaluates mathematical expressions using recursive descent parsing.
 *
 * <p>Supports:
 * <ul>
 *   <li>Integer and decimal numbers</li>
 *   <li>Operators: {@code +}, {@code -}, {@code *}, {@code /}</li>
 *   <li>Parentheses for grouping</li>
 *   <li>Unary minus and plus</li>
 *   <li>Named variables (values provided by caller)</li>
 *   <li>Built-in functions: sin, cos, tan, sqrt, abs, log, log10, pow</li>
 * </ul>
 */
public class ExpressionEvaluator {

    /** Names of all built-in functions recognized by the evaluator. */
    public static final Set<String> FUNCTIONS =
            Set.of("sin", "cos", "tan", "sqrt", "abs", "log", "log10", "pow");

    private String input;
    private int pos;
    private Map<String, Double> variables;

    /**
     * Creates an evaluator with the given variable bindings.
     *
     * @param variables map of variable name to its value; must not be {@code null}
     */
    public ExpressionEvaluator(Map<String, Double> variables) {
        this.variables = variables;
    }

    /**
     * Parses and evaluates the given expression string.
     *
     * @param expression the expression to evaluate
     * @return the numeric result
     * @throws IllegalArgumentException if the expression is syntactically invalid
     * @throws ArithmeticException      if division by zero is attempted
     */
    public double evaluate(String expression) {
        this.input = expression.replaceAll("\\s+", "");
        this.pos = 0;
        double result = parseExpression();
        if (pos != input.length()) {
            throw new IllegalArgumentException(
                    "Unexpected character at position " + pos + ": '" + input.charAt(pos) + "'");
        }
        return result;
    }

    // expression = term (('+' | '-') term)*
    private double parseExpression() {
        double result = parseTerm();
        while (pos < input.length()) {
            char op = input.charAt(pos);
            if (op != '+' && op != '-') break;
            pos++;
            double right = parseTerm();
            result = (op == '+') ? result + right : result - right;
        }
        return result;
    }

    // term = factor (('*' | '/') factor)*
    private double parseTerm() {
        double result = parseFactor();
        while (pos < input.length()) {
            char op = input.charAt(pos);
            if (op != '*' && op != '/') break;
            pos++;
            double right = parseFactor();
            if (op == '/') {
                if (right == 0) throw new ArithmeticException("Division by zero");
                result /= right;
            } else {
                result *= right;
            }
        }
        return result;
    }

    // factor = ('+' | '-') factor | '(' expression ')' | number | identifier
    private double parseFactor() {
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Unexpected end of expression");
        }

        char ch = input.charAt(pos);

        if (ch == '-') { pos++; return -parseFactor(); }
        if (ch == '+') { pos++; return parseFactor(); }

        if (ch == '(') {
            pos++;
            double result = parseExpression();
            if (pos >= input.length() || input.charAt(pos) != ')') {
                throw new IllegalArgumentException("Missing closing parenthesis");
            }
            pos++;
            return result;
        }

        if (Character.isDigit(ch) || ch == '.') {
            return parseNumber();
        }

        if (Character.isLetter(ch)) {
            return parseIdentifier();
        }

        throw new IllegalArgumentException(
                "Unexpected character at position " + pos + ": '" + ch + "'");
    }

    private double parseNumber() {
        int start = pos;
        int dotCount = 0;
        while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
            if (input.charAt(pos) == '.') dotCount++;
            pos++;
        }
        if (dotCount > 1) {
            throw new IllegalArgumentException("Invalid number format at position " + start);
        }
        return Double.parseDouble(input.substring(start, pos));
    }

    private double parseIdentifier() {
        int start = pos;
        while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
            pos++;
        }
        String name = input.substring(start, pos);

        if (pos < input.length() && input.charAt(pos) == '(') {
            pos++;
            double arg1 = parseExpression();
            double arg2 = 0;
            boolean twoArgs = false;
            if (pos < input.length() && input.charAt(pos) == ',') {
                pos++;
                arg2 = parseExpression();
                twoArgs = true;
            }
            if (pos >= input.length() || input.charAt(pos) != ')') {
                throw new IllegalArgumentException("Missing closing parenthesis in function '" + name + "'");
            }
            pos++;
            return applyFunction(name, arg1, arg2, twoArgs);
        }

        if (variables.containsKey(name)) {
            return variables.get(name);
        }

        throw new IllegalArgumentException("Unknown variable: '" + name + "'");
    }

    /**
     * Applies a named built-in function to its argument(s).
     *
     * @param name     function name
     * @param arg1     first argument
     * @param arg2     second argument (used only for {@code pow})
     * @param twoArgs  {@code true} if a second argument was provided
     * @return result of the function
     * @throws IllegalArgumentException if the function name is unknown or argument count is wrong
     */
    private double applyFunction(String name, double arg1, double arg2, boolean twoArgs) {
        return switch (name) {
            case "sin"   -> Math.sin(arg1);
            case "cos"   -> Math.cos(arg1);
            case "tan"   -> Math.tan(arg1);
            case "sqrt"  -> Math.sqrt(arg1);
            case "abs"   -> Math.abs(arg1);
            case "log"   -> Math.log(arg1);
            case "log10" -> Math.log10(arg1);
            case "pow"   -> {
                if (!twoArgs) throw new IllegalArgumentException("pow() requires two arguments");
                yield Math.pow(arg1, arg2);
            }
            default -> throw new IllegalArgumentException("Unknown function: '" + name + "'");
        };
    }
}
