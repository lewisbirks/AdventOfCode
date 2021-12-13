package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.utils.point.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 extends Day {

    private List<Point> folds;
    private boolean[][] paper;

    public Day13() {
        super(13, "Transparent Origami");
    }

    @Override
    protected Object part1() {
        read();
        boolean[][] paper = copyPaper(this.paper);
        paper = fold(folds.get(0), paper);
        return countDots(paper);
    }

    @Override
    protected Object part2() {
        read();
        boolean[][] paper = copyPaper(this.paper);
        for (Point fold : folds) {
            paper = fold(fold, paper);
        }
        printPaper(paper);
        return null;
    }

    private boolean[][] fold(Point foldPoint, boolean[][] paper) {
        boolean foldX = foldPoint.x() > foldPoint.y();

        int deltaReverseX = foldX ? -1 : 1;
        int deltaReverseY = foldX ? 1 : -1;

        int y = Math.max(0, foldPoint.y() + 1);
        for (int reverseY = foldX ? y : foldPoint.y() - 1; y < paper.length; y++, reverseY += deltaReverseY) {
            int x = Math.max(0, foldPoint.x() + 1);
            for (int reverseX = foldX ? foldPoint.x() - 1 : x; x < paper[y].length; x++, reverseX += deltaReverseX) {
                paper[reverseY][reverseX] = paper[reverseY][reverseX] || paper[y][x];
            }
        }

        boolean[][] reducedPaper = new boolean[!foldX ? foldPoint.y() : paper.length][];
        Arrays.setAll(reducedPaper, i -> Arrays.copyOf(paper[i], foldX ? foldPoint.x() : paper[i].length));
        return reducedPaper;
    }

    private int countDots(boolean[][] paper) {
        int count = 0;
        for (boolean[] row : paper) {
            for (boolean b : row) {
                count += (b ? 1 : 0);
            }
        }
        return count;
    }

    private void read() {
        if (folds != null) {
            return;
        }

        List<String> inputs = getInput(Collectors.toCollection(ArrayList::new));

        List<Point> points = new ArrayList<>();
        int maxX = 0, maxY = 0;
        String line;
        while (!(line = inputs.remove(0)).isBlank()) {
            Point mark = Point.of(line);
            points.add(mark);
            maxX = Math.max(maxX, mark.x());
            maxY = Math.max(maxY, mark.y());
        }
        List<Point> folds = new ArrayList<>();
        while (!inputs.isEmpty()) {
            line = inputs.remove(0).substring(11);
            String[] fold = line.split("=");
            Point foldPoint;
            if (fold[0].equals("x")) {
                foldPoint = new Point(Integer.parseInt(fold[1]), -1);
            } else {
                foldPoint = new Point(-1, Integer.parseInt(fold[1]));
            }
            folds.add(foldPoint);
        }
        this.paper = new boolean[maxY + 1][maxX + 1];
        points.forEach(point -> this.paper[point.y()][point.x()] = true);
        this.folds = folds;
    }

    private boolean[][] copyPaper(boolean[][] paper) {
        return Arrays.stream(paper).map(boolean[]::clone).toArray(boolean[][]::new);
    }

    private void printPaper(boolean[][] paper) {
        System.out.println();
        Arrays.stream(paper).forEach(row -> {
            for (boolean b : row) {
                System.out.print(b ? "#" : " ");
            }
            System.out.println();
        });
    }
}
