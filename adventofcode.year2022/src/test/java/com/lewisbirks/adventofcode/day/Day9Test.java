package com.lewisbirks.adventofcode.day;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    private final Day9 underTest = new Day9();

    @BeforeEach
    void setUp() {
        underTest.preLoad();
    }

    @Test
    void part1() {
        assertThat(underTest.part1()).isEqualTo(13);
    }

    @Test
    @Disabled
    void part2() {
        assertThat(underTest.part2()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("nextToCoordinates")
    void nextTo(int x, int y) {
        var source = new Day9.Point(0,0);
        var other = new Day9.Point(x, y);
        assertThat(source.nextTo(other)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("nextToCoordinates")
    void notNextTo(int x, int y) {
        var source = new Day9.Point(0,0);
        var other = new Day9.Point(x + 10, y + 10);
        assertThat(source.nextTo(other)).isFalse();
    }

    private static Stream<Arguments> nextToCoordinates() {
        return Stream.of(
            Arguments.of(0,0),
            Arguments.of(0,1),
            Arguments.of(1,0),
            Arguments.of(1,1),
            Arguments.of(0,-1),
            Arguments.of(-1,0),
            Arguments.of(-1,-1),
            Arguments.of(1,-1),
            Arguments.of(-1,1)
        );
    }
}
