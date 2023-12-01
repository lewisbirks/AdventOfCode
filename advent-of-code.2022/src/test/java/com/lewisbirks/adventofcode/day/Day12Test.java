package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day12Test {

    private final Day12 underTest = new Day12();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(31);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(29);
    }
}
