package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day15Test {

    private final Day15 underTest = new Day15();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(40L);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(315L);
    }
}
