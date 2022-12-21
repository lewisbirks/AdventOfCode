package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Day14 extends Day {

    private static final Point SOURCE = new Point(500, 0);

    private Set<Point> walls;
    private int maxY = 0;

    public Day14() {
        super(14, "Regolith Reservoir");
    }

    @Override
    protected void preLoad() {
        List<String> input = getInput();
        walls = new HashSet<>();
        for (String line : input) {
            String[] coors = line.split(" -> ");
            List<Point> calculated = new ArrayList<>();
            for (String coor : coors) {
                String[] nums = coor.split(",");
                int x = Integer.parseInt(nums[0]);
                int y = Integer.parseInt(nums[1]);
                maxY = Math.max(maxY, y);
                Point current = new Point(x, y);
                if (!calculated.isEmpty()) {
                    Point previous = calculated.get(calculated.size() - 1);
                    if (previous.isVerticalLine(current)) {
                        int minY = Math.min(y, previous.y());
                        int maxY = Math.max(y, previous.y());
                        for (int i = minY + 1; i < maxY; i++) {
                            calculated.add(new Point(x, i));
                        }
                    }
                    if (previous.isHorizontalLine(current)) {
                        int minX = Math.min(x, previous.x());
                        int maxX = Math.max(x, previous.x());
                        for (int i = minX + 1; i < maxX; i++) {
                            calculated.add(new Point(i, y));
                        }
                    }
                }
                calculated.add(current);
            }
            walls.addAll(calculated);
        }
        walls = Collections.unmodifiableSet(walls);
        if (debug) {
            System.out.println("Initial state");
            print(walls, Set.of());
        }
    }

    @Override
    protected Object part1() {
        int sandCount = 0;
        Set<Point> surfaces = new HashSet<>(walls);
        Set<Point> sands = new HashSet<>();
        while (true) {
            Point sand = SOURCE;
            while (true) {
                // will I fall forever?
                if (sand.y() > maxY) {
                    return sandCount;
                }

                Point moved = moveSand(surfaces, sand);
                if (!moved.equals(sand)) {
                    sand = moved;
                    continue;
                }

                surfaces.add(sand);
                sandCount++;
                if (debug) {
                    sands.add(sand);
                }
                break;
            }
            if (debug) {
                System.out.printf("%nAfter %d unit(s) of sand%n", sandCount);
                print(surfaces, sands);
            }
        }
    }

    @Override
    protected Object part2() {
        int sandCount = 0;
        Set<Point> surfaces = new HashSet<>(walls);
        Set<Point> sands = new HashSet<>();
        while (true) {
            Point sand = SOURCE;
            while (true) {
                // generate floor as needed
                Point floor = new Point(sand.x(), maxY + 2);
                surfaces.add(floor);
                surfaces.add(floor.add(Point.LEFT));
                surfaces.add(floor.add(Point.RIGHT));

                Point moved = moveSand(surfaces, sand);
                if (!moved.equals(sand)) {
                    sand = moved;
                    continue;
                }

                // can't move, am I the source block?
                if (sand.equals(SOURCE)) {
                    return ++sandCount;
                }

                surfaces.add(sand);
                sandCount++;
                if (debug) {
                    sands.add(sand);
                }
                break;
            }
            if (debug) {
                System.out.printf("%nAfter %d unit(s) of sand%n", sandCount);
                print(surfaces, sands);
            }
        }
    }

    private static Point moveSand(Set<Point> surfaces, Point sand) {
        // can I drop?
        Point moved = sand.add(Point.DOWN);
        if (!surfaces.contains(moved)) {
            return moved;
        }
        // can't drop but what about go left?
        moved = sand.add(Point.LEFT_DOWN);
        if (!surfaces.contains(moved)) {
            return moved;
        }
        // not down or left, now right?
        moved = sand.add(Point.RIGHT_DOWN);
        if (!surfaces.contains(moved)) {
            return moved;
        }
        return sand;
    }

    private static void print(Set<Point> surfaces, Set<Point> sand) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        Set<Point> points = new HashSet<>(surfaces);
        points.add(SOURCE);
        for (Point wall : points) {
            minX = Math.min(minX, wall.x());
            minY = Math.min(minY, wall.y());
            maxX = Math.max(maxX, wall.x());
            maxY = Math.max(maxY, wall.y());
        }
        for (int y = minY - 2; y <= maxY + 2; y++) {
            for (int x = minX - 2; x <= maxX + 2; x++) {
                Point p = new Point(x, y);
                if (p.equals(SOURCE)) {
                    System.out.print('+');
                } else if (sand.contains(p)) {
                    System.out.print('o');
                } else if (points.contains(p)) {
                    System.out.print('#');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
    }
}
