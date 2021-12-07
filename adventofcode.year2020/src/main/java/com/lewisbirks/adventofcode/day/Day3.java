package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.List;
import java.util.function.Supplier;

public final class Day3 extends Day {

    private static final char TREE = '#';
    private final Supplier<char[][]> mapSupplier;

    public Day3() {
        super(3, "Toboggan Trajectory");
        mapSupplier = CachedSupplier.memoize(() -> {
            List<char[]> example = getInput(String::toCharArray);
            return example.toArray(char[][]::new);
        });
    }

    @Override
    protected Object part1() {
        return navigateMap(3, 1);
    }

    @Override
    protected Object part2() {
        long attempt1 = navigateMap(1, 1);
        long attempt2 = (long) part1();
        long attempt3 = navigateMap(5, 1);
        long attempt4 = navigateMap(7, 1);
        long attempt5 = navigateMap(1, 2);
        return attempt1 * attempt2 * attempt3 * attempt4 * attempt5;
    }

    private long navigateMap(int right, int down) {
        char[][] map = mapSupplier.get();
        int x = 0;
        int width = map[0].length;
        long treesHit = 0;
        for (int y = 0; y < map.length; y += down) {
            if (map[y][x] == TREE) {
                treesHit++;
            }
            if (x + right >= width) {
                x = Math.abs(width - (x + right));
            } else {
                x += right;
            }
        }
        return treesHit;
    }
}
