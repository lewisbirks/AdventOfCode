package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.polymer.PolymerFormula;
import java.util.LongSummaryStatistics;

public final class Day14 extends Day {

    private PolymerFormula formula;

    public Day14() {
        super(14, "Extended Polymerization");
    }

    @Override
    public void preload() {
        formula = PolymerFormula.of(getInput());
    }

    @Override
    public Object part1() {
        return process(10);
    }

    @Override
    public Object part2() {
        return process(40);
    }

    private long process(final int times) {
        LongSummaryStatistics stats = formula.process(times).summaryStats();
        return stats.getMax() - stats.getMin();
    }
}
