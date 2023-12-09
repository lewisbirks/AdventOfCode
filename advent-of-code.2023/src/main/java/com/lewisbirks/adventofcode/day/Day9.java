package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Day9 extends Day {

    private List<List<Integer>> histories;

    public static void main(String[] args) {
        new Day9().process();
    }

    public Day9() {
        super(9, "Mirage Maintenance");
    }

    @Override
    public void preload() {
        histories =
                getInput(s -> Arrays.stream(s.split(" ")).map(Integer::valueOf).toList());
    }

    @Override
    public Object part1() {
        return histories.stream().mapToLong(this::generateNext).sum();
    }

    @Override
    public Object part2() {
        return null;
    }

    private long generateNext(List<Integer> values) {
        List<Integer> differences = new ArrayList<>();
        boolean isAllZero = true;

        for (int i = 1; i < values.size(); i++) {
            int difference = values.get(i) - values.get(i - 1);
            differences.add(difference);
            if (isAllZero && difference != 0) {
                isAllZero = false;
            }
        }

        int previous = values.get(values.size() - 1);
        if (isAllZero) {
            return previous;
        }

        return generateNext(differences) + previous;
    }
}
