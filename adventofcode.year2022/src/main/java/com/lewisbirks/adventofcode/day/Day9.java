package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

public final class Day9 extends Day {


    private static final Day9.Point START = new Point(0, 0);
    private List<Command> commands;

    public Day9() {
        super(9, "Rope Bridge");
    }

    @Override
    protected void preLoad() {
        commands = getInput(line -> {
            String[] split = line.split(" ");
            Direction direction = switch(split[0]) {
                case "L" -> Direction.LEFT;
                case "R" -> Direction.RIGHT;
                case "U" -> Direction.UP;
                case "D" -> Direction.DOWN;
                default -> throw new IllegalStateException("Unexpected value: " + split[0]);
            };
            int amount = Integer.parseInt(split[1]);
            return new Command(direction, amount);
        });
    }

    @Override
    protected Object part1() {
        return simulateRope(2);
    }

    @Override
    protected Object part2() {
        return simulateRope(10);
    }

    private int simulateRope(int numKnots) {
        Point head = START;
        List<Point> knots = IntStream.range(1, numKnots).mapToObj(i -> START).collect(toCollection(ArrayList::new));
        Set<Point> tailPositions = new HashSet<>();
        tailPositions.add(START);
        for (Command command : commands) {
            for (int i = 0; i < command.amount; i++) {
                head = head.move(command.direction);
                Point previous = head;
                for (int j = 0; j < knots.size(); j++) {
                    Point knot = knots.get(j);
                    if (!knot.nextTo(previous)) {
                        Direction d = knot.directionTo(previous);
                        knot = knot.move(d);
                        knots.set(j, knot);
                        if (j == knots.size() - 1) {
                            tailPositions.add(knot);
                        }
                    }
                    previous = knot;
                }
            }
        }
        return tailPositions.size();
    }

    public record Point(int x, int y) {

        public Point move(Direction d) {
            return switch (d) {
                case UP -> new Point(x, y + 1);
                case DOWN -> new Point(x, y - 1);
                case LEFT -> new Point(x - 1, y);
                case RIGHT -> new Point(x + 1, y);
                case LEFT_UP -> new Point(x - 1, y + 1);
                case RIGHT_UP -> new Point(x + 1, y + 1);
                case LEFT_DOWN -> new Point(x - 1, y - 1);
                case RIGHT_DOWN -> new Point(x + 1, y - 1);
            };
        }
        public boolean nextTo(Point other) {
            return other.x >= x - 1 && other.x <= x + 1 && other.y >= y - 1 && other.y <= y + 1;
        }

        public Direction directionTo(Point other) {
            if (this.x == other.x) {
                // need to move up or down
                return this.y < other.y ? Direction.UP : Direction.DOWN;
            }
            if (this.y == other.y) {
                // need to move left or right
                return this.x < other.x ? Direction.RIGHT : Direction.LEFT;
            }
            if (this.x < other.x) {
                // need to move right but is it up or down
                return this.y < other.y ? Direction.RIGHT_UP : Direction.RIGHT_DOWN;
            }
            // only option here is diagonal on the left side
            return this.y < other.y ? Direction.LEFT_UP : Direction.LEFT_DOWN;
        }
    }
    record Command(Direction direction, int amount) {}

    enum Direction{
        LEFT, RIGHT, UP, DOWN, LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN
    }
}
