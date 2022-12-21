package com.lewisbirks.adventofcode.common.utils;

public class Time {
    public static String nanoRoundToString(long nano) {
        if (nano < 1000) {
            return "%dns".formatted(nano);
        }
        long micro = nano / 1000;
        if (micro < 1000) {
            return "%dµs".formatted(micro);
        }
        long milli = micro / 1000;
        if (milli < 1000) {
            return "%dms".formatted(milli);
        }
        double second = milli / 1000.0;
        return "%.3fs".formatted(second);
    }
}
