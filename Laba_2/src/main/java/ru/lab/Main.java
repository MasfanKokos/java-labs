package ru.lab;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Entry point: reads an expression from stdin, resolves variables, and prints the result.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter expression: ");
        String expression = scanner.nextLine().trim();

        Map<String, Double> variables = new HashMap<>();
        Set<String> varNames = extractVariables(expression);

        for (String varName : varNames) {
            System.out.print("Enter value for '" + varName + "': ");
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                variables.put(varName, value);
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid number for variable '" + varName + "'");
                return;
            }
        }

        try {
            ExpressionEvaluator evaluator = new ExpressionEvaluator(variables);
            double result = evaluator.evaluate(expression);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Extracts variable names from the expression — identifiers that are not built-in functions.
     *
     * @param expression the raw expression string
     * @return ordered set of variable names (preserving first-occurrence order)
     */
    static Set<String> extractVariables(String expression) {
        Set<String> vars = new LinkedHashSet<>();
        Matcher m = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*").matcher(expression);
        while (m.find()) {
            String name = m.group();
            if (!ExpressionEvaluator.FUNCTIONS.contains(name)) {
                vars.add(name);
            }
        }
        return vars;
    }
}
