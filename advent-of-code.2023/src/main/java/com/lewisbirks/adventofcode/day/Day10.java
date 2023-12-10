package com.lewisbirks.adventofcode.day;

import static com.lewisbirks.adventofcode.common.coor.Point.DOWN;
import static com.lewisbirks.adventofcode.common.coor.Point.LEFT;
import static com.lewisbirks.adventofcode.common.coor.Point.RIGHT;
import static com.lewisbirks.adventofcode.common.coor.Point.UP;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.function.TriConsumer;

public final class Day10 extends Day {

    private Pipe start = null;

    public static void main(String[] args) {
        new Day10().process();
    }

    public Day10() {
        super(10, "Pipe Maze");
    }

    @Override
    public void preload() {
        char[][] maze = getInput(String::toCharArray).toArray(char[][]::new);
        final Map<Point, Pipe> cache = new HashMap<>();
        int maxY = maze.length;
        int maxX = maze[0].length;

        TriConsumer<Pipe, Point, Consumer<Pipe>> mapper = (pipe, location, pipeConsumer) -> {
            if (location.x() >= 0 && location.x() < maxX && location.y() >= 0 && location.y() < maxY) {
                pipeConsumer.accept(cache.computeIfAbsent(location, l -> new Pipe(maze[l.y()][l.x()], l)));
            }
        };
        // for the given pipe get the pipe which points to it
        BiConsumer<Pipe, Point> inputMapper = (pipe, point) -> mapper.accept(pipe, point, pipe::setIn);
        // for the given pipe get the pipe which it points to
        BiConsumer<Pipe, Point> outputMapper = (pipe, point) -> mapper.accept(pipe, point, pipe::setOut);

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                char symbol = maze[y][x];
                if (symbol == '.') {
                    continue;
                }
                Point location = new Point(x, y);
                Pipe pipe = cache.computeIfAbsent(location, l -> new Pipe(maze[l.y()][l.x()], l));

                if (symbol == 'S') {
                    start = pipe;
                    symbol = getStaringChar(location, maze, maxX, maxY);
                    System.out.println("Mapped start to " + symbol);
                }

                /*
                   | is a vertical pipe connecting north and south.
                   - is a horizontal pipe connecting east and west.
                   L is a 90-degree bend connecting north and east.
                   J is a 90-degree bend connecting north and west.
                   7 is a 90-degree bend connecting south and west.
                   F is a 90-degree bend connecting south and east.
                   . is ground; there is no pipe in this tile.
                   S is the starting position of the animal; there is a pipe on this tile,
                     but your sketch doesn't show what shape the pipe has.

                       Λ Λ |
                       | | |
                  -----J | L---->
                 --------+------>  This isn't quite right but the logic added to Pipe#next fixes things
                  -----7 | F---->
                       | | |
                       V | |

                */
                Point inLocation =
                        switch (symbol) {
                            case '|', 'F' -> DOWN;
                            case '-', 'J', '7' -> LEFT;
                            case 'L' -> UP;
                            default -> throw new IllegalArgumentException("Unexpected character " + symbol);
                        };
                Point outLocation =
                        switch (symbol) {
                            case '|', 'J' -> UP;
                            case '-', 'F', 'L' -> RIGHT;
                            case '7' -> DOWN;
                            default -> throw new IllegalArgumentException("Unexpected character " + symbol);
                        };

                inputMapper.accept(pipe, location.add(inLocation));
                outputMapper.accept(pipe, location.add(outLocation));
            }
        }

        if (start == null) {
            throw new IllegalStateException("Should have found a start point");
        }
    }

    @Override
    public Object part1() {

        Pipe forward = start.out;
        Pipe reverse = start.in;
        int count = 1;
        Pipe previousF = start;
        Pipe previousR = start;
        do {
            Pipe temp = forward;
            forward = forward.next(previousF);
            previousF = temp;

            temp = reverse;
            reverse = reverse.next(previousR);
            previousR = temp;

            count++;
        } while (forward != reverse);
        return count;
    }

    private char getStaringChar(Point start, char[][] maze, int maxX, int maxY) {
        Function<Point, Character> mapper = p -> {
            if (p.x() >= 0 && p.x() < maxX && p.y() >= 0 && p.y() < maxY) {
                return maze[p.y()][p.x()];
            }
            return '0';
        };
        char found = mapper.apply(start.add(UP));
        boolean couldUp = found == '|' || found == '7' || found == 'F';
        found = mapper.apply(start.add(DOWN));
        boolean couldDown = found == '|' || found == 'J' || found == 'L';
        found = mapper.apply(start.add(LEFT));
        boolean couldLeft = found == '-' || found == 'L' || found == 'F';
        found = mapper.apply(start.add(RIGHT));
        boolean couldRight = found == '-' || found == 'J' || found == '7';

        char actualStart;
        if (couldUp) {
            if (couldLeft && couldRight) {
                throw new IllegalStateException("Somehow the start could be to the left or right as well as up");
            }
            if (couldLeft) {
                actualStart = 'J';
            } else if (couldRight) {
                actualStart = 'L';
            } else {
                actualStart = '|';
            }
        } else if (couldDown) {
            if (couldLeft && couldRight) {
                throw new IllegalStateException("Somehow the start could be to the left or right as well as down");
            }
            if (couldLeft) {
                actualStart = '7';
            } else if (couldRight) {
                actualStart = 'F';
            } else {
                actualStart = '|';
            }
        } else {
            actualStart = '-';
        }
        return actualStart;
    }

    private String route(List<Pipe> p) {
        return p.stream().map(Pipe::toString).collect(Collectors.joining(" -> "));
    }

    @Override
    public Object part2() {
        return null;
    }

    static class Pipe {

        private final char symbol;
        private final Point location;
        private Pipe out;
        private Pipe in;

        public Pipe(char pipe, Point location) {
            this.symbol = pipe;
            this.location = location;
        }

        public void setOut(Pipe out) {
            this.out = out;
        }

        public void setIn(Pipe in) {
            this.in = in;
        }

        public Pipe next(Pipe p) {
            if (p == in) {
                return out;
            }
            if (p == out) {
                return in;
            }
            throw new IllegalArgumentException(
                    "Given pipe %s must be one of the outputs %s or %s".formatted(p, in, out));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pipe pipe = (Pipe) o;
            return Objects.equals(location, pipe.location);
        }

        @Override
        public int hashCode() {
            return Objects.hash(location);
        }

        @Override
        public String toString() {
            return symbol + "[" + location.x() + "," + location.y() + "]";
        }
    }
}
