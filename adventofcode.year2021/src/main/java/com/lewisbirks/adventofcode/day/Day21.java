package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.collection.FrequencyMap;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day21 extends Day {

    private static final Pattern INPUT_PATTERN = Pattern.compile("Player \\d+ starting position: (\\d+)");
    private static final int DETERMINISTIC_MAX = 1000;
    private static final int QUANTUM_MAX = 21;
    private static final FrequencyMap<Integer> ROLL_FREQUENCIES = new FrequencyMap<>(Map.of(
        3, 1L,
        4, 3L,
        5, 6L,
        6, 7L,
        7, 6L,
        8, 3L,
        9, 1L
    ));
    private int[] positions;

    public Day21() {
        super(21, "Dirac Dice");
    }

    @Override
    protected void preLoad() {
        positions = getInput(this::parse).stream().mapToInt(i -> i).toArray();
    }

    @Override
    protected Object part1() {
        return deterministicPlay(Arrays.copyOf(positions, positions.length));
    }

    @Override
    protected Object part2() {
        return Arrays.stream(quantumPlay(Arrays.copyOf(positions, positions.length))).reduce(0, Math::max);
    }

    private int deterministicPlay(int[] positions) {
        int dice = 0, rolls = 0, index = 0;
        int[] scores = {0, 0};
        while (true) {
            int rollSum = 0;
            for (int r = 0; r < 3; r++, dice = (dice + 1) % 100, rolls++) {
                rollSum += dice + 1;
            }
            positions[index] = (positions[index] + rollSum) % 10;
            scores[index] += positions[index] + 1;

            if (scores[index] >= DETERMINISTIC_MAX) {
                return scores[1 - index] * rolls;
            }
            index = 1 - index;
        }
    }

    private long[] quantumPlay(int[] players) {
        return internalQuantumPlay(new GameState(0, 0, players[0], players[1], true), new HashMap<>());
    }

    private long[] internalQuantumPlay(GameState state, Map<GameState, long[]> cache) {
        long[] wins = {0, 0};

        if (state.s1() >= QUANTUM_MAX) {
            wins[0] = 1;
            cache.put(state, wins);
        } else if (state.s2() >= QUANTUM_MAX) {
            wins[1] = 1;
            cache.put(state, wins);
        }

        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        ROLL_FREQUENCIES.forEach((rollValue, frequency) -> {
            int nextP1 = state.p1(), nextP2 = state.p2();
            int nextS1 = state.s1(), nextS2 = state.s2();

            if (state.player()) {
                nextP1 = (nextP1 + rollValue) % 10;
                nextS1 += nextP1 + 1;
            } else {
                nextP2 = (nextP2 + rollValue) % 10;
                nextS2 += nextP2 + 1;
            }

            long[] subsequentWins = internalQuantumPlay(
                new GameState(nextS1, nextS2, nextP1, nextP2, !state.player()), cache
            );
            wins[0] += frequency * subsequentWins[0];
            wins[1] += frequency * subsequentWins[1];
        });
        cache.put(state, wins);
        return wins;
    }

    private int parse(String line) {
        Matcher matcher = INPUT_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(line);
        }
        return (Integer.parseInt(matcher.group(1))) - 1;
    }

    record GameState(int s1, int s2, int p1, int p2, boolean player) {}
}
