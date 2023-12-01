package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day9Test {

    private final Day9 underTest = new Day9();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(88);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(36);
    }
}
