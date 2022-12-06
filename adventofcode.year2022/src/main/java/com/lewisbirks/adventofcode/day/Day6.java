package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

public final class Day6 extends Day {

    private String input;

    public Day6() {
        super(6, "Tuning Trouble");
    }

    @Override
    protected void preLoad() {
        input = readInput();
    }

    @Override
    protected Object part1() {
        for (int i = 0; i < input.length() - 3; i++) {
            String subsection = input.substring(i, i + 4);
            if (subsection.chars().distinct().count() == 4) {
                return i + 4;
            }
        }
        throw new IllegalArgumentException("No marker found");
    }

    @Override
    protected Object part2() {
        return null;
    }
}
