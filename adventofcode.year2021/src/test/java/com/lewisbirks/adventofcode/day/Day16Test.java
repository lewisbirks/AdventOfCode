package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test {

    private final Day16 underTest = new Day16();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(31);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(54L);
    }
}
