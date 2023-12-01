package com.lewisbirks.adventofcode.model;

import static com.lewisbirks.adventofcode.model.point.Point3D.sequence;

import com.lewisbirks.adventofcode.common.collection.FrequencyMap;
import com.lewisbirks.adventofcode.common.primitive.ReferenceInt;
import com.lewisbirks.adventofcode.model.point.Point3D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// name only included for ease of debugging
public record Scanner(String name, List<Point3D> coordinates) {
    private static final int NUM_ROTATIONS = 24;

    public static Scanner of(String points) {
        List<String> split = Arrays.asList(points.split(System.lineSeparator()));
        String name = split.get(0);
        List<Point3D> coordinates = split.subList(1, split.size()).stream()
                .filter(Predicate.not(String::isBlank))
                .map(Point3D::of)
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
        return new Scanner(name, coordinates);
    }

    public Point3D findTranslationPoint(Scanner other) {
        // get the differences between all points
        FrequencyMap<Point3D> pointFrequencies = new FrequencyMap<>();
        coordinates.forEach(coordinate ->
                other.coordinates.stream().map(coordinate::subtract).forEach(pointFrequencies::put));

        List<Map.Entry<Point3D, Long>> reoccurringPoints = pointFrequencies.entriesMatching(e -> e.getValue() >= 12);
        return reoccurringPoints.isEmpty() ? null : reoccurringPoints.get(0).getKey();
    }

    public List<Scanner> getAllRotations() {
        List<ArrayList<Point3D>> allRotations = IntStream.range(0, NUM_ROTATIONS)
                .mapToObj(i -> new ArrayList<Point3D>(coordinates.size()))
                .toList();
        for (Point3D coordinate : coordinates) {
            List<Point3D> pointRotations = sequence(coordinate);
            IntStream.range(0, NUM_ROTATIONS).forEach(i -> allRotations.get(i).add(pointRotations.get(i)));
        }
        ReferenceInt i = new ReferenceInt();
        return allRotations.stream()
                .map(coordinates -> new Scanner(name + " rot " + (i.value++), coordinates))
                .toList();
    }

    public void add(Scanner other, Point3D translation) {
        // add all the scanner points relative to the translation point
        for (Point3D coordinate : other.coordinates) {
            Point3D translatedCoordinate = coordinate.add(translation);
            if (!coordinates.contains(translatedCoordinate)) {
                coordinates.add(translatedCoordinate);
            }
        }
    }
}
