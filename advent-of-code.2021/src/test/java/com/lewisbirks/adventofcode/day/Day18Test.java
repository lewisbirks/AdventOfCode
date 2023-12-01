package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    private final Day18 underTest = new Day18();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(4140L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(3993L);
    }
}
