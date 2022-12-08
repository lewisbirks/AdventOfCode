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
        int visibleTrees = (heightMap.length * 2) + ((heightMap[0].length - 2) * 2);

        for (int y = 1; y < heightMap.length - 1; y++) {
            for (int x = 1; x < heightMap[y].length - 1; x++) {
                int currentHeight = heightMap[y][x];
                if (checkY(0, y, x, currentHeight)
                    || checkX(0, x, y, currentHeight)
                    || checkY(y + 1, heightMap.length, x, currentHeight)
                    || checkX(x + 1, heightMap[y].length, y, currentHeight)) {
                    visibleTrees++;
                }
            }
        }
        return visibleTrees;
    }

    private boolean checkX(int from, int to, int y, int currentHeight) {
        for (int i = from; i < to; i++) {
            if (heightMap[y][i] >= currentHeight) {
                return false;
            }
        }
        return true;
    }

    private boolean checkY(int from, int to, int x, int currentHeight) {
        for (int i = from; i < to; i++) {
            if (heightMap[i][x] >= currentHeight) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected Object part2() {
        int count = 0;
        for (int y = 0; y < heightMap.length; y++) {
            for (int x = 0; x < heightMap[y].length; x++) {
                count = Math.max(count, scenicScore(y, x));
            }
        }
        return count;
    }

    private int scenicScore(int y, int x) {
        int n = heightMap[y][x];
        return countDown(y, x, n) * countUp(y, x, n) * countLeft(y, x, n) * countRight(y, x, n);
    }

    private int countUp(int y, int x, int n) {
        int count = 0;
        for (int i = y - 1; i >= 0; i--) {
            count++;
            if (heightMap[i][x] >= n) {
                break;
            }
        }
        return count;
    }

    private int countDown(int y, int x, int n) {
        int c = 0;
        for (int i = y + 1; i < heightMap.length; i++) {
            c++;
            if (heightMap[i][x] >= n) {
                break;
            }
        }
        return c;
    }

    private int countLeft(int y, int x, int n) {
        int count = 0;
        for (int i = x - 1; i >= 0; i--) {
            count++;
            if (heightMap[y][i] >= n) {
                break;
            }
        }
        return count;
    }

    private int countRight(int y, int x, int n) {
        int count = 0;
        for (int i = x + 1; i < heightMap[y].length; i++) {
            count++;
            if (heightMap[y][i] >= n) {
                break;
            }
        }
        return count;
    }
}
