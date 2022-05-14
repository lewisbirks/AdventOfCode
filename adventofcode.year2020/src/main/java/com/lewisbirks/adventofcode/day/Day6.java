package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import com.lewisbirks.adventofcode.model.Group;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public final class Day6 extends Day {

    private final Supplier<List<Group>> groupsSupplier;

    public Day6() {
        super(6, "Custom Customs");
        groupsSupplier = CachedSupplier.memoize(
            () -> Arrays.stream(readInput().split("\n\n"))
                .map(answerGroup -> Arrays.asList(answerGroup.split("\n")))
                .map(Group::new)
                .toList()
        );
    }

    @Override
    protected Object part1() {
        return groupsSupplier.get().stream().map(Group::numberOfUniqueAnswers).reduce(0L, Long::sum);
    }

    @Override
    protected Object part2() {
        return groupsSupplier.get().stream().map(Group::numberOfSharedAnswers).reduce(0L, Long::sum);
    }
}
