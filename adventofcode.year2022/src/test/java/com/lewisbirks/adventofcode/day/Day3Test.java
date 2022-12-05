package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day3Test {

    private final Day3 underTest = new Day3();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(157);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(70);
    }
}