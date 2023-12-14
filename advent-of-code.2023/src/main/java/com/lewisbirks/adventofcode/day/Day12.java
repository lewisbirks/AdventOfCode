package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.Arrays;
import java.util.List;

public final class Day12 extends Day {

    public static void main(String[] args) {

    }

    private List<Springs> field;

    public Day12() {
        super(12, "Hot Springs");
    }

    @Override
    public void preload() {
        field = getInput(line -> {
            String[] parts = line.split(" ");
            int[] counts = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();
            return new Springs(parts[0].toCharArray(), counts);
        });
    }

    @Override
    public Object part1() {
        return field.stream().mapToInt(Springs::possibleArrangements).sum();
    }

    @Override
    public Object part2() {
        return null;
    }

    record Springs(char[] springs, int[] counts) {

        public int possibleArrangements() {
            return -1;
        }

        private boolean isPlaceable(int index, int count, char[] springs) {
            for (int i = index + 1; i < index + count; i++) {
                if (springs[i] == '#') {
                    return false;
                }
            }
            return true;
        }
    }
}
