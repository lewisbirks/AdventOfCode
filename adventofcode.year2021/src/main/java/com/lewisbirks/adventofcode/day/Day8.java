package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day8 extends Day {

    private final List<Integer> UNIQUE_LENGTHS = List.of(2, 3, 4, 7);
    Map<Set<String>, Integer> foo = Map.of(
        Set.of("c", "f"), 1,
        Set.of("a", "c", "d", "e", "g"), 2,
        Set.of("a", "c", "d", "f", "g"), 3,
        Set.of("b", "c", "d", "f"), 4,
        Set.of("a", "b", "d", "f", "g"), 5,
        Set.of("a", "b", "d", "e", "f", "g"), 6,
        Set.of("a", "c", "f"), 7,
        Set.of("a", "b", "c", "d", "e", "f", "g"), 8,
        Set.of("a", "b", "c", "d", "f", "g"), 9,
        Set.of("a", "b", "c", "e", "f", "g"), 0
    );

    public Day8() {
        super(8, "Seven Segment Search");
    }

    @Override
    protected Object part1() {

        return getInput().stream()
            .map(input -> input.split("\\|")[1].trim())
            .flatMap(parts -> Arrays.stream(parts.split(" ")))
            .mapToInt(String::length)
            .filter(UNIQUE_LENGTHS::contains)
            .count();
    }

    @Override
    protected Object part2() {
        return getInput().stream()
            .map(input -> input.split("\\|"))
            .map(input -> List.of(input[0].trim().split(" "), input[1].trim().split(" ")))
            .mapToLong(line -> parse(line.get(1), decode(line.get(0))))
            .sum();
    }

    private Map<String, String> decode(String[] codes) {
        List<String> sorted = new ArrayList<>(Arrays.asList(codes));
        sorted.sort(Comparator.comparingInt(String::length));

        // 1, 7, 4, 8
        String cf = sorted.remove(0); // 1 length 2
        String acf = sorted.remove(0); // 7 length 3
        String bcdf = sorted.remove(0); // 4 length 4
        String abcdefg = sorted.remove(sorted.size() - 1); // 8 length 7

        String cde = sorted.stream()
            .filter(s -> s.length() == 6) // 6/0/9
            .flatMap(s -> IntStream.range(0, s.length()).mapToObj(s::charAt))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue() == 2L)
            .map(entry -> entry.getKey().toString())
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

        if (!foo.containsKey(toMatch)) {
            throw new NoSuchElementException("Could not find " + toMatch);
        }
        return foo.get(toMatch);
    }

    // cde - cd -> e
    // cde - cf -> de
    private String removeAll(String s1, String s2) {
        String removed = s1;
        for (int i = 0; i < s2.length(); i++) {
            removed = removed.replace(String.valueOf(s2.charAt(i)), "");
        }
        return removed;
    }
}
