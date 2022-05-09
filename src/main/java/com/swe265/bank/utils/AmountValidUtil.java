package com.swe265.bank.utils;

public class AmountValidUtil {
    public static Double AmountValidUtil(String amount) {
        if ((amount == null) || !amount.matches("([1-9][0-9]*|0)\\.[0-9]{2}")) {
            return null;
        }
        Double validAmount = Double.parseDouble(amount);
        return validAmount;
    }
}
