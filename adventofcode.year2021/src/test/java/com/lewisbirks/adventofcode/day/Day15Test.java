package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    private final Day15 underTest = new Day15();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(40L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(315L);
    }
}
