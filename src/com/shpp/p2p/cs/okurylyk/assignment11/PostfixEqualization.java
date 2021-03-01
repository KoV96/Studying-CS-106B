package com.shpp.p2p.cs.okurylyk.assignment11;

import com.shpp.p2p.cs.okurylyk.assignment11.functions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

public class PostfixEqualization {
    // Number stack for postfix equalization
    Stack<Character> numberStack = new Stack<>();
    // Operator stack for postfix equalization
    Stack<Character> operatorStack = new Stack<>();
    // Map with key of function name and value of this key represented as a object of IAction class which consist method Calculate
    static HashMap<String, IAction> calculateFunc = new HashMap<>();

    /**
     * Interpretation of Shunting-Yard algorithm which put on order base formula for better calculation.
     *
     * @param inputLine corrected input formula.
     */
    public PostfixEqualization(String inputLine) {
        fillMap();
        for (int i = 0; i < inputLine.length(); i++) {
            if (Character.isDigit(inputLine.charAt(i))) {
                while (Character.isDigit(inputLine.charAt(i))) {
                    numberStack.push(inputLine.charAt(i));
                    if (i == inputLine.length() - 1) {
                        break;
                    }
                    i++;
                }
                numberStack.push(' ');
                if (i != inputLine.length() - 1)
                i--;
            } else if (Character.isLetter(inputLine.charAt(i))) {
                if (i != inputLine.length() - 1) {
                    if (Character.isLetter(inputLine.charAt(i + 1))) {
                        String res = calculateFunction(inputLine, i);
                        pushStringToStack(res);
                        i = funcExit(inputLine, i);
                    }
                } else {
                    numberStack.push(inputLine.charAt(i));
                    numberStack.push(' ');
                }
            } else if (inputLine.charAt(i) == '(') {
                operatorStack.push(inputLine.charAt(i));
            } else if (inputLine.charAt(i) == ')') {
                while (operatorStack.peek() != '(') {
                    numberStack.push(operatorStack.pop());
                }
                operatorStack.pop();
            } else if (inputLine.charAt(i) == '.' || inputLine.charAt(i) == ',') {
                numberStack.push('.');
                i++;
                while (Character.isDigit(inputLine.charAt(i))) {
                    numberStack.push(inputLine.charAt(i));
                    i++;
                }
                i--;
            } else {
                numberStack.push(' ');
                while (!operatorStack.empty()) {
                    if (operatorPriority(inputLine.charAt(i)) <= operatorPriority(operatorStack.peek())) {
                        numberStack.push(operatorStack.pop());
                    } else break;
                }
                operatorStack.push(inputLine.charAt(i));
            }
        }
        while (!operatorStack.empty()) {
            numberStack.push(operatorStack.pop());
        }
    }

    /**
     * Exit from trigonometric function if it have many left brackets
     * @param inputLine current formula
     * @param i current number of iteration
     * @return needed number of iteration
     */
    private int funcExit(String inputLine, int i) {
        boolean isExit = false;
        while (!isExit) {
            if (inputLine.charAt(i) == ')') {
                if (i == inputLine.length() - 1)
                    break;
                i++;
                if (inputLine.charAt(i) != ')') {
                    isExit = true;
                    i--;
                }
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * Pushing calculated result of trigonometric function to the number stack
     * @param res calculated result of trigonometric function
     */
    private void pushStringToStack(String res) {
        for (int i = 0; i < res.length(); i++) {
            numberStack.push(res.charAt(i));
        }
        numberStack.push(' ');
    }

    /**
     * Calculating trigonometric functions with different arguments
     * @param inputLine current formula
     * @param i current number of iteration
     * @return calculated result (String number)
     */
    private String calculateFunction(String inputLine, int i) {
        ArrayList<String> names = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        double res = 0;
        while (inputLine.charAt(i) != ')') {
            while (inputLine.charAt(i) != '(') {
                sb.append(inputLine.charAt(i));
                i++;
            }
            names.add(sb.toString());
            sb.delete(0, sb.length());
            i++;
            if (Character.isLetter(inputLine.charAt(i)) && Character.isLetter(inputLine.charAt(i + 1))) {
                continue;
            } else {
                if (Character.isLetter(inputLine.charAt(i))) {
                    res = Assignment11.variables.get(Character.toString(inputLine.charAt(i)));
                    i++;
                } else {
                    while (inputLine.charAt(i) != ')') {
                        sb.append(inputLine.charAt(i));
                        i++;
                    }
                    res = Double.parseDouble(sb.toString());
                }
            }
        }
        GetResult getResult = new GetResult();
        for (int j = names.size() - 1; j >= 0; j--) {
            res = getResult.calculateRes(calculateFunc.get(names.get(j).toLowerCase(Locale.ROOT)), res);
        }
        return Double.toString(res);

    }

    /**
     * Fill in Map with trigonometric functions
     */
    private static void fillMap() {
        calculateFunc.put("cos", new Cos());
        calculateFunc.put("sin", new Sin());
        calculateFunc.put("tan", new Tan());
        calculateFunc.put("atan", new Atan());
        calculateFunc.put("sqrt", new Sqrt());
        calculateFunc.put("log10", new Log10());
        calculateFunc.put("log2", new Log2());

    }

    /**
     * Move apart a priority of operator. '^' with the highest priority, '*' and '/' with height and '+' and '-' with
     * lowest.
     *
     * @param operator An operator that needs to check.
     * @return Number of priority. As higher number as higher priority.
     */
    private int operatorPriority(char operator) {
        if (operator == '-' || operator == '+') {
            return 2;
        } else if (operator == '(') {
            return 0;
        } else if (operator == ')') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * Transforms stack into the String
     * @param stack stack that needs to transform
     * @return transformed String
     */
    private String stackToString(Stack stack) {
        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

    /**
     * Getter which return Postfix formula.
     * @return
     */
    public String getPostfix() {
        return stackToString(numberStack);
    }
}
