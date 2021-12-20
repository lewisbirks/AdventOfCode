package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.point.Point;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Day20 extends Day {

    private Set<Point> image;
    private BitSet algorithm;

    public Day20() {
        super(20, "Trench Map");
    }

    public static void main(String[] args) {
        new Day20().process();
    }

    @Override
    protected Object part1() {
        parse();
        return processImage(image, algorithm, 2).size();
    }

    @Override
    protected Object part2() {
        return null;
    }

    private Set<Point> processImage(Set<Point> image, BitSet algorithm, int iterations) {
        Set<Point> processedImage = new HashSet<>(image);
        boolean voidFlip = false;
        for (int i = 0; i < iterations; i++) {
            processedImage = processImage(processedImage, algorithm, voidFlip);
            voidFlip = algorithm.get(voidFlip ? 511 : 0);
            //            print(processedImage);
        }
        return processedImage;
    }

    private Set<Point> processImage(Set<Point> image, BitSet algorithm, boolean voidFlip) {
        var xStats = image.stream().mapToInt(Point::x).summaryStatistics();
        var yStats = image.stream().mapToInt(Point::y).summaryStatistics();
        var bounds = new Bounds(xStats.getMin(), xStats.getMax(), yStats.getMin(), yStats.getMax());

        var newImage = new HashSet<Point>();
        for (int y = bounds.minY - 1; y < bounds.maxY + 2; y++) {
            for (int x = bounds.minX - 1; x < bounds.maxX + 2; x++) {
                int value = getValue(image, x, y, bounds, voidFlip);
                if (algorithm.get(value)) {
                    newImage.add(new Point(x, y));
                }
            }
        }

        return newImage;
    }

    private int getValue(Set<Point> image, int x, int y, Bounds bounds, boolean voidFlip) {
        int value = 0;
        for (int dy = y - 1; dy <= y + 1; dy++) {
            for (int dx = x - 1; dx <= x + 1; dx++) {
                // always shift the number
                value <<= 1;
                // only add a point if it corresponds to an existing point, or it is outside and we are on the correct
                // the void maps to '#'
                // voidFlip will alternate between true and false because a quirk of the real input is that the
                // void blinks due to the characteristics in the given algorithm
                // algo[0] == '#' and algo[511] == '.'
                if ((voidFlip && isOutside(bounds, dx, dy)) || image.contains(new Point(dx, dy))) {
                    value += 1;
                }
            }
        }
        return value;
    }

    private boolean isOutside(Bounds bounds, int x, int y) {
        return y < bounds.minY() || y > bounds.maxY() || x < bounds.minX() || x > bounds.maxX();
    }

    private void parse() {
        if (algorithm != null) {
            return;
        }

        List<String> input = new ArrayList<>(getInput());
        String alg = input.remove(0);
        List<String> img = input.subList(1, input.size());
        BitSet algorithm = new BitSet(alg.length());
        for (int i = 0; i < alg.length(); i++) {
            if (alg.charAt(i) == '#') {
                algorithm.set(i);
            }
        }

        // not representing image as a 2d array as that will almost certainly come back to haunt me in part 2, the
        // infinite void screams that part 2 will be more iterations which will probably ruin performance of an array
        // depending on the growth of the image
        Set<Point> image = new HashSet<>();
        for (int y = 0; y < img.size(); y++) {
            for (int x = 0; x < img.get(y).length(); x++) {
                if (img.get(y).charAt(x) == '#') {
                    image.add(new Point(x, y));
                }
            }
        }

        this.image = image;
        this.algorithm = algorithm;
    }

    private void print(Set<Point> image) {
        var xStats = image.stream().mapToInt(Point::x).summaryStatistics();
        var yStats = image.stream().mapToInt(Point::y).summaryStatistics();
        Bounds bounds = new Bounds(xStats.getMin(), xStats.getMax(), yStats.getMin(), yStats.getMax());

        System.out.println("Grid:");
        for (int y = bounds.minY(); y < bounds.maxY() + 1; y++) {
            System.out.printf("%3d ", y);
            for (int x = bounds.minX(); x < bounds.maxX() + 1; x++) {
                System.out.print(image.contains(new Point(x, y)) ? "#" : ".");
            }
            System.out.println();
        }
    }

    record Bounds(int minX, int maxX, int minY, int maxY) {}
}
