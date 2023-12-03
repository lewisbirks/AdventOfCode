package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Day3 extends Day {

    private List<PartNumber> partNumberLocations;
    private List<Point> symbols;

    public Day3() {
        super(3, "Gear Ratios");
    }

    @Override
    public void preload() {
        char[][] engineSchematic = getInput(String::toCharArray).toArray(char[][]::new);
        List<PartNumber> locations = new ArrayList<>();
        List<Point> special = new ArrayList<>();
        for (int y = 0; y < engineSchematic.length; y++) {
            for (int x = 0; x < engineSchematic[y].length; x++) {
                int num = -1;
                int startX = x;
                while (x < engineSchematic[y].length && Character.isDigit(engineSchematic[y][x])) {
                    if (num == -1) {
                        startX = x;
                        num = 0;
                    }
                    num = num * 10 + (engineSchematic[y][x++] - '0');
                }
                if (num != -1) {
                    locations.add(new PartNumber(num, new Location(y, startX, x - 1)));
                }
                if (x < engineSchematic[y].length && engineSchematic[y][x] != '.') {
                    special.add(new Point(x, y));
                }
            }
        }
        partNumberLocations = Collections.unmodifiableList(locations);
        symbols = Collections.unmodifiableList(special);
    }

    @Override
    public Object part1() {
        return symbols.stream()
                .map(this::getSurroundingPartNumbers)
                .flatMap(Collection::stream)
                .distinct()
                .mapToLong(PartNumber::number)
                .sum();
    }

    private Set<PartNumber> getSurroundingPartNumbers(Point symbol) {
        Set<Point> surrounding = symbol.getSurrounding();
        Set<PartNumber> connected = new HashSet<>();
        for (PartNumber partNumber : partNumberLocations) {
            Location location = partNumber.location();
            for (Point point : surrounding) {
                if (location.contains(point)) {
                    connected.add(partNumber);
                    break;
                }
            }
        }
        return connected;
    }

    @Override
    public Object part2() {
        return null;
    }

    record PartNumber(int number, Location location) {}

    record Location(int y, int xMin, int xMax) {
        public boolean contains(Point point) {
            return point.y() == y && point.x() >= xMin && point.x() <= xMax;
        }
    }
}
