package com.lewisbirks.adventofcode.common.domain;

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
            preLoad();
            long start, end;
            System.out.print("\tPart 1: ");
            start = System.currentTimeMillis();
            Object result = part1();
            end = System.currentTimeMillis();
            System.out.printf("%s (%dms)%n", result, end - start);

            preLoad();
            System.out.print("\tPart 2: ");
            start = System.currentTimeMillis();
            result = part2();
            end = System.currentTimeMillis();
            System.out.printf("%s (%dms)%n", result, end - start);
        } catch (Exception e) {
            System.err.printf("Failed to process day %02d%n", num);
            e.printStackTrace();
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
