package com.lewisbirks.adventofcode.common.domain;

import static com.lewisbirks.adventofcode.common.utils.Time.nanoRoundToString;

import com.lewisbirks.adventofcode.common.resource.ResourceUtil;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class Day implements Comparable<Day> {

    private final int num;
    private final String name;

    protected final boolean debug;

    public Day(int num, String name) {
        this.num = num;
        this.name = name;
        this.debug = Boolean.getBoolean("debug");
    }

    public void process() {
        System.out.printf("Day %02d: %s%n", num, name);
        try {
            long start, end;
            Object result;

            preload();
            start = System.nanoTime();
            result = part1();
            end = System.nanoTime();
            if (result == null) {
                System.out.println("\r\tNot implemented... skipping");
                return;
            }
            System.out.printf("\r\tPart %d: %s (%s)%n", 1, result, nanoRoundToString(end - start));

            preload();
            start = System.nanoTime();
            result = part2();
            end = System.nanoTime();
            if (result == null) {
                System.out.println("\r\tNot implemented... skipping");
                return;
            }
            System.out.printf("\r\tPart %d: %s (%s)%n", 2, result, nanoRoundToString(end - start));
        } catch (Exception e) {
            System.err.printf("Failed to process day %02d%n", num);
            e.printStackTrace();
        }
    }

    public abstract void preload();

    public abstract Object part1();

    public abstract Object part2();

    protected <I> List<I> getInput(Function<String, I> transformer) {
        return getInput(transformer, Collectors.toUnmodifiableList());
    }

    protected <C> C getInput(Collector<String, ?, C> collector) {
        return getInput(Function.identity(), collector);
    }

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
