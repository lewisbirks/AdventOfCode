package com.lewisbirks.adventofcode.common.domain;

import com.lewisbirks.adventofcode.common.resource.ResourceUtil;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class Day implements Comparable<Day> {

    private final int num;
    private final String name;

    private final boolean performance;
    private final int iterations;
    protected final boolean debug;

    public Day(int num, String name) {
        this.num = num;
        this.name = name;
        this.debug = Boolean.getBoolean("debug");
        this.performance = Boolean.getBoolean("performance");
        this.iterations = this.performance ? Integer.parseInt(System.getProperty("iterations", "100")) : 1;
    }

    public void process() {
        int iterations = performance ? this.iterations : 1;
        System.out.printf("Day %02d: %s%n", num, name);
        try {
            long start, end;
            Object result = null;
            List<Long> timings = new ArrayList<>();

            for (int i = 0; i < iterations; i++) {
                preLoad();
                if (performance) {
                    System.out.printf("\r\tIteration: %d", i + 1);
                }
                start = System.currentTimeMillis();
                result = part1();
                end = System.currentTimeMillis();
                if (result == null) {
                    System.out.println("\r\tNot implemented... skipping");
                    return;
                }
                timings.add(end - start);
            }
            LongSummaryStatistics stats = timings.stream().mapToLong(l -> l).summaryStatistics();
            printResults(1, result, timings, stats);

            timings = new ArrayList<>();
            for (int i = 0; i < iterations; i++) {
                preLoad();
                if (performance){
                    System.out.printf("\r\tIteration: %d", i + 1);
                }
                start = System.currentTimeMillis();
                result = part2();
                end = System.currentTimeMillis();
                if (result == null) {
                    System.out.println("\r\tNot implemented... skipping");
                    return;
                }
                timings.add(end - start);
            }
            stats = timings.stream().mapToLong(l -> l).summaryStatistics();
            printResults(2, result, timings, stats);
        } catch (Exception e) {
            System.err.printf("Failed to process day %02d%n", num);
            e.printStackTrace();
        }
    }

    private void printResults(int part, Object result, List<Long> timings, LongSummaryStatistics stats) {
        if (performance) {
            String extra = "runs: %d, avg: %dms, min: %dms, max: %dms, total: %s".formatted(
                stats.getCount(), (long) stats.getAverage(), stats.getMin(), stats.getMax(),
                DurationFormatUtils.formatDurationHMS(stats.getSum())
            );
            System.out.printf("\r\tPart %d: %s (%s)%n", part, result, extra);
        } else {
            System.out.printf("\r\tPart %d: %s (%dms)%n", part, result, timings.get(timings.size() - 1));
        }
    }

    protected abstract void preLoad();

    protected abstract Object part1();

    protected abstract Object part2();

    protected <I> List<I> getInput(Function<String, I> transformer) {
        return getInput(transformer, Collectors.toUnmodifiableList());
    }

    protected <C> C getInput(Collector<String, ?, C> collector) {return getInput(Function.identity(), collector);}

    protected <I, C> C getInput(Function<String, I> transformer, Collector<I, ?, C> collector) {
        return readInput().lines().map(transformer).collect(collector);
    }

    protected List<String> getInput() {
        return readInput().lines().toList();
    }

    protected String readInput() {
        return readFile();
    }

    private String readFile() {
        String location = "day" + num + ".txt";
        String input = ResourceUtil.getResourceFileAsString(location);
        return Objects.requireNonNull(input, "input must not be null");
    }

    @Override
    public int compareTo(Day o) {
        return Integer.compare(num, o.num);
    }
}
