package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class Day9 extends Day {

    private static final int LARGE_VALUE = 10;

    private final Supplier<int[][]> heightMapSupplier;

    public Day9() {
        super(9, "Smoke Basin");
        heightMapSupplier = CachedSupplier.memoize(
            () -> getInput(line -> IntStream.range(0, line.length())
                .map(i -> Integer.parseInt(line.substring(i, i + 1)))
                .toArray()
            ).toArray(int[][]::new)
        );
    }

    @Override
    protected Object part1() {
        int[][] heightMap = heightMapSupplier.get();
        int sum = 0;
        for (int i = 0; i < heightMap.length; i++) {
            int[] line = heightMap[i];
            for (int j = 0; j < line.length; j++) {
                int left = LARGE_VALUE, right = LARGE_VALUE, up = LARGE_VALUE, down = LARGE_VALUE, middle = line[j];
                if (j != 0) {
                    left = line[j - 1];
                }
                if (j != line.length - 1) {
                    right = line[j + 1];
                }
                if (i != 0) {
                    up = heightMap[i - 1][j];
                }
                if (i != heightMap.length - 1) {
                    down = heightMap[i + 1][j];
                }
                if (isBasin(middle, left, right, up, down)) {
                    sum += middle + 1;
                }
            }
        }
        return sum;
    }

    @Override
    protected Object part2() {
      return null;
    }

    private boolean isBasin(int target, int... toCheck) {
        return Arrays.stream(toCheck).noneMatch(i -> i <= target);
    }
}
