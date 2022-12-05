package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.tuple.Pair;

import java.util.List;
import java.util.function.Supplier;

public final class Day4 extends Day {

    Supplier<List<Pair<Range, Range>>> assignments;

    public Day4() {
        super(4, "Camp Cleanup");
        assignments = CachedSupplier.memoize(() -> getInput(s -> {
            String[] assignments = s.split(",");
            return new Pair<>(Range.of(assignments[0]), Range.of(assignments[1]));
        }));
    }

    @Override
    protected void preLoad() {
        assignments.get();
    }

    @Override
    protected Object part1() {
        return assignments.get().stream()
            .filter(pair -> pair.left().contains(pair.right()) || pair.right().contains(pair.left()))
            .count();
    }

    @Override
    protected Object part2() {
        return assignments.get().stream()
            .filter(pair -> pair.left().overlaps(pair.right()) || pair.right().overlaps(pair.left()))
            .count();
    }



    record Range(int start, int end) {

        public static Range of(String range) {
            String[] indexes = range.split("-");
            return new Range(Integer.parseInt(indexes[0]), Integer.parseInt(indexes[1]));
        }

        public boolean contains(Range other) {
            return this.start <= other.start && this.end >= other.end;
        }

        public boolean overlaps(Range other) {
            return this.start <= other.start && other.start <= this.end
                   || this.end >= other.end && this.start <= other.end;
        }
    }
}
