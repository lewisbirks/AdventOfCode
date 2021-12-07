package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {

    private final Day7 underTest = new Day7();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(37L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(168L);
    }
}
