package com.lewisbirks.adventofcode.day;

import static com.lewisbirks.adventofcode.common.coor.Point.DOWN;
import static com.lewisbirks.adventofcode.common.coor.Point.LEFT;
import static com.lewisbirks.adventofcode.common.coor.Point.RIGHT;
import static com.lewisbirks.adventofcode.common.coor.Point.UP;

import com.lewisbirks.adventofcode.common.coor.Point;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.lang3.function.TriConsumer;

public final class Day10 extends Day {

    private Pipe start = null;
    private char[][] maze;
    private int maxX;
    private int maxY;

    public static void main(String[] args) {
        new Day10().process();
    }

    public Day10() {
        super(10, "Pipe Maze");
    }

    @Override
    public void preload() {
        maze = getInput(String::toCharArray).toArray(char[][]::new);
        maxY = maze.length;
        maxX = maze[0].length;

        final Map<Point, Pipe> cache = new HashMap<>();
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
                    if (debug) {
                        System.out.println("Start is in " + start + " and is being mapped to " + symbol);
                    }
                    maze[y][x] = symbol;
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

                   Somehow I'm incapable of mapping things correctly
                   If I could do that correctly then I wouldn't need to use Pipe#next and could
                   instead just call pipe.in or pipe.out
                        | | |
                        | | |
                  <-----J | L----->
                  --------+------->
                  <-----7 | F----->
                        | | |
                        | V |

                */
                Point inLocation =
                        switch (symbol) {
                            case '|', 'J', 'L' -> UP;
                            case '-' -> LEFT;
                            case '7', 'F' -> DOWN;
                            default -> throw new IllegalArgumentException("Unexpected character " + symbol);
                        };
                Point outLocation =
                        switch (symbol) {
                            case '|' -> DOWN;
                            case '-', 'L', 'F' -> RIGHT;
                            case 'J', '7' -> LEFT;
                            default -> throw new IllegalArgumentException("Unexpected character " + symbol);
                        };

                inputMapper.accept(pipe, location.add(inLocation));
                outputMapper.accept(pipe, location.add(outLocation));
            }
        }
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
            if (couldLeft) {
                actualStart = 'J';
            } else if (couldRight) {
                actualStart = 'L';
            } else {
                actualStart = '|';
            }
        } else if (couldDown) {
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

    @Override
    public Object part1() {
        return getWalls().size() / 2;
    }

    @Override
    public Object part2() {
        Set<Point> walls = getWalls();

        int count = 0;
        for (int y = 0; y < maze.length; y++) {
            boolean enclosed = false;
            for (int x = 0; x < maze[y].length; x++) {
                char pipe = maze[y][x];
                Point position = new Point(x, y);
                boolean isWall = walls.contains(position);
                if (isWall && (pipe == '|' || pipe == 'J' || pipe == 'L')) {
                    enclosed = !enclosed;
                } else if (enclosed && !isWall) {
                    count++;
                }
            }
        }

        return count;
    }

    private Set<Point> getWalls() {
        Pipe forward = start.out;
        Pipe reverse = start.in;
        Pipe previousF = start;
        Pipe previousR = start;

        Set<Point> walls = new HashSet<>(Set.of(start.location, forward.location, reverse.location));

        do {
            Pipe temp = forward;
            forward = forward.next(previousF);
            previousF = temp;

            temp = reverse;
            reverse = reverse.next(previousR);
            previousR = temp;

            walls.add(forward.location);
            walls.add(reverse.location);
        } while (forward != reverse);

        return walls;
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
            return p == in ? out : in;
        }

        @Override
        public String toString() {
            return symbol + " " + location;
        }
    }
}
