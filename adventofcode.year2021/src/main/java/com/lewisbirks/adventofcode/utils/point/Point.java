package com.lewisbirks.adventofcode.utils.point;

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

    public int[] getDirection(Point other) {
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
        return new int[]{xDirection, yDirection};
    }
}
