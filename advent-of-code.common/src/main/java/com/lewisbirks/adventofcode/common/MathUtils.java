package com.lewisbirks.adventofcode.common;

public class MathUtils {

    public static boolean isInt(double d) {
        return d % 1 == 0;
    }

    public static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    public static long gcd(long a, long b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    public static long lcm(long... args) {
        long r = args[0];
        int i = 0;
        while (i < args.length - 1) {
            r = lcm(r, args[++i]);
        }
        return r;
    }
}
