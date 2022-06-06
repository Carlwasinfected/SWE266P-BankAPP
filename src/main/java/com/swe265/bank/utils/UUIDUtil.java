package com.swe265.bank.utils;

import java.util.UUID;

public class UUIDUtil {
    public static boolean isValidUUID(String uuid) {
        if (uuid == null) {
            return false;
        }
        String regex = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        if (uuid.matches(regex)) {
            return true;
        }
        return false;
    }
}

