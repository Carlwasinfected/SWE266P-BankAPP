package com.swe265.bank.utils;

/**
 * @author Can Wang
 * @contact canw7@uci.edu
 * @date 5/9/22
 */
public class StringValidatorUtil {

    private static final String PATTERN = "[_\\\\-\\\\.0-9a-z]";

    /**
     * Account names and passwords are restricted to underscores, hyphens, dots, digits, and lowercase alphabetical
     * characters (each character should match /[_\\-\\.0-9a-z]/). Account names and passwords are to be
     * between 1 and 127 characters long
     * @param input
     * @return boolean
     */
    public static boolean isNameOrPasswordValid(String input) {
        if (input.length() < 1 || input.length() > 127) {
            return false;
        }

        for (char c : input.toCharArray()) {
            if (!String.valueOf(c).matches(PATTERN)) {
                return false;
            }
        }

        return true;
    }
}
