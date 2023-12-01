package com.lewisbirks.adventofcode.day;

import static java.lang.Integer.parseInt;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.tuple.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Day15 extends Day {

    private static final Pattern CO_OR = Pattern.compile("x=(-?\\d+), y=(-?\\d+)");
    private static final int MULTIPLIER = 4_000_000;

    private Map<Point, Point> sensorBeacons;

    int yToCheck = 2_000_000;
    int max = 4_000_000;

    public Day15() {
        super(15, "");
    }

    public static void main(String[] args) {
        new Day15().process();
    }

    @Override
    protected void preLoad() {
        sensorBeacons = getInput(line -> {
            Matcher matcher = CO_OR.matcher(line);
            Point sensor = null;
            Point beacon = null;
            while (matcher.find()) {
                if (sensor == null) {
                    sensor = new Point(parseInt(matcher.group(1)), parseInt(matcher.group(2)));
                } else {
                    beacon = new Point(parseInt(matcher.group(1)), parseInt(matcher.group(2)));
                }
            }
            return new Pair<>(sensor, beacon);
        }, Collectors.toMap(Pair::left, Pair::right));
    }

    // redo but better
    @Override
    protected Object part1() {
        // all locations where there is a beacon or sensor, i.e. those that can then be excluded
        Set<Integer> toExclude = sensorBeacons.entrySet().stream()
            .flatMap(e -> Stream.of(e.getKey(), e.getValue()))
            .filter(p -> p.y() == yToCheck)
            .map(Point::x)
            .collect(Collectors.toSet());

        Set<Integer> locations = new HashSet<>();
        for (Map.Entry<Point, Point> entry : sensorBeacons.entrySet()) {
            Point sensor = entry.getKey();
            Point beacon = entry.getValue();
            int distance = sensor.distance(beacon);
            int distanceFromRow = Math.abs(sensor.y() - yToCheck);
            if (distanceFromRow > distance) {
                continue;
            }
            for (int i = 0; i <= distance - distanceFromRow; i++) {
                locations.add(sensor.x() - i);
                locations.add(sensor.x() + i);
            }
        }
        locations.removeAll(toExclude);
        return locations.size();
    }

    @Override
    protected Object part2() {
        return null;
    }
}
