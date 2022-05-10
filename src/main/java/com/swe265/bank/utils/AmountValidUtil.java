package com.swe265.bank.utils;

public class AmountValidUtil {

    public static boolean checkAmount(String amount) {
        if ((amount != null) && amount.matches("(0|[1-9][0-9]*)\\.[0-9]{2}")) {
            return true;
        }
        return false;
    }
}
