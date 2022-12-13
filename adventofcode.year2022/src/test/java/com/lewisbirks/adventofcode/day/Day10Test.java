package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day10Test {

    private final Day10 underTest = new Day10();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    @Disabled
    void part1() {
        assertThat(underTest.part1()).isEqualTo(13140L);
    }

    @Test
    @Disabled
    void part2() {
        assertThat(underTest.part2()).isNotNull();
    }
}
