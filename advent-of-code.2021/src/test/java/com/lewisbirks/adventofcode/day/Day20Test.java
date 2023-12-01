package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day20Test {

    private final Day20 underTest = new Day20();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(35);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(3351);
    }
}
