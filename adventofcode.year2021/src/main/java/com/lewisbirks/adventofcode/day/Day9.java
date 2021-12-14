package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.point.Point;

import java.util.ArrayDeque;
import java.util.HashSet;
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
        int[][] heightMap = heightMapSupplier.get();
        long[] largest3 = new long[3];
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
                    keepLargest3(getBasinSize(j, i, heightMap), largest3);
                }
            }
        }
        return largest3[0] * largest3[1] * largest3[2];
    }

    private int getBasinSize(int x, int y, int[][] heightMap) {
        int maxX = heightMap[0].length - 1;
        int maxY = heightMap.length - 1;

        ArrayDeque<Point> points = new ArrayDeque<>();
        HashSet<Point> seen = new HashSet<>();
        points.push(new Point(x, y));
        int size = 0;

        while (!points.isEmpty()) {
            Point point = points.pop();
            if (!seen.add(point)) {
                continue;
            }
            int height = heightMap[point.y()][point.x()];
            if (height == 9) {
                continue;
            }
            int newY, newX;
            // check up
            if (point.y() != maxY) {
                newY = point.y() + 1;
                if (heightMap[newY][point.x()] > height) {
                    points.push(new Point(point.x(), newY));
                }
            }
            // check down
            if (point.y() != 0) {
                newY = point.y() - 1;
                if (heightMap[newY][point.x()] > height) {
                    points.push(new Point(point.x(), newY));
                }
            }
            // check left
            if (point.x() != 0) {
                newX = point.x() - 1;
                if (heightMap[point.y()][newX] > height) {
                    points.push(new Point(newX, point.y()));
                }
            }
            // check right
            if (point.x() != maxX) {
                newX = point.x() + 1;
                if (heightMap[point.y()][newX] > height) {
                    points.push(new Point(newX, point.y()));
                }
            }
            size++;
        }

        return size;
    }

    private boolean isBasin(int target, int... toCheck) {
        for (int i : toCheck) {
            if (i <= target) {
                return false;
            }
        }
        return true;
    }

    private void keepLargest3(int value, long[] largest) {
        if (value > largest[0]) {
            largest[2] = largest[1];
            largest[1] = largest[0];
            largest[0] = value;
        } else if (value > largest[1]) {
            largest[2] = largest[1];
            largest[1] = value;
        } else if (value > largest[2]) {
            largest[2] = value;
        }
    }
}
