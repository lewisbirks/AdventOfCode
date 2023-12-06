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
        List<String> document = getInput(l -> l.substring(l.indexOf(":") + 1).trim());

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
            boolean startFound = false;
            boolean endFound = false;
            long start = 0;
            long end = 0;
            for (long i = 1, j = this.time - 1; i < this.time; i++, j--) {
                if (!startFound && calcDistance(i) > record) {
                    startFound = true;
                    start = i;
                }

                if (!endFound && calcDistance(j) > record) {
                    endFound = true;
                    end = j;
                }

                if (startFound && endFound) {
                    break;
                }
            }
            return end - start + 1;
        }

        private long calcDistance(long time) {
            return time * (this.time - time);
        }
    }
}
