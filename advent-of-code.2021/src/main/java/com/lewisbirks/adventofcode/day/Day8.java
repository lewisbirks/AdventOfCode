package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day8 extends Day {

    private static final List<Integer> UNIQUE_LENGTHS = List.of(2, 3, 4, 7);
    private static final Map<Set<String>, Integer> CHARS_TO_NUMBERS = Map.of(
            Set.of("c", "f"), 1,
            Set.of("a", "c", "d", "e", "g"), 2,
            Set.of("a", "c", "d", "f", "g"), 3,
            Set.of("b", "c", "d", "f"), 4,
            Set.of("a", "b", "d", "f", "g"), 5,
            Set.of("a", "b", "d", "e", "f", "g"), 6,
            Set.of("a", "c", "f"), 7,
            Set.of("a", "b", "c", "d", "e", "f", "g"), 8,
            Set.of("a", "b", "c", "d", "f", "g"), 9,
            Set.of("a", "b", "c", "e", "f", "g"), 0);
    private List<String[]> displays;

    public Day8() {
        super(8, "Seven Segment Search");
    }

    @Override
    public void preload() {
        displays = getInput(input -> input.split("\\|"));
    }

    @Override
    public Object part1() {
        return displays.stream()
                .map(input -> input[1].trim())
                .flatMap(parts -> Arrays.stream(parts.split(" ")))
                .mapToInt(String::length)
                .filter(UNIQUE_LENGTHS::contains)
                .count();
    }

    @Override
    public Object part2() {
        return displays.stream()
                .map(input ->
                        List.of(input[0].trim().split(" "), input[1].trim().split(" ")))
                .mapToLong(line -> parse(line.get(1), decode(line.get(0))))
                .sum();
    }

    private Map<String, String> decode(String[] codes) {
        // 1, 7, 4, 8
        String cf = "", acf = "", bcdf = "", abcdefg = "";
        Map<String, Integer> occurrence = new HashMap<>(8);
        for (String code : codes) {
            switch (code.length()) {
                case 2 -> cf = code;
                case 3 -> acf = code;
                case 4 -> bcdf = code;
                case 7 -> abcdefg = code;
                case 6 -> {
                    for (int i = 0; i < code.length(); i++) {
                        String c = String.valueOf(code.charAt(i));
                        occurrence.merge(c, 1, Integer::sum);
                    }
                }
            }
        }

        String cde = occurrence.entrySet().stream()
                .filter(entry -> entry.getValue() == 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining());

        String de = removeAll(cde, cf);
        String a = removeAll(acf, cf);
        String c = removeAll(cde, de);
        String f = removeAll(acf, a + c);

        String bd = removeAll(bcdf, c + f);
        String e = removeAll(de, bd);
        String d = removeAll(de, e);
        String b = removeAll(bd, d);
        String g = removeAll(abcdefg, a + b + c + d + e + f);

        return Map.of(a, "a", b, "b", c, "c", d, "d", e, "e", f, "f", g, "g");
    }

    // cde - cd -> e
    // cde - cf -> de
    private String removeAll(final String s1, final String s2) {
        String removed = s1;
        for (int i = 0; i < s2.length(); i++) {
            removed = removed.replace(String.valueOf(s2.charAt(i)), "");
        }
        return removed;
    }

    private int parse(String[] numbers, Map<String, String> map) {
        int parsed = 0;
        for (String number : numbers) {
            parsed *= 10;
            parsed += parseNumber(number, map);
        }
        return parsed;
    }

    private int parseNumber(String number, Map<String, String> map) {
        Set<String> toMatch = IntStream.range(0, number.length())
                .mapToObj(i -> map.get(String.valueOf(number.charAt(i))))
                .collect(Collectors.toSet());

        if (!CHARS_TO_NUMBERS.containsKey(toMatch)) {
            throw new NoSuchElementException("Could not find " + toMatch);
        }
        return CHARS_TO_NUMBERS.get(toMatch);
    }
}
