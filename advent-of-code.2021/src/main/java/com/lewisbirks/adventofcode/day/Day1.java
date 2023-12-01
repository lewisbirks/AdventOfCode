package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.List;
import java.util.stream.IntStream;

public final class Day1 extends Day {

    private List<Long> depths;

    public Day1() {
        super(1, "Sonar Sweep");
    }

    @Override
    protected void preLoad() {
        depths = getInput(Long::parseLong);
    }

    @Override
    protected Object part1() {
        return calculate(depths, 1);
    }

    @Override
    protected Object part2() {
        return calculate(depths, 3);
    }

    private Long calculate(List<Long> input, int windowSize) {
        return IntStream.range(windowSize, input.size())
                .filter(i -> isWindowIncreasing(input, i, windowSize))
                .count();
    }

    private boolean isWindowIncreasing(List<Long> input, int index, int windowSize) {
        long previousWindow = 0;
        long currentWindow = 0;
        for (int offset = 0; offset < windowSize; offset++) {
            previousWindow += input.get(index - offset - 1);
            currentWindow += input.get(index - offset);
        }
        return previousWindow < currentWindow;
    }
}
