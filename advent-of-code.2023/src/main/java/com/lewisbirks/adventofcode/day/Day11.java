package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Day11 extends Day {

    public static void main(String[] args) {
        new Day11().process();
    }

    int part2Modifier = 1_000_000;
    private char[][] sky;

    public Day11() {
        super(11, "Cosmic Expansion");
    }

    @Override
    public void preload() {
        sky = getInput(String::toCharArray).toArray(char[][]::new);
    }

    @Override
    public Object part1() {
        return sumDistances(getGalaxies(2));
    }

    @Override
    public Object part2() {
        return sumDistances(getGalaxies(part2Modifier));
    }

    private long sumDistances(Point[] galaxies) {
        long sum = 0;
        int numGalaxies = galaxies.length;
        for (int i = 0; i < numGalaxies; i++) {
            Point galaxy = galaxies[i];
            for (int j = i + 1; j < numGalaxies; j++) {
                sum += galaxy.calculateCartesianDistance(galaxies[j]);
            }
        }
        return sum;
    }

    private Point[] getGalaxies(int modifier) {
        Map<Point, Point> originalPointMap = new HashMap<>();
        int maxX = sky[0].length;
        int maxY = sky.length;

        int expansion = 0;
        for (int y = 0; y < maxY; y++) {
            boolean isEmpty = true;
            for (int x = 0; x < maxX; x++) {
                if (sky[y][x] == '#') {
                    isEmpty = false;
                    originalPointMap.put(new Point(x, y), new Point(x, y + expansion));
                }
            }
            if (isEmpty) {
                expansion += modifier - 1;
            }
        }

        List<Point> galaxies = new ArrayList<>(originalPointMap.size());
        expansion = 0;
        for (int x = 0; x < maxX; x++) {
            boolean isEmpty = true;
            for (int y = 0; y < maxY; y++) {
                if (sky[y][x] == '#') {
                    isEmpty = false;
                    Point found = originalPointMap.get(new Point(x, y));
                    galaxies.add(new Point(found.x() + expansion, found.y()));
                }
            }
            if (isEmpty) {
                expansion += modifier - 1;
            }
        }

        return galaxies.toArray(Point[]::new);
    }
}
