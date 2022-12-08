package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.stream.IntStream;

public final class Day8 extends Day {

    private int[][] heightMap;

    public Day8() {
        super(8, "Treetop Tree House");
    }

    @Override
    protected void preLoad() {
        heightMap = getInput(row -> IntStream.range(0, row.length()).map(j -> row.charAt(j) - '0').toArray())
            .toArray(new int[][]{});
    }

    @Override
    protected Object part1() {
        int visibleTrees = 0;

        for (int y = 0; y < heightMap.length; y++) {
            for (int x = 0; x < heightMap[y].length; x++) {
                int currentHeight = heightMap[y][x];
                if (isEdge(y, x)) {
                    visibleTrees++;
                    continue;
                }
                if (checkUp(x, y - 1, currentHeight)) {
                    visibleTrees++;
                    continue;
                }
                if (checkDown(x, y + 1, currentHeight)) {
                    visibleTrees++;
                    continue;
                }
                if (checkRight(y, x - 1, currentHeight)) {
                    visibleTrees++;
                    continue;
                }
                if (checkLeft(y, x + 1, currentHeight)) {
                    visibleTrees++;
                }
            }
        }
        return visibleTrees;
    }

    @Override
    protected Object part2() {
        return null;
    }

    private boolean checkUp(int x, int upperBound, int currentHeight) {
        for (int y = upperBound; y >= 0; y--) {
            if (currentHeight <= heightMap[y][x]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDown(int x, int lowerBound, int currentHeight) {
        for (int y = lowerBound; y < heightMap.length; y++) {
            if (currentHeight <= heightMap[y][x]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRight(int y, int upperBound, int currentHeight) {
        for (int x = upperBound; x >= 0; x--) {
            if (currentHeight <= heightMap[y][x]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkLeft(int y, int lowerBound, int currentHeight) {
        for (int x = lowerBound; x < heightMap[y].length; x++) {
            if (currentHeight <= heightMap[y][x]) {
                return false;
            }
        }
        return true;
    }

    private boolean isEdge(int y, int x) {
        return x == 0 || x == heightMap[0].length - 1 || y == 0 || y == heightMap.length - 1;
    }
}
