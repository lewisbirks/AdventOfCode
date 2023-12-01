package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public final class Day6 extends Day {

    private String input;

    public Day6() {
        super(6, "Tuning Trouble");
    }

    @Override
    protected void preLoad() {
        input = readInput();
    }

    @Override
    protected Object part1() {
        return findMarker(4);
    }

    @Override
    protected Object part2() {
        return findMarker(14);
    }

    private int findMarker(int markerSize) {
        int size = input.length() - 1;
        for (int i = markerSize; i < size; i++) {
            String subsection = input.substring(i - markerSize, i);
            if (hasDistinctCharacters(subsection)) {
                return i;
            }
            int addition = indexOfLastRepeatingChar(subsection);
            i += addition;
        }
        throw new IllegalArgumentException("No marker found");
    }

    private static boolean hasDistinctCharacters(String subsection) {
        Set<Character> found = new HashSet<>();
        char[] chars = subsection.toCharArray();
        return IntStream.range(0, chars.length).allMatch(i -> found.add(chars[i]));
    }

    private static int indexOfLastRepeatingChar(String subsection) {
        Map<Character, Integer> found2 = new HashMap<>();
        char[] chars = subsection.toCharArray();
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            if (found2.containsKey(chars[i])) {
                index = found2.get(chars[i]);
            } else {
                found2.put(chars[i], i);
            }
        }
        return index;
    }
}
