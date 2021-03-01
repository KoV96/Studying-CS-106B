package com.shpp.p2p.cs.okurylyk.assignment11;

import java.util.HashMap;

public class Assignment11 {
    /*
    --------------------FOR TEST-------------------------------
    * I have realized my calculator just for cases like this:
    *
    * "tan (sin (sqrt (8100) ) )^2 + 25/a" "a = 25"
    * "log10 (b)^2 * 2 - a" "a = 25" "b=2"
    * "(1*0+2)*2/(2+2)^sin(45) + 1"
    ------------------------------------------------------------
    Don`t work with this:
    "( 1 + 2 * 3 / 4 ^ 5 + ( -6 * 7 / ( cos ( 8 ) ^ 9 + sin ( tan ( atan ( log2 ( 10 )))) ^ 11 ) / 12 ) * 13 ) + 14 - 15 * 16 ) ) ^ 17
    - 18 + ( -19 ^ ( -20 ) ) * ( -21 ) + 22 ^ 23 + tan ( 24 ) - sqrt ( 25 ) - 26 + 27 ^ 28 / 29 - 30 ) / 31 ^ a + sqrt ( sqrt ( 625 )) )" "a = 36"
    * */
    /**
     * The Hash Map of variables and it`s value.
     */
    public static final HashMap<String, Double> variables = new HashMap<>();
    /**
     * boolean var if it is some error in runtime
     */
    public static boolean isError;

    /**
     * The main body
     */
    public static void main(String[] args) {
        for (int i = 1; i < args.length; i++) {
            checkForVariable(args[i]);
        }
        try {
            PostfixEqualization postfix = new PostfixEqualization(checkFormula(args[0]));
            Calculate calculate = new Calculate(postfix.getPostfix());
            printResult(calculate.getResult());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error, you did not input equation!");
        }
    }

    /**
     * This method trying to find input variable. If it found, variable putting to the Hash Map of variables. If not
     * method catch an exception and ignore it.
     *
     * @param arg second input line from the console which must consist a variable.
     */
    private static void checkForVariable(String arg) {
        try {
            String checkedArg = checkFormula(arg);
            String[] var = parseVar(checkedArg);
            variables.put(var[0], Double.parseDouble(var[1]));
        } catch (ArrayIndexOutOfBoundsException e) {
            /* ignored */
        }
    }

    /**
     * Printing out result if there is no error before.
     *
     * @param result which have calculated Calculate class
     */
    private static void printResult(double result) {
        if (!isError) {
            System.out.println("The result is: " + result);
        }
    }

    /**
     * Correct input formula if it`s need. Delete all spaces
     *
     * @param formula Input formula from console
     * @return Corrected formula
     */
    public static String checkFormula(String formula) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) != ' ') {
                sb.append(formula.charAt(i));
            }
        }
        return sb.toString();
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
}
