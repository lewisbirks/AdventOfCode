package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day2Test {

    private final Day2 underTest = new Day2();

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
