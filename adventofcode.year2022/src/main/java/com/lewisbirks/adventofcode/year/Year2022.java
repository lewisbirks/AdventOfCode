package com.lewisbirks.adventofcode.year;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.common.domain.Year;
import com.lewisbirks.adventofcode.day.Day1;
import com.lewisbirks.adventofcode.day.Day10;
import com.lewisbirks.adventofcode.day.Day11;
import com.lewisbirks.adventofcode.day.Day12;
import com.lewisbirks.adventofcode.day.Day13;
import com.lewisbirks.adventofcode.day.Day14;
import com.lewisbirks.adventofcode.day.Day15;
import com.lewisbirks.adventofcode.day.Day16;
import com.lewisbirks.adventofcode.day.Day17;
import com.lewisbirks.adventofcode.day.Day18;
import com.lewisbirks.adventofcode.day.Day19;
import com.lewisbirks.adventofcode.day.Day2;
import com.lewisbirks.adventofcode.day.Day20;
import com.lewisbirks.adventofcode.day.Day21;
import com.lewisbirks.adventofcode.day.Day22;
import com.lewisbirks.adventofcode.day.Day23;
import com.lewisbirks.adventofcode.day.Day24;
import com.lewisbirks.adventofcode.day.Day25;
import com.lewisbirks.adventofcode.day.Day3;
import com.lewisbirks.adventofcode.day.Day4;
import com.lewisbirks.adventofcode.day.Day5;
import com.lewisbirks.adventofcode.day.Day6;
import com.lewisbirks.adventofcode.day.Day7;
import com.lewisbirks.adventofcode.day.Day8;
import com.lewisbirks.adventofcode.day.Day9;

import java.util.List;

public class Year2022 extends Year<Day> {

    private static final List<Day> DAYS = List.of(
        new Day1(), new Day2(), new Day3(), new Day4(), new Day5(), new Day6(), new Day7(), new Day8(), new Day9(),
        new Day10(), new Day11(), new Day12(), new Day13(), new Day14(), new Day15(), new Day16(), new Day17(),
        new Day18(), new Day19(), new Day20(), new Day21(), new Day22(), new Day23(), new Day24(), new Day25()
    );

    public Year2022() {
        super(2022);
    }

    @Override
    protected List<Day> getDays() {
        return DAYS;
    }
}
