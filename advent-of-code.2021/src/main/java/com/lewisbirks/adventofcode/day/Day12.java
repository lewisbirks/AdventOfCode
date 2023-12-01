package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.collection.MultiValueMap;
import com.lewisbirks.adventofcode.common.domain.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Day12 extends Day {

    private static final String START = "start";
    private static final String END = "end";

    private MultiValueMap<String, String> routes;

    public Day12() {
        super(12, "Passage Pathing");
    }

    @Override
    protected void preLoad() {
        routes = new MultiValueMap<>();
        getInput().stream().map(e -> e.split("-")).forEach(a -> {
            routes.put(a[0], a[1]);
            routes.put(a[1], a[0]);
        });
    }

    @Override
    protected Object part1() {
        return getRoutesCount(START, routes, Map.of(START, 1), 1);
    }

    @Override
    protected Object part2() {
        return getRoutesCount(START, routes, Map.of(START, 2), 2);
    }

    private int getRoutesCount(final String current, final MultiValueMap<String, String> routesMap,
                               final Map<String, Integer> visited, final int maxVisits) {
        if (END.equals(current)) {
            return 1;
        }
        int totalRoutes = 0;
        List<String> availableRoutes = routesMap.get(current);
        for (String route : availableRoutes) {
            if (!canVisit(route, visited, maxVisits)) {
                continue;
            }
            Map<String, Integer> visitedClone = new HashMap<>(visited);
            if (isSmallCave(route)) {
                visitedClone.merge(route, 1, Integer::sum);
            }
            totalRoutes += getRoutesCount(route, routesMap, visitedClone, maxVisits);
        }
        return totalRoutes;
    }

    private boolean canVisit(String route, Map<String, Integer> visited, int maxVisits) {
        long maxVisitsCount = visited.values().stream().filter(visits -> visits == maxVisits).count();
        return !visited.containsKey(route) || (visited.getOrDefault(route, 0) < maxVisits && maxVisitsCount <= 1);
    }

    private boolean isSmallCave(String route) {
        return route.toLowerCase().equals(route);
    }
}
