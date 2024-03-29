package com.lewisbirks.adventofcode.common.coor;

import java.util.List;
import java.util.stream.Stream;

public record Point(int x, int y) {
    public static final Point RIGHT = new Point(1, 0);
    public static final Point LEFT = new Point(-1, 0);
    public static final Point UP = new Point(0, -1);
    public static final Point DOWN = new Point(0, 1);
    public static final Point LEFT_DOWN = LEFT.add(DOWN);
    public static final Point RIGHT_DOWN = RIGHT.add(DOWN);
    public static final Point LEFT_UP = LEFT.add(UP);
    public static final Point RIGHT_UP = RIGHT.add(UP);

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

    public Point getDirection(Point other) {
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
        return new Point(xDirection, yDirection);
    }

    public Point add(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }

    public List<Point> getSurrounding() {
        return Stream.of(UP, DOWN, LEFT, RIGHT, LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN)
                .map(this::add)
                .filter(point -> point.x >= 0 && point.y >= 0)
                .toList();
    }

    public int calculateCartesianDistance(Point other) {
        return Math.abs(other.x - this.x) + Math.abs(other.y - this.y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ']';
    }
}
