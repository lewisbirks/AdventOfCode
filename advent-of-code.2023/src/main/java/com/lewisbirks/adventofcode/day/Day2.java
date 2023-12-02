package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Day2 extends Day {

    private List<Game> games;

    public Day2() {
        super(2, "Cube Conundrum");
    }

    @Override
    public void preload() {
        games = getInput(s -> {
            int separator = s.indexOf(':');
            int id = Integer.parseInt(s.substring(s.indexOf(' ') + 1, separator));
            String sets = s.substring(separator + 2);
            List<CubeSet> cubeSets = new ArrayList<>(3);
            for (String string : sets.split("; ")) {
                String[] cubes = string.split(", ");
                int red = 0;
                int green = 0;
                int blue = 0;

                for (String cube : cubes) {
                    int i = Integer.parseInt(cube.substring(0, cube.indexOf(' ')));
                    if (cube.contains("red")) {
                        red = i;
                    } else if (cube.contains("green")) {
                        green = i;
                    } else {
                        blue = i;
                    }
                }

                CubeSet apply = new CubeSet(red, green, blue);
                cubeSets.add(apply);
            }
            return new Game(id, Collections.unmodifiableList(cubeSets));
        });
    }

    @Override
    public Object part1() {
        int maxRed = 12;
        int maxGreen = 13;
        int maxBlue = 14;
        return games.stream()
                .filter(game -> game.isPossible(maxRed, maxGreen, maxBlue))
                .mapToLong(Game::id)
                .sum();
    }

    @Override
    public Object part2() {
        return games.stream().map(Game::getMin).mapToLong(CubeSet::power).sum();
    }

    record Game(int id, List<CubeSet> sets) {
        public boolean isPossible(int r, int g, int b) {
            return sets.stream().noneMatch(set -> set.red() > r || set.green() > g || set.blue() > b);
        }

        public CubeSet getMin() {
            int maxR = 0;
            int maxG = 0;
            int maxB = 0;

            for (CubeSet set : sets) {
                maxR = Math.max(maxR, set.red());
                maxG = Math.max(maxG, set.green());
                maxB = Math.max(maxB, set.blue());
            }

            return new CubeSet(maxR, maxG, maxB);
        }
    }

    record CubeSet(int red, int green, int blue) {
        public long power() {
            return (long) red * green * blue;
        }
    }
}
