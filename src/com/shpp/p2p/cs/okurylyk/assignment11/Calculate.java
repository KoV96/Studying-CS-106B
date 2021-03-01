package com.shpp.p2p.cs.okurylyk.assignment11;

import java.util.Stack;

public class Calculate {
    // boolean var if it is some error in runtime
    private boolean isError;
    // stack for calculating and sorting
    private static final Stack<Double> stack = new Stack<>();
    // calculated value of formula
    double result;

    /**
     * Constructor, that evaluating postfix expression by doing arithmetic actions one by one. The main idea of this
     * realization consist in using Stack. With help of stack it is easy to do all action one by one.
     *
     */
    public Calculate(String exp) {
        isError = false;
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == ' ')
                continue;
            if (!isError) {
                if (Character.isDigit(exp.charAt(i))) {
                    double num = 0;
                    while (Character.isDigit(exp.charAt(i))) {
                        num = num * 10 + Double.parseDouble(Character.toString(exp.charAt(i)));
                        i++;
                    }
                    i--;
                    stack.push(num);
                } else if (exp.charAt(i) == '.') {
                    double fnum = stack.pop();
                    int n = 1;
                    i++;
                    while (Character.isDigit(exp.charAt(i))) {
                        fnum += Double.parseDouble(Character.toString(exp.charAt(i))) / Math.pow(10, n);
                        i++;
                        n++;
                    }
                    i--;
                    stack.push(fnum);
                } else if (Character.isLetter(exp.charAt(i))) {
                    double var = Assignment11.variables.get(Character.toString(exp.charAt(i)));
                    stack.push(var);
                } else {
                    double val1 = stack.pop();
                    double val2;
                    if (stack.empty()) {
                        val2 = 0;
                    } else {
                        val2 = stack.pop();
                    }
                    calculate(val1, val2, exp, i);
                }
            } else {
                break;
            }
        }
        if (!isError) {
            result = stack.pop();
        }
        Assignment11.isError = isError;
    }

    /**
     * Making calculation with the following operator and puts result to the stack.
     * @param val1 first value
     * @param val2 second value
     * @param exp postfix formula
     * @param i current step of iteration
     */
    private void calculate(double val1, double val2, String exp, int i) {
        switch (exp.charAt(i)) {
            case '*':
                stack.push(val2 * val1);
                break;
            case '/':
                if (val1 != 0) {
                    stack.push(val2 / val1);
                } else {
                    System.out.println("Error! Division by zero");
                    isError = true;
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
                System.out.println("Incorrect input!");
                isError = true;
        }
    }

    /**
     * Getter for get result
     * @return calculated result
     */
    public double getResult(){
        return result;
    }
}
