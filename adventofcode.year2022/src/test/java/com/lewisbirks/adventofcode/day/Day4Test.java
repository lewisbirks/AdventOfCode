package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day4Test {

    private final Day4 underTest = new Day4();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(2L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(4L);
    }
}
