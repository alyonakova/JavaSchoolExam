package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;

public class Calculator {

    private static final DecimalFormat OUTPUT_FORMAT =
            new DecimalFormat("#.####", DecimalFormatSymbols.getInstance(Locale.ROOT));

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (statement == null || statement.equals("")) return null;
        String statementNew = getReversePolishNotation(statement);
        if (statementNew == null) return null;
        Deque<Double> stack = new ArrayDeque<>();
        String[] splittedStatement = statementNew.split(" ");
        String current;
        double operandA, operandB, result;
        for (int i = 0; i < splittedStatement.length; i++) {
            try {
            current = splittedStatement[i];
            if (isOperator(current.charAt(0))) {
                if (stack.size() < 2) {
                    System.out.println("Not enough operands for an operation :(");
                    return null;
                }
                operandB = stack.pop();
                operandA = stack.pop();
                if (current.charAt(0) == '+') result = operandA + operandB;
                else if (current.charAt(0) == '-') result = operandA - operandB;
                else if (current.charAt(0) == '*') result = operandA * operandB;
                else result = operandA / operandB;
                stack.push(result);
            } else {
                operandA = Double.parseDouble(current);
                stack.push(operandA);
            }
        } catch (Exception ex) {
                System.out.println("Incorrect character in statement");
                return null;
            }
        }

        if (stack.size() > 1) {
            System.out.println("The number of operands is not equal to the number of operators");
            return null;
        }
        Double finalResult = stack.pop();
        if (Double.isFinite(finalResult)) {
            return OUTPUT_FORMAT.format(finalResult);
        } else {
            return null;
        }
    }

    public static boolean isOperator(char inputChar) {
        switch (inputChar) {
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
        }
        return false;
    }

    public static char getOperatorPriority(char operator) {
        if (operator == '+' || operator == '-') return 1;
        return 2;
    }

    public static String getReversePolishNotation(String inputString) {
        char currentChar;
        char stackTop;
        StringBuilder result = new StringBuilder("");
        StringBuilder stack = new StringBuilder("");
        for (int i = 0; i < inputString.length(); i++) {
            currentChar = inputString.charAt(i);
            if (isOperator(currentChar)) {
                while (stack.length() > 0) {
                    stackTop = stack.charAt(stack.length()-1);
                    if (isOperator(stackTop) && (getOperatorPriority(currentChar) <= getOperatorPriority(stackTop))) {
                        result.append(" ");
                        result.append(stackTop);
                        stack.setLength(stack.length()-1); // stack.pop() :)
                    } else {
                        break;
                    }
                }
                result.append(" ");
                stack.append(currentChar);
            } else if ('(' == currentChar) {
                stack.append(currentChar);
            } else if (')' == currentChar) {
                stackTop = stack.substring(stack.length()-1).charAt(0);
                while (stackTop !='(') {
                    try {
                    if (stack.length() <= 1) {
                        System.out.println("Not equal number of opening and closing parentheses");
                        return null;
                    }
                    result.append(" ").append(stackTop);
                    stack.setLength(stack.length()-1);
                    stackTop = stack.substring(stack.length() - 1).charAt(0);
                    } catch (NullPointerException npe) {
                        return null;
                    }
                }
                stack.setLength(stack.length()-1);
            } else {
                result.append(currentChar);
            }
        }

        while (stack.length() > 0) {
            result.append(" ").append(stack.substring(stack.length()-1));
            stack.setLength(stack.length()-1);
        }
        return  result.toString();
    }
}

