package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day21Test {

    private final Day21 underTest = new Day21();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(739785);
    }

    @Test
    void part2() {
        assertThat(underTest.part2()).isEqualTo(444356092776315L);
    }
}
