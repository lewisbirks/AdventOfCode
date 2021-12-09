package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    private final Day9 underTest = new Day9();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(15);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(1134L);
    }
}
