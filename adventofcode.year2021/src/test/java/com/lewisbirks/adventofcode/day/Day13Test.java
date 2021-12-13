package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day13Test {

    private final Day13 underTest = new Day13();

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(17);
    }
}
