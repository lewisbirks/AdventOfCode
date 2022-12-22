package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day8Test {

    private final Day8 underTest = new Day8();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(26L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(61229L);
    }
}
