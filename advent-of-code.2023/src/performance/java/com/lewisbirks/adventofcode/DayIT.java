package com.lewisbirks.adventofcode;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class DayIT {

    @Test
    void benchmark() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(this.getClass().getName() + ".*")
                .mode(Mode.Throughput)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(5)
                .threads(1)
                .measurementIterations(6)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(options).run();
    }

    @State(Scope.Benchmark)
    public static class Plan {

        @Param({"Day1"})
        private String clazz;

        private Day day;

        @Setup(Level.Iteration)
        public void setUp()
                throws NoSuchMethodException, InvocationTargetException, InstantiationException,
                IllegalAccessException, ClassNotFoundException {
            Class<? extends Day> clazz = (Class<? extends Day>) Class.forName("com.lewisbirks.adventofcode.day." + this.clazz);
            day = clazz.getDeclaredConstructor().newInstance();
            day.preload();
        }
    }

    @Benchmark
    public void partOne(Plan plan, Blackhole blackhole) {
        blackhole.consume(plan.day.part1());
    }

    @Benchmark
    public void partTwo(Plan plan, Blackhole blackhole) {
        blackhole.consume(plan.day.part2());
    }
}
