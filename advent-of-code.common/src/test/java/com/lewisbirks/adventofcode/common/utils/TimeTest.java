package com.lewisbirks.adventofcode.common.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TimeTest {

    @Test
    void nanoRoundToString_nanoSecondResolution() {
        String result = Time.nanoRoundToString(100);
        assertThat(result).isEqualTo("100ns");
    }

    @Test
    void nanoRoundToString_microSecondResolution() {
        String result = Time.nanoRoundToString(100_000);
        assertThat(result).isEqualTo("100µs");
    }

    @Test
    void nanoRoundToString_milliSecondResolution() {
        String result = Time.nanoRoundToString(100_000_000);
        assertThat(result).isEqualTo("100ms");
    }

    @Test
    void nanoRoundToString_secondResolution() {
        String result = Time.nanoRoundToString(100_000_000_000L);
        assertThat(result).isEqualTo("100.000s");
    }
}
