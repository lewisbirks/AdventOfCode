package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.List;
import java.util.function.Supplier;

public final class Day5 extends Day {

    private final Supplier<List<String>> seatsSupplier;

    public Day5() {
        super(5);
        seatsSupplier = CachedSupplier.memoize(this::getInput);
    }

    @Override
    protected Object part1() {
        return seatsSupplier.get().stream().mapToInt(this::seatId).max().orElse(0);
    }

    @Override
    protected Object part2() {
        List<Integer> seats = seatsSupplier.get().stream().map(this::seatId).toList();
        return seats.stream()
            // the next seat doesn't exist but the seat after that does
            .filter(seat -> !seats.contains(seat + 1) && seats.contains(seat + 2))
            .map(i -> ++i)
            .findFirst()
            .orElse(0);
    }

    private int seatId(String seat) {
        int row = 0;
        int column = 0;
        for (int i = 0; i < seat.length(); i++) {
            switch (seat.charAt(i)) {
                case 'F' -> row = row << 1;
                case 'B' -> row = row << 1 | 1;
                case 'R' -> column = column << 1 | 1;
                case 'L' -> column = column << 1;
            }
        }
        return row * 8 + column;
    }

}
