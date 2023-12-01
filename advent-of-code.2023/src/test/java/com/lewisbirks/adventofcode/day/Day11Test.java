package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day11Test {

    private final Day11 underTest = new Day11();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    @Test
    @Disabled
    void part1() {
        assertThat(underTest.part1()).isNotNull();
    }

    @Test
    @Disabled
    void part2() {
        assertThat(underTest.part2()).isNotNull();
    }
}
