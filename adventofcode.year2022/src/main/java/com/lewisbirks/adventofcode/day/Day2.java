package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public final class Day2 extends Day {

    private static final int ROCK_SCORE = 1;
    private static final int PAPER_SCORE = 2;
    private static final int SCISSORS_SCORE = 3;

    private static final int DRAW = 3;
    private static final int WIN = 6;
    private static final int LOSE = 0;

    private final Supplier<List<Strategy>> strategyGuide;

    public Day2() {
        super(2, "Rock Paper Scissors");
        strategyGuide = CachedSupplier.memoize(() -> getInput(Strategy::from));
    }

    @Override
    protected void preLoad() {
        strategyGuide.get();
    }

    @Override
    protected Object part1() {
        return strategyGuide.get().stream().mapToInt(Strategy::getScore).sum();
    }

    @Override
    protected Object part2() {
        return null;
    }

    record Strategy(Move opponent, Move mine) {
        public static Strategy from(String strategy) {
            String[] split = strategy.split(" ");
            return new Strategy(
                Move.from(split[0]),
                Move.from(split[1])
            );
        }

        public int getScore() {
            return result() + mine.getScore();
        }

        private int result() {
            return switch (opponent) {
                case ROCK -> mine == Move.ROCK ? DRAW : mine == Move.PAPER ? WIN : LOSE;
                case PAPER -> mine == Move.PAPER ? DRAW : mine == Move.SCISSORS ? WIN : LOSE;
                case SCISSORS -> mine == Move.SCISSORS ? DRAW : mine == Move.ROCK ? WIN : LOSE;
            };
        }

        private enum Move {
            ROCK(ROCK_SCORE, "A", "X"), PAPER(PAPER_SCORE, "B", "Y"), SCISSORS(SCISSORS_SCORE, "C", "Z");

            private final List<String> options;
            private final int score;
            Move(int score, String... options) {
                this.options = List.of(options);
                this.score = score;
            }

            public static Move from(String move) {
                return Arrays.stream(Move.values()).filter(m -> m.options.contains(move)).findFirst().orElseThrow();
            }

            public int getScore() {
                return score;
            }
        }
    }

}
