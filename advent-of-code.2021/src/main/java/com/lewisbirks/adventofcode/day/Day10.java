package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Day10 extends Day {

    private static final Map<Character, Integer> CORRUPT_POINTS = Map.of(
            ')', 3,
            ']', 57,
            '}', 1197,
            '>', 25137);
    private static final Map<Character, Integer> INCOMPLETE_POINTS = Map.of(
            '(', 1,
            '[', 2,
            '{', 3,
            '<', 4);
    private static final Map<Character, Character> PAIRS = Map.of(
            '(', ')',
            '[', ']',
            '{', '}',
            '<', '>');

    private static final Set<Character> OPENING = Set.of('(', '[', '{', '<');
    private static final Set<Character> CLOSING = Set.of(')', ']', '}', '>');

    private List<String> chunks;

    public Day10() {
        super(10, "Syntax Scoring");
    }

    @Override
    public void preload() {
        chunks = getInput();
    }

    @Override
    public Object part1() {
        long score = 0;
        for (String chunk : chunks) {
            Deque<Character> stack = new ArrayDeque<>();
            for (char character : chunk.toCharArray()) {
                if (OPENING.contains(character)) {
                    stack.push(character);
                } else if (CLOSING.contains(character) && PAIRS.get(stack.pop()) != character) {
                    score += CORRUPT_POINTS.get(character);
                    break;
                }
            }
        }

        return score;
    }

    @Override
    public Object part2() {
        List<Long> scores = new ArrayList<>();
        for (String chunk : chunks) {
            Deque<Character> stack = new ArrayDeque<>();
            boolean corrupt = false;
            for (char character : chunk.toCharArray()) {
                if (OPENING.contains(character)) {
                    stack.push(character);
                } else if (CLOSING.contains(character) && PAIRS.get(stack.pop()) != character) {
                    corrupt = true;
                    break;
                }
            }
            if (!corrupt) {
                long score = 0;
                while (!stack.isEmpty()) {
                    score *= 5;
                    score += INCOMPLETE_POINTS.get(stack.pop());
                }
                scores.add(score);
            }
        }
        scores.sort(Long::compareTo);
        return scores.get(scores.size() / 2);
    }
}
