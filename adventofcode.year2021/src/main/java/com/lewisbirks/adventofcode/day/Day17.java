package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.point.Point;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day17 extends Day {

    private static final Pattern TARGET_PATTERN = Pattern.compile(
        "target area: x=?(-?\\d+)\\.\\.?(-?\\d+), y=?(-?\\d+)\\.\\.?(-?\\d+)"
    );
    private final Supplier<Target> targetSupplier;

    public Day17() {
        super(17, "Trick Shot");

        targetSupplier = CachedSupplier.memoize(() -> {
            String input = readInput();
            Matcher matcher = TARGET_PATTERN.matcher(input);
            if (matcher.matches()) {
                throw new IllegalArgumentException("Couldn't parse input: " + input);
            }
            int x1 = Integer.parseInt(matcher.group(1)), x2 = Integer.parseInt(matcher.group(2));
            int y1 = Integer.parseInt(matcher.group(3)), y2 = Integer.parseInt(matcher.group(4));
            return new Target(
                new Point(Math.min(x1, x2), Math.max(y1, y2)), new Point(Math.max(x1, x2), Math.min(y1, y2))
            );
        });
    }

    @Override
    protected Object part1() {
        // brute force
        Target target = targetSupplier.get();
        int maxY = 0;
        for (int y = -1000; y < 1000; y++) {
            maxY = Math.max(maxY, maxHeight(target, y));
        }

        return maxY;
    }

    private int maxHeight(Target target, int yVelocity) {
        int y = 0;
        int maxY = 0;
        while (true) {
            if (y <= target.topLeft().y() && y >= target.bottomRight().y()) {
                return maxY;
            }
            if (y < target.bottomRight().y()) {
                return -1;
            }
            y += yVelocity;
            maxY = Math.max(maxY, y);
            yVelocity--;
        }
    }

    @Override
    protected Object part2() {
        // brute force
        Target target = targetSupplier.get();
        int sum = 0;
        for (int x = 1; x < 5000; x++) {
            for (int y = -1000; y < 1000; y++) {
                if (reachesTarget(target, x, y)) {
                    sum++;
                }
            }
        }

        return sum;
    }

    private boolean reachesTarget(Target target, int xVelocity, int yVelocity) {
        Point position = new Point(0, 0);
        while (true) {
            if (target.isInTarget(position)) {
                return true;
            }
            if (target.isPastTarget(position)) {
                return false;
            }
            position = new Point(position.x() + xVelocity, position.y() + yVelocity);
            if (xVelocity > 0) {
                xVelocity--;
            }
            if (xVelocity < 0) {
                xVelocity++;
            }
            yVelocity--;
        }
    }

    record Target(Point topLeft, Point bottomRight) {
        public boolean isInTarget(Point point) {
            return point.x() >= topLeft.x()
                   && point.x() <= bottomRight.x()
                   && point.y() <= topLeft.y()
                   && point.y() >= bottomRight.y();
        }

        public boolean isPastTarget(Point point) {
            return point.x() > bottomRight.x() || point.y() < bottomRight.y();
        }
    }
}
