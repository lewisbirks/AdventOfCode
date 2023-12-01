package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.Scanner;
import com.lewisbirks.adventofcode.model.point.Point3D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Day19 extends Day {

    private static final Point3D CENTER = new Point3D(0, 0, 0);

    private List<Scanner> scanners;
    private List<Point3D> scannerLocations;

    public Day19() {
        super(19, "Beacon Scanner");
    }

    @Override
    protected void preLoad() {
        scanners = new ArrayList<>();
        String[] unprocessedScanners = readInput().split(System.lineSeparator() + System.lineSeparator());
        for (String s : unprocessedScanners) {
            if (!s.isBlank()) {
                scanners.add(Scanner.of(s));
            }
        }
    }

    @Override
    protected Object part1() {
        scannerLocations = new ArrayList<>(List.of(CENTER));
        List<Scanner> scanners = new ArrayList<>(this.scanners);
        Scanner baseScanner = scanners.remove(0);
        while (!scanners.isEmpty()) {
            Iterator<Scanner> iterator = scanners.iterator();
            boolean found = false;
            while (iterator.hasNext() && !found) {
                Scanner scanner = iterator.next();
                // search for a scanners locations
                // find translation point relative to the baseScanner
                for (Scanner rotatedScanner : scanner.getAllRotations()) {
                    Point3D translationPoint = baseScanner.findTranslationPoint(rotatedScanner);
                    if (translationPoint != null) {
                        // found a location
                        baseScanner.add(rotatedScanner, translationPoint);
                        // might as well add this here, otherwise we are just going to have to repeat part 1 in part 2
                        scannerLocations.add(translationPoint);
                        // no longer need to search for the scanner
                        iterator.remove();
                        found = true;
                        break;
                    }
                }
            }
        }
        return baseScanner.coordinates().size();
    }

    @Override
    protected Object part2() {
        int max = 0;
        for (Point3D s1 : scannerLocations) {
            for (Point3D s2 : scannerLocations) {
                if (s1 != s2) {
                    max = Math.max(max, s1.distance(s2));
                }
            }
        }
        return max;
    }
}
