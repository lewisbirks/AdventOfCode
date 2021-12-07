package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.utils.bingo.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Day4 extends Day {

    private List<Integer> values;
    private List<Board> boards;

    public Day4() {
        super(4, "Giant Squid");
    }

    @Override
    protected Object part1() {
        readBoards();
        for (Integer number : values) {
            for (Board board : boards) {
                board.mark(number);
                if (board.bingo()) {
                    return board.score(number);
                }
            }
        }
        return null;
    }

    @Override
    protected Object part2() {
        readBoards();
        List<Board> boards = new ArrayList<>(this.boards);
        Board lastWinner = Board.empty();
        int lastNumber = 0;
        for (int number : values) {
            for (int i = 0; i < boards.size(); i++) {
                Board board = boards.get(i);
                board.mark(number);
                if (board.bingo()) {
                    lastWinner = board;
                    lastNumber = number;
                    boards.remove(board);
                    i--;
                }
            }
            if (boards.isEmpty()) {
                break;
            }
        }
        return lastWinner.score(lastNumber);
    }

    private void readBoards() {
        if (values != null) {
            boards.forEach(Board::reset);
        }

        List<String> lines = getInput(Collectors.toCollection(ArrayList::new));

        String values = lines.remove(0);
        this.values = Arrays.stream(values.split(",")).map(Integer::parseInt).toList();
        lines.remove(0); // empty line

        List<Board> boards = new ArrayList<>();
        while (!lines.isEmpty()) {
            List<String> boardLines = new ArrayList<>();
            String line;
            while (!lines.isEmpty() && !(line = lines.remove(0)).isBlank()) {
                boardLines.add(line);
            }
            boards.add(Board.of(boardLines));
        }
        this.boards = Collections.unmodifiableList(boards);
    }
}
