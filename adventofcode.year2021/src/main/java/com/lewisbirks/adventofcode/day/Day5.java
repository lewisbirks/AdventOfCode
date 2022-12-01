package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.point.Point;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public final class Day5 extends Day {

    private final Supplier<List<List<Point>>> coordinateGroupsSupplier;

    public Day5() {
        super(5, "Hydrothermal Venture");
        coordinateGroupsSupplier = CachedSupplier.memoize(() -> getInput(line -> {
            String[] points = line.split(" -> ");
            return Arrays.stream(points).map(Point::of).toList();
        }));
    }

    @Override
    protected void preLoad() {
        coordinateGroupsSupplier.get();
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
        List<List<Point>> coordinateGroups = coordinateGroupsSupplier.get();
        int[] dimensions = getDimensions(coordinateGroups);
        int[][] board = new int[dimensions[0]][dimensions[1]];
        for (List<Point> coordinateGroup : coordinateGroups) {
            // could loop internally to allow for more than 2 points, loop and remove until the size of the list is 1
            Point start = coordinateGroup.get(0);
            Point end = coordinateGroup.get(1);
            if (linesToConsider.test(start, end)) {
                int[] direction = start.getDirection(end);
                int xIncrement = direction[0], yIncrement = direction[1];
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

    private int[] getDimensions(List<List<Point>> points) {
        OptionalInt width = points.stream().flatMap(Collection::stream).mapToInt(Point::x).max();
        OptionalInt height = points.stream().flatMap(Collection::stream).mapToInt(Point::y).max();
        return new int[]{width.orElse(0) + 1, height.orElse(0) + 1};
    }
}
