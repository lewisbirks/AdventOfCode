package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.Arrays;

public final class Day11 extends Day {

    public static final int THRESHOLD = 9;
    private int[][] energyLevels;

    public Day11() {
        super(11, "Dumbo Octopus");
    }

    @Override
    protected void preLoad() {
        energyLevels = getInput(line -> Arrays.stream(line.split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
    }

    @Override
    protected Object part1() {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += flashGrid(energyLevels);
            reset(energyLevels);
        }
        return sum;
    }

    @Override
    protected Object part2() {
        int step = 0;
        while (true) {
            step++;
            flashGrid(energyLevels);
            if (reset(energyLevels)) {
                return step;
            }
        }
    }

    private int flashGrid(int[][] energyLevels) {
        int sum = 0;
        for (int y = 0; y < energyLevels.length; y++) {
            for (int x = 0; x < energyLevels[y].length; x++) {
                sum += flash(x, y, energyLevels);
            }
        }
        return sum;
    }

    private int flash(int x, int y, int[][] energyLevels) {
        int maxX = energyLevels[y].length - 1;
        int maxY = energyLevels.length - 1;

        energyLevels[y][x]++;

        if (energyLevels[y][x] != THRESHOLD + 1) {
            return 0;
        }

        int sum = 1;
        for (int i = Math.max(0, y - 1); i <= Math.min(maxY, y + 1); i++) {
            for (int j = Math.max(0, x - 1); j <= Math.min(maxX, x + 1); j++) {
                // exclude current position from recursive flash
                if (x != j || i != y) {
                    sum += flash(j, i, energyLevels);
                }
            }
        }
        return sum;
    }

    private boolean reset(int[][] energyLevels) {
        boolean allFlashed = true;
        for (int y = 0; y < energyLevels.length; y++) {
            for (int x = 0; x < energyLevels[y].length; x++) {
                if (energyLevels[y][x] > THRESHOLD) {
                    energyLevels[y][x] = 0;
                } else {
                    allFlashed = false;
                }
            }
        }
        return allFlashed;
    }
}
