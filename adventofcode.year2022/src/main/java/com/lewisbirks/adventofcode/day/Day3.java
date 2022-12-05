package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.List;
import java.util.function.Supplier;

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
        return rucksacks.get().stream().mapToInt(this::calculatePriorityOfDuplicates).sum();
    }

    @Override
    protected Object part2() {
        return null;
    }

    private int calculatePriorityOfDuplicates(String rucksack) {
        String left = rucksack.substring(0, rucksack.length() / 2);
        String right = rucksack.substring(rucksack.length() / 2);
        return left.chars().filter(i -> right.indexOf(i) != -1)
            .distinct()
            .map(c -> (c < 'a' ? c - 'A' + 26 : c - 'a') + 1).sum();
    }

}
