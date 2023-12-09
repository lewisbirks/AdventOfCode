package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

public final class Day9 extends Day {

    private long[][] histories;

    public static void main(String[] args) {
        new Day9().process();
    }

    public Day9() {
        super(9, "Mirage Maintenance");
    }

    @Override
    public void preload() {
        histories = getInput(s -> {
                    String[] chunks = s.split(" ");
                    long[] history = new long[chunks.length];
                    for (int i = 0; i < chunks.length; i++) {
                        history[i] = Long.parseLong(chunks[i]);
                    }
                    return history;
                })
                .toArray(long[][]::new);
    }

    @Override
    public Object part1() {
        long sum = 0L;
        for (int i = 0; i < histories.length; i++) {
            sum += generateNext(histories[i]);
        }
        return sum;
    }

    @Override
    public Object part2() {
        long sum = 0L;
        for (int i = 0; i < histories.length; i++) {
            sum += generateFirst(histories[i]);
        }
        return sum;
    }

    private long generateNext(long[] values) {
        int size = values.length;
        long[] differences = new long[values.length - 1];
        boolean isAllZero = true;

        for (int i = 1; i < size; i++) {
            long difference = values[i] - values[i - 1];
            differences[i - 1] = difference;
            if (isAllZero && difference != 0) {
                isAllZero = false;
            }
        }

        long previous = values[size - 1];
        if (isAllZero) {
            return previous;
        }

        return generateNext(differences) + previous;
    }

    private long generateFirst(long[] values) {
        int size = values.length;
        long[] differences = new long[values.length - 1];

        for (int i = size - 1, j = 0; i >= 1; i--, j++) {
            long difference = values[i] - values[i - 1];
            differences[j] = difference;
        }

        return values[0] - generateNext(differences);
    }
}
