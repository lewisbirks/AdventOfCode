package com.lewisbirks.adventofcode.year;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.common.domain.Year;
import com.lewisbirks.adventofcode.day.Day1;

import java.util.List;

public class Year2022 extends Year<Day> {

    private static final List<Day> DAYS = List.of(
        new Day1()
    );

    public Year2022() {
        super(2022);
    }

    @Override
    protected List<Day> getDays() {
        return DAYS;
    }
}
