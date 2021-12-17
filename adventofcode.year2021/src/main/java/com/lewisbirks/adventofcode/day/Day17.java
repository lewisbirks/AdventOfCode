package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.point.Point;

import java.util.function.Supplier;

public final class Day17 extends Day {

    private final Supplier<Target> targetSupplier;

    public Day17() {
        super(17, "Trick Shot");

        targetSupplier = CachedSupplier.memoize(() -> {
            String[] bounds = readInput().replaceAll("[^-|\\d]", " ").trim().split("\\s+");
            int x1 = Integer.parseInt(bounds[0]), x2 = Integer.parseInt(bounds[1]);
            int y1 = Integer.parseInt(bounds[2]), y2 = Integer.parseInt(bounds[3]);
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
        // initial y cannot go greater than the abs value of the lowest point of the box + 1 as otherwise
        // it would just skip over it on the step before it hits
        int yBound = Math.abs(target.bottomRight().y()) + 1;
        for (int y = 0; y < yBound; y++) {
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
        // no point starting at a y velocity lower than the y lower bound of the target as it will just immediately
        // skip once it reaches the next step
        int yUpperBound = Math.abs(target.bottomRight().y()) + 1;
        int yLowerBound = target.bottomRight.y();
        // no point going faster than the x upper bound of the target
        int xUpperBound = target.bottomRight().x() + 1;

        int sum = 0;
        // assume that the target is not behind us
        for (int x = 0; x < xUpperBound; x++) {
            for (int y = yLowerBound; y < yUpperBound; y++) {
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
            if (target.intersects(position)) {
                return true;
            }
            if (target.isPassedBy(position)) {
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
        public boolean intersects(Point point) {
            return point.x() >= topLeft.x()
                   && point.x() <= bottomRight.x()
                   && point.y() <= topLeft.y()
                   && point.y() >= bottomRight.y();
        }

        public boolean isPassedBy(Point point) {
            return point.x() > bottomRight.x() || point.y() < bottomRight.y();
        }
    }
}
