package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.passport.Passport;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Day4 extends Day {

    private List<Passport> passports;

    public Day4() {
        super(4, "Passport Processing");
    }

    @Override
    protected void preLoad() {
        passports = Arrays.stream(readInput().split("\n\n"))
                .map(line -> line.lines().collect(Collectors.joining(" ")).trim())
                .map(Passport::of)
                .toList();
    }

    @Override
    protected Object part1() {
        return passports.stream().filter(Passport::isValid).count();
    }

    @Override
    protected Object part2() {
        return passports.stream().filter(Passport::isStrictValid).count();
    }
}
