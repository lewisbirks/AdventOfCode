package com.lewisbirks.adventofcode.day;

import static java.util.function.Predicate.not;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.common.tuple.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class Day5 extends Day {

    private List<Long> seeds;
    private List<Range> seedToSoil;
    private List<Range> soilToFertilizer;
    private List<Range> fertilizerToWater;
    private List<Range> waterToLight;
    private List<Range> lightToTemperature;
    private List<Range> temperatureToHumidity;
    private List<Range> humidityToLocation;

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
        long best = Long.MAX_VALUE;
        for (Long seed : seeds) {
            long location = seedToLocation(seed);
            best = Math.min(location, best);
        }
        return best;
    }

    @Override
    public Object part2() {
        List<Pair<Long, Long>> ranges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i += 2) {
            ranges.add(new Pair<>(seeds.get(i), seeds.get(i) + seeds.get(i + 1)));
        }

        long location = 0;
        while (true) {
            long seed = locationToSeed(location);
            if (isValidSeed(ranges, seed)) {
                break;
            }
            location++;
        }

        return location;
    }

    private static boolean isValidSeed(List<Pair<Long, Long>> ranges, long seed) {
        for (Pair<Long, Long> range : ranges) {
            if (range.left() <= seed && seed < range.right()) {
                return true;
            }
        }
        return false;
    }

    private long seedToLocation(long seed) {
        long soil = mapToDestination(seedToSoil, seed);
        long fertilizer = mapToDestination(soilToFertilizer, soil);
        long water = mapToDestination(fertilizerToWater, fertilizer);
        long light = mapToDestination(waterToLight, water);
        long temp = mapToDestination(lightToTemperature, light);
        long hum = mapToDestination(temperatureToHumidity, temp);
        return mapToDestination(humidityToLocation, hum);
    }

    private long locationToSeed(long location) {
        long hum = mapToSource(humidityToLocation, location);
        long temp = mapToSource(temperatureToHumidity, hum);
        long light = mapToSource(lightToTemperature, temp);
        long water = mapToSource(waterToLight, light);
        long fertilizer = mapToSource(fertilizerToWater, water);
        long soil = mapToSource(soilToFertilizer, fertilizer);
        return mapToSource(seedToSoil, soil);
    }

    private long mapToDestination(List<Range> ranges, long source) {
        for (Range range : ranges) {
            if (range.isSourceInRange(source)) {
                return range.mapToDestination(source);
            }
        }
        return source;
    }

    private long mapToSource(List<Range> ranges, long destination) {
        for (Range range : ranges) {
            if (range.isDestinationInRange(destination)) {
                return range.mapToSource(destination);
            }
        }
        return destination;
    }

    record Range(long destinationStart, long sourceStart, long length) {

        public long mapToDestination(long source) {
            return destinationStart + (source - sourceStart);
        }

        public long mapToSource(long destination) {
            return sourceStart + (destination - destinationStart);
        }

        public boolean isDestinationInRange(long destination) {
            return destination >= destinationStart && destination <= destinationStart + length;
        }

        public boolean isSourceInRange(long source) {
            return source >= sourceStart && source <= sourceStart + length;
        }
    }
}
