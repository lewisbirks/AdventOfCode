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

    public Day6() {
        super(6, "Wait For It");
    }

    @Override
    public void preload() {
        List<String> document = getInput(line -> line.substring(line.indexOf(":") + 1).trim());

        List<Race> races = new ArrayList<>();

        Matcher times = NUMBERS.matcher(document.get(0));
        Matcher distances = NUMBERS.matcher(document.get(1));

        while (distances.find() && times.find()) {
            String time = times.group(1);
            String distance = distances.group(1);
            races.add(Race.of(time, distance));
        }

        this.races = Collections.unmodifiableList(races);
    }

    @Override
    public Object part1() {
        int product = 1;
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
        return null;
    }

    record Race(int time, int record) {
        public static Race of(String time, String record) {
            return new Race(Integer.parseInt(time), Integer.parseInt(record));
        }

        public int calculateNumberOfBetterOptions() {
            int better = 0;
            for (int i = 1; i < this.time; i++) {
                int remainingTime = this.time - i;
                int distance = i * remainingTime;
                if (distance > record) {
                    better++;
                }
            }
            return better;
        }
    }
}
