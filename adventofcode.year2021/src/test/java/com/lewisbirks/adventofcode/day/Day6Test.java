package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day6Test {

    private final Day6 underTest = new Day6();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(5934L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(26984457539L);
    }
}
