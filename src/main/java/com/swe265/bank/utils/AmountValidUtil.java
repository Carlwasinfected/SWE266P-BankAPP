package com.swe265.bank.utils;

public class AmountValidUtil {
    Double validAmount = null;
    public void AmountValidUtil(String amount) {
        if ((amount != null) && amount.matches("(0|[1-9][0-9]*)\\.[0-9]{2}")) {
            this.validAmount = Double.parseDouble(amount);
        }
    }

    public Double getAmount() {
        return this.validAmount;
    }
}
