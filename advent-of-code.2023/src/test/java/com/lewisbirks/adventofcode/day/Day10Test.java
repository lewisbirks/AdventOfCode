package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day10Test {

    private final Day10 underTest = new Day10();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(80);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(4);
    }
}
