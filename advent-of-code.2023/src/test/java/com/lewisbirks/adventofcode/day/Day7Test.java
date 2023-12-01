package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Day7Test {

    private final Day7 underTest = new Day7();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    @Disabled
    void part1() {
        assertThat(underTest.part1()).isNotNull();
    }

    @Test
    @Disabled
    void part2() {
        assertThat(underTest.part2()).isNotNull();
    }
}
