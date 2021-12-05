package com.lewisbirks.adventofcode.day;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiPredicate;

public final class Day5 extends DayOf2021 {


    public Day5() {
        super(5);
    }

    @Override
    protected Object part1() {
        return getOverlapCount((start, end) -> !start.isDiagonalLine(end));
    }

    @Override
    protected Object part2() {
        return getOverlapCount((start, end) -> true);
    }

    private long getOverlapCount(BiPredicate<Point, Point> linesToConsider) {
        List<Pair<Point, Point>> coordinatePairs = getCoordinatePairs();
        Pair<Integer, Integer> dimensions = getDimensions(coordinatePairs);
        int[][] board = new int[dimensions.left()][dimensions.right()];
        for (Pair<Point, Point> pair : coordinatePairs) {
            Point start = pair.left();
            Point end = pair.right();
            if (linesToConsider.test(start, end)) {
                Pair<Integer, Integer> direction = start.getDirection(end);
                int yIncrement = direction.right(), xIncrement = direction.left();
                int x = start.x(), y = start.y();
                int endX = end.x() + xIncrement, endY = end.y() + yIncrement;
                while (x != endX || y != endY) {
                    board[y][x]++;
                    x += xIncrement;
                    y += yIncrement;
                }
            }
        }
        return Arrays.stream(board).flatMapToInt(Arrays::stream).filter(i -> i >= 2).count();

    }

    private Pair<Integer, Integer> getDimensions(List<Pair<Point, Point>> points) {
        OptionalInt width = points.stream().mapToInt(pair -> Math.max(pair.left().x(), pair.right().x())).max();
        OptionalInt height = points.stream().mapToInt(pair -> Math.max(pair.left().y(), pair.right().y())).max();

        return new Pair<>(width.orElse(0) + 1, height.orElse(0) + 1);
    }

    private List<Pair<Point, Point>> getCoordinatePairs() {
        return getInput(line -> {
            String[] points = line.split(" -> ");
            return new Pair<>(Point.of(points[0]), Point.of(points[1]));
        });
    }

    public record Point(int x, int y) {
        public static Point of(String pair) {
            String[] points = pair.split(",");
            return new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1]));
        }

        public boolean isDiagonalLine(Point other) {
            return !(isHorizontalLine(other) || isVerticalLine(other));
        }

        public boolean isVerticalLine(Point other) {
            return this.x == other.x;
        }

        public boolean isHorizontalLine(Point other) {
            return this.y == other.y;
        }

        public Pair<Integer, Integer> getDirection(Point other) {
            int xDirection = 0;
            if (other.x > x) {
                xDirection = 1;
            } else if (other.x < x) {
                xDirection = -1;
            }
            int yDirection = 0;
            if (other.y > y) {
                yDirection = 1;
            } else if (other.y < y) {
                yDirection = -1;
            }
            return new Pair<>(xDirection, yDirection);
        }
    }

    public record Pair<L, R>(L left, R right) {}
}
