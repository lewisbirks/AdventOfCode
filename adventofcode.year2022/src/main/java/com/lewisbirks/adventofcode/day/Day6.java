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
        return findMarker(4);
    }

    @Override
    protected Object part2() {
        return findMarker(14);
    }

    private int findMarker(int markerSize) {
        for (int i = 0; i < input.length() - markerSize - 1; i++) {
            String subsection = input.substring(i, i + markerSize);
            if (subsection.chars().distinct().count() == markerSize) {
                return i + markerSize;
            }
        }
        throw new IllegalArgumentException("No marker found");
    }
}
