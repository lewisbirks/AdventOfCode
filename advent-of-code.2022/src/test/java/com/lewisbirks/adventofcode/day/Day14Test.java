package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day14Test {

    private final Day14 underTest = new Day14();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(24);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(93);
    }
}
