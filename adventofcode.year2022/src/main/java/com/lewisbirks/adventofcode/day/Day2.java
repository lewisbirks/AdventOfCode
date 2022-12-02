package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.List;
import java.util.function.Supplier;

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
    static int[][] moveStrategy = {
        {DRAW, WIN, LOSE},
        {LOSE, DRAW, WIN},
        {WIN, LOSE, DRAW}
    };

    //         lose draw win
    // rock
    // paper
    // scissors
    static int[][] outcomeStrategy = {
        {SCISSORS_SCORE, ROCK_SCORE, PAPER_SCORE},
        {ROCK_SCORE, PAPER_SCORE, SCISSORS_SCORE},
        {PAPER_SCORE, SCISSORS_SCORE, ROCK_SCORE}
    };


    private final Supplier<List<int[]>> strategyGuide;

    public Day2() {
        super(2, "Rock Paper Scissors");
        strategyGuide = CachedSupplier.memoize(() -> getInput(move -> {
            String[] split = move.split(" ");
            return new int[] {
                split[0].charAt(0) - 'A', split[1].charAt(0) - 'X'
            };
        }));
    }

    @Override
    protected void preLoad() {
        strategyGuide.get();
    }

    @Override
    protected Object part1() {
        return strategyGuide.get().stream().mapToInt(move -> playWithMove(move[0], move[1])).sum();
    }

    @Override
    protected Object part2() {
        return strategyGuide.get().stream().mapToInt(move -> playWithOutcome(move[0], move[1])).sum();
    }

    public int playWithMove(int opponent, int mine) {
        return moveStrategy[opponent][mine] + mine + 1;
    }

    public int playWithOutcome(int opponent, int mine) {
        return outcomeStrategy[opponent][mine] + (mine * 3);
    }
}
