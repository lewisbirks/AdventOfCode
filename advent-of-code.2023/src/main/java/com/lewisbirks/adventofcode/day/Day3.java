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
    private List<Symbol> symbols;

    public Day3() {
        super(3, "Gear Ratios");
    }

    @Override
    public void preload() {
        char[][] engineSchematic = getInput(String::toCharArray).toArray(char[][]::new);
        List<PartNumber> locations = new ArrayList<>();
        List<Symbol> special = new ArrayList<>();
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
                    special.add(new Symbol(engineSchematic[y][x], new Point(x, y)));
                }
            }
        }
        partNumberLocations = Collections.unmodifiableList(locations);
        symbols = Collections.unmodifiableList(special);
    }

    @Override
    public Object part1() {
        Set<PartNumber> partNumbers = new HashSet<>(partNumberLocations);
        return symbols.stream()
                .mapToLong(symbol -> getSurroundingPartNumbers(symbol, partNumbers, false))
                .sum();
    }

    private long getSurroundingPartNumbers(Symbol symbol, Set<PartNumber> partNumbers, boolean multiply) {
        Set<Point> surrounding = symbol.position().getSurrounding();
        List<PartNumber> connected = new ArrayList<>();
        for (PartNumber partNumber : partNumbers) {
            if (!partNumber.isNear(symbol.position())) {
                continue;
            }
            for (Point point : surrounding) {
                if (partNumber.location().contains(point)) {
                    connected.add(partNumber);
                    break;
                }
            }
        }
        connected.forEach(partNumbers::remove);
        if (multiply) {
            return !symbol.isGear(connected) ? 0 : ((long) connected.get(0).number) * connected.get(1).number;
        }
        return connected.stream().mapToLong(PartNumber::number).sum();
    }

    @Override
    public Object part2() {
        Set<PartNumber> partNumbers = new HashSet<>(partNumberLocations);
        return symbols.stream()
                .mapToLong(symbol -> getSurroundingPartNumbers(symbol, partNumbers, true))
                .sum();
    }

    record PartNumber(int number, Location location) {
        boolean isNear(Point point) {
            return point.y() >= location().y() - 1 && point.y() <= location().y() + 1
                    || point.x() >= location().xMin() - 1
                            && point.x() <= location().xMax() + 1;
        }
    }

    record Location(int y, int xMin, int xMax) {
        public boolean contains(Point point) {
            return point.y() == y && point.x() >= xMin && point.x() <= xMax;
        }
    }

    record Symbol(char symbol, Point position) {
        boolean isGear(Collection<PartNumber> connected) {
            return symbol == '*' && connected.size() == 2;
        }
    }
}
