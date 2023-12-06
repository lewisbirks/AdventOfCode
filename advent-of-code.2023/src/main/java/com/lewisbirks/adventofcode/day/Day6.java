package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day6 extends Day {

    private static final Pattern NUMBERS = Pattern.compile("(\\d+)");

    private List<Race> races;
    private Race race;

    public Day6() {
        super(6, "Wait For It");
    }

    @Override
    public void preload() {
        List<String> document = getInput(line -> line.substring(line.indexOf(":") + 1).trim());

        List<Race> races = new ArrayList<>();

        Matcher times = NUMBERS.matcher(document.get(0));
        Matcher distances = NUMBERS.matcher(document.get(1));
        StringBuilder fullTime = new StringBuilder();
        StringBuilder fullDistance = new StringBuilder();

        while (distances.find() && times.find()) {
            String time = times.group(1);
            String distance = distances.group(1);
            races.add(Race.of(time, distance));
            fullTime.append(time);
            fullDistance.append(distance);
        }

        this.races = Collections.unmodifiableList(races);
        this.race = Race.of(fullTime.toString(), fullDistance.toString());
    }

    @Override
    public Object part1() {
        long product = 1;
        for (Race race : this.races) {
            long better = race.calculateNumberOfBetterOptions();
            if (better > 0) {
                product *= better;
            }
        }
        return product;
    }

    @Override
    public Object part2() {
        return this.race.calculateNumberOfBetterOptions();
    }

    record Race(long time, long record) {
        public static Race of(String time, String record) {
            return new Race(Long.parseLong(time), Long.parseLong(record));
        }

        public long calculateNumberOfBetterOptions() {
            long better = 0;
            for (long i = 1; i < this.time; i++) {
                long remainingTime = this.time - i;
                long distance = i * remainingTime;
                if (distance > record) {
                    better++;
                }
            }
            return better;
        }
    }
}
