package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.vectors.Vector;

import java.util.List;
import java.util.function.Supplier;

public final class Day2 extends Day {

    private final Supplier<List<Vector>> vectorsSupplier;

    public Day2() {
        super(2, "Dive!");
        vectorsSupplier = CachedSupplier.memoize(() -> getInput(Vector::of));
    }

    @Override
    protected Object part1() {
        int depth = 0;
        int horizontal = 0;

        for (Vector vector : vectorsSupplier.get()) {
            switch (vector.direction()) {
                case UP -> depth -= vector.distance();
                case DOWN -> depth += vector.distance();
                case FORWARD -> horizontal += vector.distance();
            }
        }

        return depth * horizontal;
    }

    @Override
    protected Object part2() {
        int depth = 0;
        int horizontal = 0;
        int aim = 0;

        for (Vector vector : vectorsSupplier.get()) {
            switch (vector.direction()) {
                case UP -> aim -= vector.distance();
                case DOWN -> aim += vector.distance();
                case FORWARD -> {
                    horizontal += vector.distance();
                    depth += aim * vector.distance();
                }
            }
        }

        return depth * horizontal;
    }
}
