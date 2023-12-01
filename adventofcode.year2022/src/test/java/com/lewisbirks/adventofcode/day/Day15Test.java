package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    private final Day15 underTest = new Day15();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        underTest.yToCheck = 10;
        assertThat(underTest.part1()).isEqualTo(26);
    }

    @Test
    void part2() {
        underTest.max = 20;
        assertThat(underTest.part2()).isEqualTo(56000011);
    }
}
