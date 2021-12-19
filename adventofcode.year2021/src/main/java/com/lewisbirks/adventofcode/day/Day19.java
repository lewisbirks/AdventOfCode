package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.Pair;
import com.lewisbirks.adventofcode.model.Scanner;
import com.lewisbirks.adventofcode.model.point.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Day19 extends Day {

    private static final Point3D CENTER = new Point3D(0, 0, 0);
    private Pair<Scanner, List<Point3D>> processedScanners;

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
        Scanner basScanner = processedScanners.left();
        return basScanner.coordinates().size();
    }

    @Override
    protected Object part2() {
        if (processedScanners == null) {
            processedScanners = findScanners();
        }
        List<Point3D> scannerLocations = processedScanners.right();
        return scannerLocations.stream()
            .flatMap(s1 -> scannerLocations.stream().filter(s2 -> s1 != s2).map(s1::distance))
            .max(Integer::compareTo).orElse(0);
    }

    private Pair<Scanner, List<Point3D>> findScanners() {
        List<Scanner> scanners = Arrays.stream(readInput().split(System.lineSeparator() + System.lineSeparator()))
            .filter(Predicate.not(String::isBlank))
            .map(Scanner::of)
            .collect(Collectors.toCollection(ArrayList::new));

        Scanner baseScanner = scanners.remove(0);
        List<Point3D> scannerLocations = new ArrayList<>(List.of(CENTER));

        while (!scanners.isEmpty()) {
            Iterator<Scanner> iterator = scanners.iterator();
            while (iterator.hasNext()) {
                Scanner scanner = iterator.next();
                // search for a scanners locations
                Optional<Pair<Scanner, Point3D>> possibleScannerLocation = scanner.getAllRotations().stream()
                    // find translation point relative to the baseScanner
                    .map(t -> new Pair<>(t, baseScanner.findTranslationPoint(t)))
                    .filter(pair -> pair.right() != null)
                    .findFirst();
                if (possibleScannerLocation.isPresent()) {
                    // found a location
                    Pair<Scanner, Point3D> scannerAndLocation = possibleScannerLocation.get();
                    scannerLocations.add(scannerAndLocation.right());
                    baseScanner.add(scannerAndLocation.left(), scannerAndLocation.right());
                    // no longer need to search for the scanner
                    iterator.remove();
                    break;
                }
            }
        }
        return new Pair<>(baseScanner, scannerLocations);
    }

}
