package com.lewisbirks.adventofcode.year;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.common.domain.Year;
import com.lewisbirks.adventofcode.day.Day1;
import com.lewisbirks.adventofcode.day.Day2;
import com.lewisbirks.adventofcode.day.Day3;
import com.lewisbirks.adventofcode.day.Day4;
import com.lewisbirks.adventofcode.day.Day5;
import com.lewisbirks.adventofcode.day.Day6;
import com.lewisbirks.adventofcode.day.Day7;
import com.lewisbirks.adventofcode.day.Day8;

import java.util.List;

public class Year2021 extends Year<Day> {

    private static final List<Day> DAYS = List.of(
        new Day1(), new Day2(), new Day3(), new Day4(), new Day5(), new Day6(), new Day7(), new Day8()
    );

    public Year2021() {
        super(2021);
    }

    @Override
    protected List<Day> getDays() {
        return DAYS;
    }
}
