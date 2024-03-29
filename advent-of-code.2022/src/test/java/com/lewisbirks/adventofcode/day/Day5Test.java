package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day5Test {

    private final Day5 underTest = new Day5();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo("CMZ");
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo("MCD");
    }
}
