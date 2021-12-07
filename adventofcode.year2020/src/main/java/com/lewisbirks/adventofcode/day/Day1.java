package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.List;
import java.util.function.Supplier;

public final class Day1 extends Day {

    private final Supplier<List<Long>> expensesSupplier;

    public Day1() {
        super(1);
        expensesSupplier = CachedSupplier.memoize(() -> getInput(Long::parseLong));
    }

    @Override
    protected Object part1() {
        List<Long> expenses = expensesSupplier.get();
        for (int i = 0; i < expenses.size() - 1; i++) {
            Long first = expenses.get(i);
            for (int j = i + 1; j < expenses.size(); j++) {
                Long second = expenses.get(j);
                if (first + second == 2020) {
                    return first * second;
                }
            }
        }
        return null;
    }

    @Override
    protected Object part2() {
        List<Long> expenses = expensesSupplier.get();
        for (int i = 0; i < expenses.size() - 2; i++) {
            Long first = expenses.get(i);
            for (int j = i + 1; j < expenses.size() - 1; j++) {
                Long second = expenses.get(j);
                for (int k = j + 1; k < expenses.size(); k++) {
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
