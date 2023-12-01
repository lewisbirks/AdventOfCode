package com.lewisbirks.adventofcode.model.point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public record Point3D(int x, int y, int z) implements Comparable<Point3D> {
    public static Point3D of(String line) {
        String[] ints = line.split(",");
        return new Point3D(Integer.parseInt(ints[0]), Integer.parseInt(ints[1]), Integer.parseInt(ints[2]));
    }

    // https://stackoverflow.com/questions/16452383/how-to-get-all-24-rotations-of-a-3-dimensional-array
    public static List<Point3D> sequence(Point3D point) {
        List<Point3D> rotated = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                point = roll(point);
                rotated.add(point);
                for (int k = 0; k < 3; k++) {
                    point = turn(point);
                    rotated.add(point);
                }
            }
            point = roll(turn(roll(point)));
        }
        return Collections.unmodifiableList(rotated);
    }

    private static Point3D roll(Point3D point) {
        return new Point3D(point.x, point.z, -point.y);
    }

    private static Point3D turn(Point3D point) {
        return new Point3D(-point.y, point.x, point.z);
    }

    public Point3D subtract(Point3D other) {
        return new Point3D(x - other.x, y - other.y, z - other.z);
    }

    public Point3D add(Point3D other) {
        return new Point3D(x + other.x, y + other.y, z + other.z);
    }

    public int distance(Point3D other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
    }

    @Override
    public int compareTo(Point3D o) {
        return Arrays.compare(new int[] {x, y, z}, new int[] {o.x, o.y, o.z});
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
