package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.cache.CachedSupplier;
import com.lewisbirks.adventofcode.common.domain.Day;
import com.lewisbirks.adventofcode.utils.MultiValueMap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public final class Day12 extends Day {

    private static final String START = "start";
    private static final String END = "end";

    private final Supplier<MultiValueMap<String, String>> routesSupplier;

    public Day12() {
        super(12, "Passage Pathing");
        routesSupplier = CachedSupplier.memoize(() -> {
            MultiValueMap<String, String> map = new MultiValueMap<>();
            getInput().stream().map(e -> e.split("-")).forEach(a -> {
                map.put(a[0], a[1]);
                map.put(a[1], a[0]);
            });
            return map.immutable();
        });
    }

    @Override
    protected Object part1() {
        return getRoutesCount(START, routesSupplier.get(), Set.of(START));
    }

    private int getRoutesCount(String current, MultiValueMap<String, String> map, Set<String> visited) {
        if (END.equals(current)) {
            return 1;
        }
        int totalRoutes = 0;
        List<String> availableRoutes = map.get(current);
        for (String route : availableRoutes) {
            if (visited.contains(route)) {
                continue;
            }
            Set<String> visitedClone = new HashSet<>(visited);
            if (isSmallCave(route)) {
                visitedClone.add(route);
            }
            totalRoutes += getRoutesCount(route, map, visitedClone);
        }
        return totalRoutes;
    }

    private boolean isSmallCave(String route) {
        return route.toLowerCase().equals(route);
    }

    @Override
    protected Object part2() {
        return null;
    }


}
