package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.Arrays;
import java.util.function.Supplier;

public final class Day6 extends Day {
    private static final int NEW_LIFE_TIMER_START = 8;
    private static final int STANDARD_LIFE_TIMER_START = 6;
    private final Supplier<long[]> fishSupplier;

    public Day6() {
        super(6, "Lanternfish");
        fishSupplier = CachedSupplier.memoize(
            () -> {
                long[] numFishPerAge = new long[NEW_LIFE_TIMER_START + 1];
                for (String s : readInput().split(",")) {
                    numFishPerAge[Integer.parseInt(s)]++;
                }
                return numFishPerAge;
            }
        );
    }

    @Override
    protected void preLoad() {
        fishSupplier.get();
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
        long[] numFishPerAge = fishSupplier.get();
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
