package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day5Test {

    private final Day5 underTest = new Day5();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(820);
    }
}
