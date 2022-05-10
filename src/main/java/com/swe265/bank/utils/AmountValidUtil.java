package com.swe265.bank.utils;

public class AmountValidUtil {

    public static boolean numericInputsCheck(String amount) {
        if (amount == null) {
            return false;
        }
        double v = Double.parseDouble(amount);
        if (v < 0.00 || v > 4294967295.99) {
            return false;
        }
        // when number doesn't have digits
        if (!amount.contains(".")) {
            if (amount.matches("(0|[1-9][0-9]*)")) {
                return true;
            }
            return false;
        }
        // when number has two digits
        String[] strings = amount.split("\\.");
        if (strings.length != 2) {
            return false;
        }
        if (strings[0].matches("(0|[1-9][0-9]*)") && strings[1].matches("[0-9]{2}")) {
            return true;
        } else {
            return false;
        }


    }

    public static void main(String[] args) {
        String[] split = "123.45".split("\\.");
        System.out.println(numericInputsCheck("123.45"));
    }
}
