package com.lewisbirks.adventofcode.day;

import static com.lewisbirks.adventofcode.common.MathUtils.isInt;

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
            long better = race.numberOfWaysToBeatRecord();
            if (better > 0) {
                product *= better;
            }
        }
        return product;
    }

    @Override
    public Object part2() {
        return this.race.numberOfWaysToBeatRecord();
    }

    record Race(long time, long distance) {
        public static Race of(String time, String record) {
            return new Race(Long.parseLong(time), Long.parseLong(record));
        }

        public long numberOfWaysToBeatRecord() {
            // Distance:
            // d < x * (t - x)
            // d < tx - x^2
            // x^2 - tx + d > 0
            // therefore just solve the quadratic
            // ax^2 + bx + c = 0
            // -b ± √(b^2 - 4ac)
            // ----------------
            //       2a
            // b = -t
            // a = 1
            // c = d
            double sqrt = Math.sqrt((time * time) - (4 * distance));
            double positive = (time + sqrt) / 2.0d;
            double negative = (time - sqrt) / 2.0d;

            // seems to be a boundary issue when the difference is an integer, normally add one to account for the
            // losses due to rounding but when the difference is an integer we need to alter by 1 for some reason
            // to simulate these rounding losses
            // (this only occurs in the test input...)
            long upperBound = isInt(positive) ? ((long) positive) - 1 : (long) Math.floor(positive);
            long lowerBound = isInt(negative) ? ((long) negative) + 1 : (long) Math.ceil(negative);

            return upperBound - lowerBound + 1;
        }
    }
}
