package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    private final Day10 underTest = new Day10();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(26397L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(288957L);
    }
}
