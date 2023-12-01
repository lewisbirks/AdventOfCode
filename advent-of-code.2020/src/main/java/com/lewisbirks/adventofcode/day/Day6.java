package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.Group;
import java.util.Arrays;
import java.util.List;

public final class Day6 extends Day {

    private List<Group> groups;

    public Day6() {
        super(6, "Custom Customs");
    }

    @Override
    protected void preLoad() {
        groups = Arrays.stream(readInput().split("\n\n"))
                .map(answerGroup -> Arrays.asList(answerGroup.split("\n")))
                .map(Group::new)
                .toList();
    }

    @Override
    protected Object part1() {
        return groups.stream().map(Group::numberOfUniqueAnswers).reduce(0L, Long::sum);
    }

    @Override
    protected Object part2() {
        return groups.stream().map(Group::numberOfSharedAnswers).reduce(0L, Long::sum);
    }
}
