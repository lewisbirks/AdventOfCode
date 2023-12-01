package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

    private final Day1 underTest = new Day1();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(514579L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(241861950L);
    }
}
