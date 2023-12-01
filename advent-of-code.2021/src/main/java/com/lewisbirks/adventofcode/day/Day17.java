package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;

public final class Day17 extends Day {

    private Target target;

    public Day17() {
        super(17, "Trick Shot");
    }

    @Override
    protected void preLoad() {
        String[] bounds = readInput().replaceAll("[^-|\\d]", " ").trim().split("\\s+");
        int x1 = Integer.parseInt(bounds[0]), x2 = Integer.parseInt(bounds[1]);
        int y1 = Integer.parseInt(bounds[2]), y2 = Integer.parseInt(bounds[3]);
        target = new Target(
                new Point(Math.min(x1, x2), Math.max(y1, y2)), new Point(Math.max(x1, x2), Math.min(y1, y2)));
    }

    @Override
    protected Object part1() {
        // Tracking the horizontal velocity is redundant here, the probe can be modelled only in the vertical axis.
        // The max height will be when the velocity at height = 0 is equal to the lowest y position of the target + 1,
        // any faster than this it will skip over the target on the next step.
        // Therefore, as the y velocity decrease by 1 on each step we are essentially doing arithmetic sum as at the
        // height = max the velocity = 0 and at height = 0 velocity = max
        int yBound = Math.abs(target.bottomRight().y() + 1);
        return yBound * (yBound + 1) / 2;
    }

    @Override
    protected Object part2() {
        // brute force
        // no point starting at a y velocity lower than the y lower bound of the target as it will just immediately
        // skip once it reaches the next step
        int yUpperBound = Math.abs(target.bottomRight().y());
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

    private record Target(Point topLeft, Point bottomRight) {
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
