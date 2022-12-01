package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    private final Day1 underTest = new Day1();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(24000);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(45000);
    }
}
