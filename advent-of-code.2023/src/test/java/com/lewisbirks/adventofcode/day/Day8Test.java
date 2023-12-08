package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day8Test {

    private final Day8 underTest = new Day8();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    /*
    Test input between the two days operate on different rules, here is the input for part 1

    LLR

    AAA = (BBB, BBB)
    BBB = (AAA, ZZZ)
    ZZZ = (ZZZ, ZZZ)
    */
    @Test
    @Disabled
    void part1() {
        assertThat(underTest.part1()).isEqualTo(6L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(6);
    }
}
