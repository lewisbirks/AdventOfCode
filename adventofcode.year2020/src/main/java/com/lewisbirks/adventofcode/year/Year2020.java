package com.lewisbirks.adventofcode.year;

import com.lewisbirks.adventofcode.common.domain.Year;
import com.lewisbirks.adventofcode.day.Day1;
import com.lewisbirks.adventofcode.day.Day2;
import com.lewisbirks.adventofcode.day.Day3;
import com.lewisbirks.adventofcode.day.DayOf2020;

import java.util.List;

public class Year2020 extends Year<DayOf2020> {

    private static final List<DayOf2020> DAYS = List.of(new Day1(), new Day2(), new Day3());

    public Year2020() {
        super(2020);
    }

    @Override
    protected List<DayOf2020> getDays() {
        return DAYS;
    }
}
