package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day21 extends Day {

    private static final Pattern INPUT_PATTERN = Pattern.compile("Player \\d+ starting position: (\\d+)");

    private static final int DETERMINISTIC_MAX = 1000;

    public Day21() {
        super(21, "Dirac Dice");
    }

    @Override
    protected Object part1() {
        List<Player> players = getInput(this::parse);
        return deterministicPlay(players);
    }

    private Player parse(String line) {
        Matcher matcher = INPUT_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(line);
        }
        return new Player(Integer.parseInt(matcher.group(1)));
    }

    private int deterministicPlay(List<Player> players) {
        int dice = 0;
        int rolls = 0;
        while (true) {
            Player player = players.get(rolls % 2);
            int rollSum = 0;
            for (int r = 0; r < 3; r++, dice = (dice + 1) % 100, rolls++) {
                rollSum += dice + 1;
            }
            player.increment(rollSum);
            if (player.hasWon(DETERMINISTIC_MAX)) {
                return players.get(rolls % 2).score * rolls;
            }
        }
    }

    @Override
    protected Object part2() {
        return null;
    }

    static class Player {
        int position;
        int score;

        public Player(int startingPosition) {
            this.position = startingPosition - 1;
        }

        void increment(int amount) {
            position = (position + amount) % 10;
            score += position + 1;
        }

        boolean hasWon(int max) {
            return score >= max;
        }

        @Override
        public String toString() {
            return "Player{position=" + position + ", score=" + score + '}';
        }
    }
}
