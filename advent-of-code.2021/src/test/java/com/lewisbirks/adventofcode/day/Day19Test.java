package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(Lifecycle.PER_CLASS)
class Day19Test {

    private final Day19 underTest = new Day19();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    @Test
    @Order(1)
    void part1() {
        assertThat(underTest.part1()).isEqualTo(79);
    }

    @Test
    @Order(2)
    void part2() {
        assertThat(underTest.part2()).isEqualTo(3621);
    }
}
