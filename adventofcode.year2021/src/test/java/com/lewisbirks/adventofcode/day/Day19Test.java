package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day19Test {

    private final Day19 underTest = new Day19();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(79);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(3621);
    }
}
