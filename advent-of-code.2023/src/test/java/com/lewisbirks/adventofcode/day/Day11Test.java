package com.lewisbirks.adventofcode.day;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Day11Test {

    private final Day11 underTest = new Day11();

    @BeforeEach
    void setUp() {
        underTest.preload();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(374L);
    }

    @ParameterizedTest
    @MethodSource("part2Examples")
    void part2(int modifier, long answer) {
        underTest.part2Modifier = modifier;
        assertThat(underTest.part2()).isEqualTo(answer);
    }

    private static Stream<Arguments> part2Examples() {
        return Stream.of(Arguments.arguments(10, 1030L), Arguments.arguments(100, 8410L));
    }
}
