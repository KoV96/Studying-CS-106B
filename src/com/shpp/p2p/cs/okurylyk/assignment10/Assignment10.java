package com.shpp.p2p.cs.okurylyk.assignment10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Assignment10 {
    /**
     * The Hash Map of variables and it`s value.
     */
    private static final HashMap<String, Double> variables = new HashMap<>();
    /**
     * Final result of equation.
     */
    private static double result;
    /**
     * Boolean value that will indicates if we should print out result or there is some error and result is wrong, so
     * it is will not print out
     */
    private static boolean isError = false;

    /**
     * The main body of our program.
     *
     * @param args An array of Strings which consist input lines from console.
     */
    public static void main(String[] args) {
        checkForVariable(args[1]);
        evaluatePostfix(postfixEqualization(args[0]));
        printResult();
    }

    /**
     * This method trying to find input variable. If it found, variable putting to the Hash Map of variables. If not
     * method catch an exception and ignore it.
     *
     * @param arg second input line from the console which must consist a variable.
     */
    private static void checkForVariable(String arg) {
        try {
            String[] var = parseVar(arg);
            variables.put(var[0], Double.parseDouble(var[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            /* ignored */
        }
    }

    /**
     * Printing out result if there is no error before.
     */
    private static void printResult() {
        if (!isError) {
            System.out.println("The result is: " + result);
        }
    }

    /**
     * Method, that evaluating postfix expression by doing arithmetic actions one by one. The main idea of this
     * realization consist in using Stack. With help of stack it is easy to do all action one by one.
     *
     * @param exp The postfix expression of input formula.
     */
    static void evaluatePostfix(ArrayList<Character> exp) {
        isError = false;
        boolean isGood = true;
        Stack<Double> stack = new Stack<>();
        for (Character element : exp) {
            if (isGood) {
                if (Character.isDigit(element)) {
                    stack.push(Double.parseDouble(Character.toString(element)));
                } else if (Character.isLetter(element)) {
                    double var = variables.get(Character.toString(element));
                    stack.push(var);
                } else {
                    double val1 = stack.pop();
                    double val2 = stack.pop();
                    switch (element) {
                        case '*':
                            stack.push(val2 * val1);
                            break;
                        case '/':
                            if (val1 != 0) {
                                stack.push(val2 / val1);
                            } else {
                                System.out.println("Error! Division by zero");
                                isGood = false;
                            }
                            break;
                        case '+':
                            stack.push(val2 + val1);
                            break;
                        case '-':
                            stack.push(val2 - val1);
                            break;
                        case '^':
                            stack.push(Math.pow(val2, val1));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + element);
                    }
                }
            } else {
                isError = true;
                break;
            }
        }
        result = stack.pop();
    }

    /**
     * Interpretation of Shunting-Yard algorithm which put on order base formula for better calculation.
     *
     * @param formula Input formula.
     * @return An array of chars in postfix form.
     */
    private static ArrayList<Character> postfixEqualization(String formula) {
        formula = formula.trim();
        ArrayList<Character> numberStack = new ArrayList<>();
        ArrayList<Character> operatorStack = new ArrayList<>();
        for (int i = 0; i < formula.length(); i++) {
            if (Character.isDigit(formula.charAt(i))) {
                numberStack.add(formula.charAt(i));
            } else {
                if (operatorStack.size() != 0) {
                    for (int j = operatorStack.size() - 1; j >= 0; j--) {
                        if (operatorPriority(formula.charAt(i)) <= operatorPriority(operatorStack.get(j))) {
                            numberStack.add(operatorStack.get(j));
                            operatorStack.remove(j);
                        }
                    }
                }
                operatorStack.add(formula.charAt(i));
            }
        }
        ArrayList<Character> result = new ArrayList<>(numberStack);
        for (int numOfElement = operatorStack.size() - 1; numOfElement >= 0; numOfElement--) {
            result.add(operatorStack.get(numOfElement));
        }
        return result;
    }

    /**
     * Parsing input line which consist variable by two parts: name of variable and it`s value.
     *
     * @param arg Line with variable.
     * @return An array of Strings (name of variable and it`s value).
     */
    private static String[] parseVar(String arg) {
        arg = arg.trim();
        return arg.split("=");
    }

    /**
     * Move apart a priority of operator. '^' with the highest priority, '*' and '/' with height and '+' and '-' with
     * lowest.
     *
     * @param operator An operator that needs to check.
     * @return Number of priority. As higher number as higher priority.
     */
    private static int operatorPriority(char operator) {
        if (operator == '-' || operator == '+') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else {
            return 3;
        }
    }
}
