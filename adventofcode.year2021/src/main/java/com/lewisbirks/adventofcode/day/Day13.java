package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.point.Point;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class Day13 extends Day {

    private List<Point> folds;
    private List<Point> marks;

    public Day13() {
        super(13, "Transparent Origami");
    }

    @Override
    protected Object part1() {
        read();
        return fold(folds.get(0), this.marks).size();
    }

    @Override
    protected Object part2() {
        read();
        Collection<Point> marks = this.marks;
        for (Point fold : folds) {
            marks = fold(fold, marks);
        }
        printPaper(marks);
        return "";
    }

    private Collection<Point> fold(final Point fold, Collection<Point> marks) {
        boolean foldX = fold.x() > fold.y();
        return marks.stream()
            .map(mark -> foldX ? (fold.x() < mark.x() ? new Point(fold.x() - (mark.x() - fold.x()), mark.y()) : mark)
                               : (fold.y() < mark.y() ? new Point(mark.x(), fold.y() - (mark.y() - fold.y())) : mark)
            ).collect(Collectors.toSet());
    }

    private void read() {
        if (folds != null) {
            return;
        }

        List<String> inputs = getInput();
        List<String> markInputs = inputs.subList(0, inputs.indexOf(""));
        List<String> foldInputs = inputs.subList(inputs.indexOf("") + 1, inputs.size());

        this.marks = markInputs.stream().map(Point::of).toList();
        this.folds = foldInputs.stream()
            .map(line -> {
                String[] fold = line.substring(11).split("=");
                return fold[0].equals("x") ? new Point(Integer.parseInt(fold[1]), -1)
                                           : new Point(-1, Integer.parseInt(fold[1]));
            })
            .toList();
    }

    private void printPaper(Collection<Point> points) {
        int maxX = points.stream().max(Comparator.comparingInt(Point::x)).map(Point::x).orElse(0) + 1;
        int maxY = points.stream().max(Comparator.comparingInt(Point::y)).map(Point::y).orElse(0) + 1;
        System.out.println();
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                System.out.print(points.contains(new Point(x, y)) ? "██" : "  ");
            }
            System.out.println();
        }
    }
}
