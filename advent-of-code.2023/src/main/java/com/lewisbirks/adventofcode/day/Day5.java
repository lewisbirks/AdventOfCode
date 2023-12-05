package com.lewisbirks.adventofcode.day;

import static java.util.function.Predicate.not;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class Day5 extends Day {

    List<Long> seeds;
    List<Range> seedToSoil;
    List<Range> soilToFertilizer;
    List<Range> fertilizerToWater;
    List<Range> waterToLight;
    List<Range> lightToTemperature;
    List<Range> temperatureToHumidity;
    List<Range> humidityToLocation;

    public Day5() {
        super(5, "If You Give A Seed A Fertilizer");
    }

    @Override
    public void preload() {
        List<String> almanac = getInput();

        String seeds = almanac.get(0);
        this.seeds = Arrays.stream(seeds.substring(seeds.indexOf(':') + 2).split(" "))
                .map(Long::valueOf)
                .toList();
        this.seedToSoil = buildRange(almanac, "seed-to-soil");
        this.soilToFertilizer = buildRange(almanac, "soil-to-fertilizer");
        this.fertilizerToWater = buildRange(almanac, "fertilizer-to-water");
        this.waterToLight = buildRange(almanac, "water-to-light");
        this.lightToTemperature = buildRange(almanac, "light-to-temperature");
        this.temperatureToHumidity = buildRange(almanac, "temperature-to-humidity");
        this.humidityToLocation = buildRange(almanac, "humidity-to-location");
    }

    private Stream<String> getSection(List<String> lines, String section) {
        int start = lines.indexOf(section + " map:") + 1;
        return lines.subList(start, lines.size()).stream().takeWhile(not(String::isBlank));
    }

    private List<Range> buildRange(List<String> lines, String section) {
        Stream<String> ranges = getSection(lines, section);
        return ranges.map(range -> {
                    String[] rangeValues = range.split(" ");
                    long dest = Long.parseLong(rangeValues[0]);
                    long source = Long.parseLong(rangeValues[1]);
                    long length = Long.parseLong(rangeValues[2]);
                    return new Range(dest, source, length);
                })
                .toList();
    }

    @Override
    public Object part1() {
        return seeds.stream()
            .mapToLong(source -> source)
            .map(compose())
            .min()
            .orElse(-1);
    }

    @Override
    public Object part2() {
        return null;
    }

    private LongUnaryOperator compose() {
        return mapToDestination(seedToSoil)
            .andThen(mapToDestination(soilToFertilizer))
            .andThen(mapToDestination(fertilizerToWater))
            .andThen(mapToDestination(waterToLight))
            .andThen(mapToDestination(lightToTemperature))
            .andThen(mapToDestination(temperatureToHumidity))
            .andThen(mapToDestination(humidityToLocation));
    }

    private LongUnaryOperator mapToDestination(List<Range> ranges) {
        return source -> {
            for (Range range : ranges) {
                if (range.isSourceInRange(source)) {
                    return range.mapToDestination(source);
                }
            }
            return source;
        };
    }

    record Range(long destinationStart, long sourceStart, long length) {

        public long mapToDestination(long source) {
            return destinationStart + (source - sourceStart);
        }

        public boolean isSourceInRange(long source) {
            return source >= sourceStart && source <= sourceStart + length;
        }
    }
}
