package com.lewisbirks.adventofcode.model.bingo;

import java.util.Arrays;
import java.util.List;

import static java.util.function.Predicate.not;

public record Board(BingoNumber[][] board) {
    public static Board empty() {
        return new Board(new BingoNumber[0][0]);
    }

    public static Board of(List<String> values) {
        BingoNumber[][] boardValues = values.stream().map(line -> line.split(" "))
            .map(strings -> Arrays.stream(strings)
                .filter(not(String::isBlank))
                .map(num -> new BingoNumber(Integer.parseInt(num)))
                .toArray(BingoNumber[]::new))
            .toArray(BingoNumber[][]::new);
        return new Board(boardValues);
    }

    public void mark(int value) {
        for (BingoNumber[] bingoNumbers : board) {
            for (BingoNumber bingoNumber : bingoNumbers) {
                if (bingoNumber.value() == value) {
                    bingoNumber.setMarked(true);
                    break;
                }
            }
        }
    }

    public boolean bingo() {
        for (BingoNumber[] bingoNumbers : board) {
            boolean rowMarked = true;
            for (BingoNumber bingoNumber : bingoNumbers) {
                if (!bingoNumber.marked()) {
                    rowMarked = false;
                    break;
                }
            }
            if (rowMarked) {
                return true;
            }
        }

        for (int i = 0; i < board[0].length; i++) {
            boolean columnMarked = true;
            for (BingoNumber[] bingoNumbers : board) {
                if (!bingoNumbers[i].marked()) {
                    columnMarked = false;
                    break;
                }
            }
            if (columnMarked) {
                return true;
            }
        }
        return false;
    }

    public long score(int finalValue) {
        long unmarkedSum = Arrays.stream(board)
            .flatMap(row -> Arrays.stream(row)
                .filter(not(BingoNumber::marked))
                .map(BingoNumber::value))
            .mapToLong(Long::valueOf)
            .sum();
        return finalValue * unmarkedSum;
    }
}
