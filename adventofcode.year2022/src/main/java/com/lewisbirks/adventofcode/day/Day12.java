package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public final class Day12 extends Day {

    private static final List<Point> DIRECTIONS = List.of(
        new Point(1, 0), new Point(-1, 0), new Point(0, -1), new Point(0, 1)
    );

    char[][] map;

    Point start, end;
    private int width;
    private int height;

    public Day12() {
        super(12, "Hill Climbing Algorithm");
    }

    @Override
    protected void preLoad() {
        List<String> input = getInput();
        map = input.stream().map(String::toCharArray).toArray(char[][]::new);
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'S') {
                    start = new Point(x, y);
                    map[y][x] = 'a';
                }
                if (map[y][x] == 'E') {
                    end = new Point(x, y);
                    map[y][x] = 'z';
                }
            }
        }
        width = map[0].length;
        height = map.length;
    }

    @Override
    protected Object part1() {
        return calculateShortestPath(Set.of(start));
    }

    @Override
    protected Object part2() {
        Set<Point> as = new HashSet<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'a') {
                    as.add(new Point(x, y));
                }
            }
        }
        return calculateShortestPath(as);
    }

    private int calculateShortestPath(Set<Point> potentialDestinations) {
        Set<Point> visited = new HashSet<>();
        Queue<State> processing = new PriorityQueue<>(Comparator.comparing(State::steps));

        // work in reverse
        processing.add(new State(end, 0));
        while (!processing.isEmpty()) {
            State state = processing.poll();
            Point current = state.position();
            if (!visited.add(current)) {
                continue;
            }
            if (potentialDestinations.contains(current)) {
                return state.steps();
            }
            for (Point direction : DIRECTIONS) {
                Point p = current.add(direction);
                // check if out of bounds
                if (p.x() < 0 || p.x() >= width || p.y() < 0 || p.y() >= height) {
                    continue;
                }
                // check if already been there
                if (visited.contains(p)) {
                    continue;
                }
                char currentValue = map[current.y()][current.x()];
                char newValue = map[p.y()][p.x()];
                // check if valid move
                if (newValue < currentValue - 1) {
                    continue;
                }
                processing.add(new State(p, state.steps() + 1));
            }
        }
        return -1;
    }

    record State(Point position, int steps) {}
}
