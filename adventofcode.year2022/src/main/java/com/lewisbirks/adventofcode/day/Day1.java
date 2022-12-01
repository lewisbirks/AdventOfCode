package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.lewisbirks.adventofcode.common.cache.CachedSupplier.memoize;
import static java.util.Comparator.reverseOrder;

public final class Day1 extends Day {

    Supplier<List<List<Integer>>> calorieGroups;

    public Day1() {
        super(1, "Calorie Counting");
        calorieGroups = memoize(() -> {
            String[] sections = readInput().split("\n\n");
            return Arrays.stream(sections).map(section -> section.lines().map(Integer::valueOf).toList()).toList();
        });
    }

    @Override
    protected Object part1() {
        return getTotals().reduce(Math::max).orElseThrow();
    }

    @Override
    protected Object part2() {
        return getTotals().sorted(reverseOrder()).limit(3).reduce(0, Integer::sum);
    }

    private Stream<Integer> getTotals() {
        return this.calorieGroups.get().stream()
            .map(group -> group.stream().reduce(0, Integer::sum));
    }

}
