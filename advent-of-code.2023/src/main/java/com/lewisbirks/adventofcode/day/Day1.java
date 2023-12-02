package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.List;

public final class Day1 extends Day {

    private static final List<String> NUMBERS =
            List.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine");

    private List<String> calibrations;

    public Day1() {
        super(1, "Trebuchet?!");
    }

    @Override
    public void preload() {
        calibrations = getInput();
    }

    @Override
    public Object part1() {
        return calibrations.stream()
                .mapToLong(calibration -> buildNumber(calibration, false))
                .sum();
    }

    @Override
    public Object part2() {
        return calibrations.stream()
                .mapToLong(calibration -> buildNumber(calibration, true))
                .sum();
    }

    private static int buildNumber(String calibration, boolean considerStrings) {
        boolean first = false;
        boolean last = false;
        int num = 0;
        for (int i = 0, j = calibration.length() - 1; i < calibration.length(); i++, j--) {
            int digit;
            if (!first) {
                digit = getDigit(calibration, i, considerStrings, true);
                if (digit != -1) {
                    first = true;
                    num += 10 * digit;
                }
            }
            if (!last) {
                digit = getDigit(calibration, j, considerStrings, false);
                if (digit != -1) {
                    num += digit;
                    last = true;
                }
            }
            if (first && last) {
                break;
            }
        }
        return num;
    }

    private static int getDigit(String calibration, int index, boolean considerStrings, boolean fromStart) {
        char digit = calibration.charAt(index);
        if (Character.isDigit(digit)) {
            return digit - '0';
        }
        if (!considerStrings) {
            return -1;
        }
        for (int i = 0; i < NUMBERS.size(); i++) {
            String number = NUMBERS.get(i);
            int offset = fromStart ? index : index + 1 - number.length();
            if (calibration.startsWith(number, offset)) {
                return i;
            }
        }
        return -1;
    }
}
