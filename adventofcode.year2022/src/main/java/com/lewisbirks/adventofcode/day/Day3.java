package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class Day3 extends Day {

    private final Supplier<List<String>> rucksacks;

    public Day3() {
        super(3, "Rucksack Reorganization");
        rucksacks = CachedSupplier.memoize(this::getInput);
    }

    @Override
    protected void preLoad() {
        rucksacks.get();
    }

    @Override
    protected Object part1() {
        return rucksacks.get().stream().mapToInt(Day3::calculatePriorityOfDuplicates).sum();
    }

    @Override
    protected Object part2() {
        List<String> rucksacks = this.rucksacks.get();
        int size = rucksacks.size();
        return IntStream.iterate(0, i -> i < size - 2, i -> i + 3)
            .map(i -> calculatePriorityOfBadges(rucksacks.get(i), rucksacks.get(i + 1), rucksacks.get(i + 2)))
            .sum();
    }

    private static int calculatePriorityOfDuplicates(String rucksack) {
        String left = rucksack.substring(0, rucksack.length() / 2);
        String right = rucksack.substring(rucksack.length() / 2);
        return left.chars().filter(i -> right.indexOf(i) != -1)
            .distinct()
            .map(Day3::calculatePriority)
            .sum();
    }

    public static int calculatePriorityOfBadges(String rucksack1, String rucksack2, String rucksack3) {
        return rucksack1.chars()
            .filter(c -> rucksack2.indexOf(c) != -1 && rucksack3.indexOf(c) != -1)
            .map(Day3::calculatePriority)
            .findFirst().orElseThrow();
    }

    private static int calculatePriority(int c) {
        return (c < 'a' ? c - 'A' + 26 : c - 'a') + 1;
    }

}
