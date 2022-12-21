package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day2Test {

    private final Day2 underTest = new Day2();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(150);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(900);
    }
}
