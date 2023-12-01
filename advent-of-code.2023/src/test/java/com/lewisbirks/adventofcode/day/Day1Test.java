package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day1Test {

    private final Day1 underTest = new Day1();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(142L);
    }

    @Test
    @Disabled
    void part2() {
        assertThat(underTest.part2()).isEqualTo(281L);
    }
}
