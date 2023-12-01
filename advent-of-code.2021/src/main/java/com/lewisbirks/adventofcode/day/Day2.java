package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.vectors.Vector;
import java.util.List;

public final class Day2 extends Day {

    private List<Vector> vectors;

    public Day2() {
        super(2, "Dive!");
    }

    @Override
    protected void preLoad() {
        vectors = getInput(Vector::of);
    }

    @Override
    protected Object part1() {
        int depth = 0;
        int horizontal = 0;

        for (Vector vector : vectors) {
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

        for (Vector vector : vectors) {
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
