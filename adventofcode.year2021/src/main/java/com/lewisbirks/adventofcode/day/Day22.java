package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.model.point.Point3D;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 extends Day {

    public static final int PART_ONE_BOUNDS = 50;

    public Day22() {
        super(22, "Reactor Reboot");
    }

    public static void main(String[] args) {
        new Day22().process();
    }

    @Override
    protected Object part1() {
        List<Step> steps = getInput(Step::of);

        Set<Point3D> cubes = new HashSet<>();

        for (Step step : steps) {
            Set<Point3D> stepCubes = step.getCubes();
            if (step.isOn()) {
                cubes.addAll(stepCubes);
            } else {
                cubes.removeAll(stepCubes);
            }
        }

        return cubes.size();
        //        List<Range<Long>> xs = new ArrayList<>();
        //        List<Range<Long>> ys = new ArrayList<>();
        //        List<Range<Long>> zs = new ArrayList<>();
        //
        //        for (Step step : steps) {
        //            System.out.println("Processing " + step);
        //            System.out.println("Current xs: " + xs);
        //            System.out.println("Current ys: " + ys);
        //            System.out.println("Current zs: " + zs);
        //            if (step.isOn()) {
        //                System.out.print("\tXS ");
        //                merge(xs, step.x());
        //                System.out.print("\tYS ");
        //                merge(ys, step.y());
        //                System.out.print("\tZS ");
        //                merge(zs, step.z());
        //            } else {
        //                System.out.print("\tXS ");
        //                split(xs, step.x());
        //                System.out.print("\tYS ");
        //                split(ys, step.y());
        //                System.out.print("\tZS ");
        //                split(zs, step.y());
        //            }
        //            System.out.println("\tnum cubes: " + numCubes(xs, ys, zs) );
        //        }
        //
        //        System.out.println(xs);
        //        System.out.println(ys);
        //        System.out.println(zs);
        //
        //        return numCubes(xs, ys, zs);
    }

    //    private long numCubes(List<Range<Long>> xs, List<Range<Long>> ys, List<Range<Long>> zs) {
    //        long x = xs.stream().map(r -> Math.abs(r.getMaximum() - r.getMinimum() + 1)).reduce(0L, Long::sum);
    //        long y = ys.stream().map(r -> Math.abs(r.getMaximum() - r.getMinimum() + 1)).reduce(0L, Long::sum);
    //        long z = zs.stream().map(r -> Math.abs(r.getMaximum() - r.getMinimum() + 1)).reduce(0L, Long::sum);
    //
    //        return x * y * z;
    //    }
    //
    //    private void merge(List<Range<Long>> ranges, Range<Long> range) {
    //        // check if any existing ranges contains the current range
    //        if (ranges.stream().anyMatch(r -> r.containsRange(range))) {
    //            // one found skip
    //            System.out.println("already in");
    //            return;
    //        }
    //        // remove anything that is a subrange of the current range
    ////        ranges.removeIf(range::containsRange);
    //        List<Range<Long>> removed = ranges.stream().filter(range::containsRange).toList();
    //        if (!removed.isEmpty()) {
    //            System.out.print("removed " + removed + " ");
    //            ranges.removeAll(removed);
    //        }
    //        // get from the current ranges one that contains the end of the current range
    //        Optional<Range<Long>> partialRange = ranges.stream()
    //            .filter(r -> r.contains(range.getMaximum()))
    //            .findFirst();
    //
    //        if (partialRange.isPresent()) {
    //            ranges.remove(partialRange.get());
    //            System.out.print("removed " + partialRange.get() + " ");
    //            ranges.add(Range.between(range.getMinimum(), partialRange.get().getMaximum()));
    //            System.out.println("added " + Range.between(range.getMinimum(), partialRange.get().getMaximum()));
    //            return;
    //        }
    //
    //        // get from the current ranges one that contains the start of the current range
    //        partialRange = ranges.stream()
    //            .filter(r -> r.contains(range.getMinimum()))
    //            .findFirst();
    //
    //        if (partialRange.isPresent()) {
    //            ranges.remove(partialRange.get());
    //            System.out.print("removed " + partialRange.get() + " ");
    //            ranges.add(Range.between(partialRange.get().getMinimum(), range.getMaximum()));
    //            System.out.println("added " + Range.between(partialRange.get().getMinimum(), range.getMaximum()));
    //            return;
    //        }
    //
    //        // no existing range can be updated to fit so just add
    //        System.out.println("added " + range);
    //        ranges.add(range);
    //    }
    //
    //    private void split(List<Range<Long>> ranges, Range<Long> range) {
    //        // check if any existing ranges overlaps the current range
    //        if (ranges.stream().noneMatch(r -> r.isOverlappedBy(range))) {
    //            // none do so nothing needs removing
    //            System.out.println("doing nothing");
    //            return;
    //        }
    //
    //        // remove any that are a sub subrange
    //        ranges.removeIf(range::containsRange);
    //
    //        Optional<Range<Long>> contains = ranges.stream().filter(r -> r.containsRange(range)).findFirst();
    //
    //        if (contains.isPresent()) {
    //            // split the current range
    //            ranges.remove(contains.get());
    //            System.out.print("removed " + contains.get() + " ");
    //            ranges.add(Range.between(contains.get().getMinimum(), range.getMinimum() + 1));
    //            ranges.add(Range.between(range.getMaximum() + 1, contains.get().getMaximum()));
    //            System.out.println("added " + Range.between(contains.get().getMinimum(), range.getMinimum() + 1) +
    //            ", " + Range.between(range.getMaximum() + 1, contains.get().getMaximum()));
    //            return;
    //        }
    //
    //        // check if there are any ranges that contain in the start of the range to search
    //        contains = ranges.stream().filter(r -> r.contains(range.getMinimum())).findFirst();
    //
    //        if (contains.isPresent()) {
    //            ranges.remove(contains.get());
    //            System.out.print("removed " + contains.get() + " ");
    //            ranges.add(Range.between(contains.get().getMinimum(), range.getMinimum() - 1));
    //            System.out.println("added " + Range.between(contains.get().getMinimum(), range.getMinimum() - 1));
    //        }
    //
    //        // check if there are any ranges that contain in the start of the range to search
    //        contains = ranges.stream().filter(r -> r.contains(range.getMaximum())).findFirst();
    //
    //        if (contains.isPresent()) {
    //            ranges.remove(contains.get());
    //            System.out.print("removed " + contains.get() + " ");
    //            ranges.add(Range.between(range.getMaximum() + 1, contains.get().getMaximum()));
    //            System.out.println("added " + Range.between(range.getMaximum() + 1, contains.get().getMaximum()));
    //        }
    //    }

    @Override
    protected Object part2() {
        return null;
    }

    record Step(boolean status, int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        private static final Pattern STEP_PATTERN = Pattern.compile(
            "(on|off) x=(-?\\d+)\\.\\.(-?\\d+),y=(-?\\d+)\\.\\.(-?\\d+),z=(-?\\d+)\\.\\.(-?\\d+)"
        );

        public static Step of(String line) {
            Matcher matcher = STEP_PATTERN.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException(line);
            }
            boolean status = "on".equals(matcher.group(1));
            int minX = Integer.parseInt(matcher.group(2));
            int maxX = Integer.parseInt(matcher.group(3));
            int minY = Integer.parseInt(matcher.group(4));
            int maxY = Integer.parseInt(matcher.group(5));
            int minZ = Integer.parseInt(matcher.group(6));
            int maxZ = Integer.parseInt(matcher.group(7));

            return new Step(status, minX, maxX, minY, maxY, minZ, maxZ);
        }

        public boolean isOn() {
            return status;
        }

        public Set<Point3D> getCubes() {
            Set<Point3D> cubes = new HashSet<>();
            for (int x = Math.max(minX(), -PART_ONE_BOUNDS); x <= Math.min(maxX(), PART_ONE_BOUNDS); x++) {
                for (int y = Math.max(minY(), -PART_ONE_BOUNDS); y <= Math.min(maxY(), PART_ONE_BOUNDS); y++) {
                    for (int z = Math.max(minZ(), -PART_ONE_BOUNDS); z <= Math.min(maxZ(), PART_ONE_BOUNDS); z++) {
                        cubes.add(new Point3D(x, y, z));
                    }
                }
            }
            return cubes;
        }
    }


    //    record Step(boolean status, Range<Long> x, Range<Long> y, Range<Long> z) {
    //        private static final Pattern STEP_PATTERN = Pattern.compile(
    //            "(on|off) x=(-?\\d+)\\.\\.(-?\\d+),y=(-?\\d+)\\.\\.(-?\\d+),z=(-?\\d+)\\.\\.(-?\\d+)"
    //        );
    //
    //        public static Step of(String line) {
    //            Matcher matcher = STEP_PATTERN.matcher(line);
    //            if (!matcher.matches()) {
    //                throw new IllegalArgumentException(line);
    //            }
    //            boolean status = "on".equals(matcher.group(1));
    //            long minX = Long.parseLong(matcher.group(2));
    //            long maxX = Long.parseLong(matcher.group(3));
    //            long minY = Long.parseLong(matcher.group(4));
    //            long maxY = Long.parseLong(matcher.group(5));
    //            long minZ = Long.parseLong(matcher.group(6));
    //            long maxZ = Long.parseLong(matcher.group(7));
    //
    //            return new Step(status, Range.between(minX, maxX), Range.between(minY, maxY), Range.between(minZ, maxZ));
    //        }
    //
    //        public boolean isOn() {
    //            return status;
    //        }
    //    }
}
