package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.Scanner;
import com.lewisbirks.adventofcode.model.point.Point3D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Day19 extends Day {

    private static final Point3D CENTER = new Point3D(0, 0, 0);
    private static final String SCANNER = "SCANNER";
    private static final String LOCATIONS = "LOCATIONS";
    private Map<String, Object> processedScanners;

    public Day19() {
        super(19, "Beacon Scanner");
    }

    public static void main(String[] args) {
        new Day19().process();
    }

    @Override
    protected Object part1() {
        if (processedScanners == null) {
            processedScanners = findScanners();
        }
        Scanner basScanner = (Scanner) processedScanners.get(SCANNER);
        return basScanner.coordinates().size();
    }

    @Override
    protected Object part2() {
        if (processedScanners == null) {
            processedScanners = findScanners();
        }
        List<?> scannerLocations = (List<?>) processedScanners.get(LOCATIONS);
        int max = 0;
        for (Object s1 : scannerLocations) {
            for (Object s2 : scannerLocations) {
                if (s1 != s2) {
                    max = Math.max(max, ((Point3D) s1).distance((Point3D) s2));
                }
            }
        }
        return max;
    }

    private Map<String, Object> findScanners() {
        List<Scanner> scanners = new ArrayList<>();
        String[] unprocessedScanners = readInput().split(System.lineSeparator() + System.lineSeparator());
        for (String s : unprocessedScanners) {
            if (!s.isBlank()) {
                scanners.add(Scanner.of(s));
            }
        }

        Scanner baseScanner = scanners.remove(0);
        List<Point3D> scannerLocations = new ArrayList<>(List.of(CENTER));

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
                        scannerLocations.add(translationPoint);
                        // no longer need to search for the scanner
                        iterator.remove();
                        found = true;
                        break;
                    }
                }
            }
        }
        return Map.of(SCANNER, baseScanner, LOCATIONS, scannerLocations);
    }
}
