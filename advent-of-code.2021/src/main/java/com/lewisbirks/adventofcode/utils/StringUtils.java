package com.lewisbirks.adventofcode.utils;

import java.math.BigInteger;

public class StringUtils {

    public static String hexToBinary(String hex) {
        return new BigInteger(hex, 16).toString(2);
    }

    public static int binaryToInt(String binary) {
        return Integer.parseInt(binary, 2);
    }

    public static long binaryToLong(String binary) {
        return Long.parseLong(binary, 2);
    }
}
