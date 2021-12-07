package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;

import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.function.Supplier;
import java.util.stream.LongStream;

import static java.lang.Long.MAX_VALUE;

public final class Day7 extends DayOf2021 {

    private final Supplier<List<Long>> crabPositions;

    public Day7() {
        super(7);
        crabPositions = CachedSupplier.memoize(
            () -> Arrays.stream(readInput().split(",")).map(Long::parseLong).toList()
        );
    }

    @Override
    protected Object part1() {
        return range().map(target -> costForTarget(target).sum()).min().orElse(MAX_VALUE);
    }

    private LongStream range() {
        LongSummaryStatistics summary = crabPositions.get().stream().mapToLong(i -> i).summaryStatistics();
        return LongStream.rangeClosed(summary.getMin(), summary.getMax());
    }

    private LongStream costForTarget(long target) {
        return crabPositions.get().stream().mapToLong(v -> Math.abs(v - target));
    }

    @Override
    protected Object part2() {
        return range().map(target -> costForTarget(target).map(v -> v * (v + 1) / 2).sum()).min().orElse(MAX_VALUE);
    }
}
