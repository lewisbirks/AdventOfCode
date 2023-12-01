package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.List;

public final class Day5 extends Day {

    private List<String> seats;

    public Day5() {
        super(5, "Binary Boarding");
    }

    @Override
    protected void preLoad() {
        seats = getInput();
    }

    @Override
    protected Object part1() {
        return seats.stream().mapToInt(this::seatId).max().orElse(0);
    }

    @Override
    protected Object part2() {
        List<Integer> ids = seats.stream().map(this::seatId).toList();
        return ids.stream()
            // the next seat doesn't exist but the seat after that does
            .filter(seat -> !ids.contains(seat + 1) && ids.contains(seat + 2))
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
