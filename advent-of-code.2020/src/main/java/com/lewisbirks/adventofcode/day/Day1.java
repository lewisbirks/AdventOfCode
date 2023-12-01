package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.List;

public final class Day1 extends Day {

    private List<Long> expenses;

    public Day1() {
        super(1, "Report Repair");
    }

    @Override
    public void preload() {
        expenses = getInput(Long::parseLong);
    }

    @Override
    public Object part1() {
        int numExpenses = expenses.size();
        for (int i = 0; i < numExpenses - 1; i++) {
            Long first = expenses.get(i);
            for (int j = i + 1; j < numExpenses; j++) {
                Long second = expenses.get(j);
                if (first + second == 2020) {
                    return first * second;
                }
            }
        }
        return null;
    }

    @Override
    public Object part2() {
        int numExpenses = expenses.size();
        for (int i = 0; i < numExpenses - 2; i++) {
            Long first = expenses.get(i);
            for (int j = i + 1; j < numExpenses - 1; j++) {
                Long second = expenses.get(j);
                for (int k = j + 1; k < numExpenses; k++) {
                    Long third = expenses.get(k);
                    if (first + second + third == 2020) {
                        return first * second * third;
                    }
                }
            }
        }
        return null;
    }
}
