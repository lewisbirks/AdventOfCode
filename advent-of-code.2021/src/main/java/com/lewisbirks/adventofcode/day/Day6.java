package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.Arrays;

public final class Day6 extends Day {
    private static final int NEW_LIFE_TIMER_START = 8;
    private static final int STANDARD_LIFE_TIMER_START = 6;
    private long[] fish;

    public Day6() {
        super(6, "Lanternfish");
    }

    @Override
    protected void preLoad() {
        fish = new long[NEW_LIFE_TIMER_START + 1];
        String[] lines = readInput().split(",");
        Arrays.stream(lines).forEach(s -> fish[Integer.parseInt(s)]++);
    }

    @Override
    protected Object part1() {
        return processLifeForDays(80);
    }

    @Override
    protected Object part2() {
        return processLifeForDays(256);
    }

    private long processLifeForDays(final int numOfDays) {
        long[] numFishPerAge = fish;
        for (int day = 0; day < numOfDays; day++) {
            long[] tmp = new long[NEW_LIFE_TIMER_START + 1];
            System.arraycopy(numFishPerAge, 1, tmp, 0, NEW_LIFE_TIMER_START);
            tmp[NEW_LIFE_TIMER_START] = numFishPerAge[0];
            tmp[STANDARD_LIFE_TIMER_START] += numFishPerAge[0];
            numFishPerAge = tmp;
        }
        return Arrays.stream(numFishPerAge).sum();
    }
}
