package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public final class Day15 extends Day {

    private int[][] riskLevels;

    public Day15() {
        super(15, "Chiton");
    }

    @Override
    public void preload() {
        riskLevels = getInput(line -> Arrays.stream(line.split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
    }

    @Override
    public Object part1() {
        return shortestPath(riskLevels);
    }

    @Override
    public Object part2() {
        int[][] riskLevels = expand(this.riskLevels, 5);
        return shortestPath(riskLevels);
    }

    private long shortestPath(int[][] riskLevels) {
        int maxX = riskLevels[0].length;
        int maxY = riskLevels.length;
        Point end = new Point(maxX - 1, maxY - 1);
        long[][] totalRiskLevels = new long[maxX][maxY];

        for (long[] row : totalRiskLevels) {
            // large but not too large for overflow
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        totalRiskLevels[0][0] = 0;

        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingLong(o -> totalRiskLevels[o.y()][o.x()]));
        queue.add(new Point(0, 0));

        Point current;
        while (!queue.isEmpty() && !end.equals(current = queue.poll())) {
            for (Point neighbour : getNeighbours(current, maxX, maxY)) {
                int x = neighbour.x();
                int y = neighbour.y();
                long cellRiskLevel = riskLevels[y][x];
                long cellTotalRiskLevel = totalRiskLevels[y][x];
                long newTotalRiskLevel = totalRiskLevels[current.y()][current.x()] + cellRiskLevel;

                if (cellTotalRiskLevel > newTotalRiskLevel) {
                    // already seen so remove to be replaced with the new value
                    if (cellTotalRiskLevel != Integer.MAX_VALUE) {
                        queue.remove(new Point(x, y));
                    }

                    totalRiskLevels[y][x] = newTotalRiskLevel;
                    queue.add(new Point(x, y));
                }
            }
        }
        return totalRiskLevels[maxY - 1][maxX - 1];
    }

    private List<Point> getNeighbours(Point current, int maxX, int maxY) {
        int x = current.x();
        int y = current.y();
        List<Point> neighbours = new ArrayList<>(4);

        if (x > 0) {
            neighbours.add(new Point(x - 1, y));
        }
        if (x < maxX - 1) {
            neighbours.add(new Point(x + 1, y));
        }
        if (y > 0) {
            neighbours.add(new Point(x, y - 1));
        }
        if (y < maxY - 1) {
            neighbours.add(new Point(x, y + 1));
        }
        return neighbours;
    }

    private int[][] expand(int[][] original, int growthFactor) {
        int originalColumnsLength = original.length;
        int originalRowsLength = original[0].length;
        int[][] expanded = new int[originalColumnsLength * growthFactor][originalRowsLength * growthFactor];

        for (int y = 0; y < expanded.length; y++) {
            for (int x = 0; x < expanded[y].length; x++) {
                int increase = (y / originalColumnsLength) + (x / originalRowsLength);
                int originalValue = original[y % originalColumnsLength][x % originalRowsLength];
                expanded[y][x] = originalValue + increase;
                if (expanded[y][x] > 9) {
                    expanded[y][x] -= 9;
                }
            }
        }

        return expanded;
    }
}
