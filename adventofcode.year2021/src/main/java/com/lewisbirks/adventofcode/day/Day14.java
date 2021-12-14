package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.polymer.PolymerFormula;

import java.util.LongSummaryStatistics;
import java.util.function.Supplier;

public final class Day14 extends Day {

    private final Supplier<PolymerFormula> formulaSupplier;

    public Day14() {
        super(14, "Extended Polymerization");
        formulaSupplier = CachedSupplier.memoize(() -> PolymerFormula.of(getInput()));
    }

    @Override
    protected Object part1() {
        PolymerFormula formula = new PolymerFormula(formulaSupplier.get());
        formula.process(10);
        LongSummaryStatistics stats = formula.summaryStats();
        return stats.getMax() - stats.getMin();
    }

    @Override
    protected Object part2() {
        PolymerFormula formula = new PolymerFormula(formulaSupplier.get());
        formula.process(40);
        LongSummaryStatistics stats = formula.summaryStats();
        return stats.getMax() - stats.getMin();
    }
}
