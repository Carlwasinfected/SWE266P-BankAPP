package com.swe265.bank.utils;

import java.math.BigDecimal;

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

    public static int doubleCompare(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.compareTo(b2);
    }


    public static boolean namePasswordCheck(String name) {
        if (name != null && !"".equals(name.trim()) &&
                name.length()>=1 && name.length() <=127 &&
                name.matches("[_\\-\\.0-9a-z]*") ){
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
//        String[] split = "123.45".split("\\.");
//        System.out.println(numericInputsCheck("0123.45"));
        System.out.println(namePasswordCheck("gasjkgpqwehotqp40w8_.-"));
    }
}
