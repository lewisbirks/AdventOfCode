package com.lewisbirks.adventofcode.day;

import static java.lang.Long.MAX_VALUE;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.LongStream;

public final class Day7 extends Day {

    private List<Long> crabPositions;

    public Day7() {
        super(7, "Treachery of Whales");
    }

    @Override
    protected void preLoad() {
        crabPositions =
                Arrays.stream(readInput().split(",")).map(Long::parseLong).toList();
    }

    @Override
    protected Object part1() {
        return range().map(target -> costForTarget(target).sum()).min().orElse(MAX_VALUE);
    }

    private LongStream range() {
        LongSummaryStatistics summary = crabPositions.stream().mapToLong(i -> i).summaryStatistics();
        return LongStream.rangeClosed(summary.getMin(), summary.getMax());
    }

    private LongStream costForTarget(long target) {
        return crabPositions.stream().mapToLong(v -> Math.abs(v - target));
    }

    @Override
    protected Object part2() {
        return range().map(target ->
                        costForTarget(target).map(v -> v * (v + 1) / 2).sum())
                .min()
                .orElse(MAX_VALUE);
    }
}
