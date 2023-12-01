package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.List;

public final class Day2 extends Day {

    private static final int ROCK_SCORE = 1;
    private static final int PAPER_SCORE = 2;
    private static final int SCISSORS_SCORE = 3;

    private static final int LOSE = 0;
    private static final int DRAW = 3;
    private static final int WIN = 6;

    // They play row and I play column
    //         rock paper scissor
    // rock
    // paper
    // scissors
    private static final int[][] MOVE_STRATEGY = {
        {DRAW, WIN, LOSE},
        {LOSE, DRAW, WIN},
        {WIN, LOSE, DRAW}
    };

    //         lose draw win
    // rock
    // paper
    // scissors
    private static final int[][] OUTCOME_STRATEGY = {
        {SCISSORS_SCORE, ROCK_SCORE, PAPER_SCORE},
        {ROCK_SCORE, PAPER_SCORE, SCISSORS_SCORE},
        {PAPER_SCORE, SCISSORS_SCORE, ROCK_SCORE}
    };

    private List<int[]> strategyGuide;

    public Day2() {
        super(2, "Rock Paper Scissors");
    }

    @Override
    public void preload() {
        strategyGuide = getInput(move -> {
            String[] split = move.split(" ");
            return new int[] {split[0].charAt(0) - 'A', split[1].charAt(0) - 'X'};
        });
    }

    @Override
    public Object part1() {
        return strategyGuide.stream()
                .mapToInt(move -> MOVE_STRATEGY[move[0]][move[1]] + move[1] + 1)
                .sum();
    }

    @Override
    public Object part2() {
        return strategyGuide.stream()
                .mapToInt(move -> OUTCOME_STRATEGY[move[0]][move[1]] + (move[1] * 3))
                .sum();
    }
}
