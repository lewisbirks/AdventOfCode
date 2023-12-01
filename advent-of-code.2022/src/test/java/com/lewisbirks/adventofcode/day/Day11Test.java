package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day11Test {

    private final Day11 underTest = new Day11();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(10605L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(2713310158L);
    }
}
