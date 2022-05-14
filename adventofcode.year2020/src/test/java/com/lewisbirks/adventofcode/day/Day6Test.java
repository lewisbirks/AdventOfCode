package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day6Test {

    private final Day6 underTest = new Day6();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(11L);
    }
}
