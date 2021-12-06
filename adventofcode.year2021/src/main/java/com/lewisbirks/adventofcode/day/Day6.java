package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class Day6 extends DayOf2021 {
    private final Supplier<List<Lanternfish>> fishSupplier;

    public Day6() {
        super(6);
        fishSupplier = CachedSupplier.memoize(
            () -> Arrays.stream(readInput().split(",")).map(i -> new Lanternfish(Integer.parseInt(i))).toList());
    }

    @Override
    protected Object part1() {
        List<Lanternfish> fish = new ArrayList<>(fishSupplier.get());
        int dayLimit = 80;
        int day = 0;
        do {
            List<Lanternfish> babies = fish.stream().map(Lanternfish::live).filter(Objects::nonNull).toList();
            fish.addAll(babies);
        } while (++day != dayLimit);
        return fish.size();
    }

    @Override
    protected Object part2() {
        return null;
    }

    public static class Lanternfish {
        private static final int TIMER_START = 6;
        private static final int TIMER_NEW = 8;
        private int timer;

        public Lanternfish(int timer) {
            this.timer = timer;
        }

        public Lanternfish live() {
            Lanternfish baby = null;
            if (timer == 0) {
                timer = TIMER_START;
                baby = new Lanternfish(TIMER_NEW);
            } else {
                timer--;
            }

            return baby;
        }
    }
}
