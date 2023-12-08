package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.MathUtils;
import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Day8 extends Day {

    private String route;
    private Map<String, Node> cache;

    public static void main(String[] args) {
        new Day8().process();
    }

    public Day8() {
        super(8, "Haunted Wasteland");
    }

    @Override
    public void preload() {
        List<String> map = getInput(Collectors.toCollection(ArrayList::new));

        route = map.remove(0);

        map.remove(0);
        cache = new HashMap<>();

        while (!map.isEmpty()) {
            String description = map.remove(0);
            String id = description.substring(0, description.indexOf(' '));
            String leftId = description.substring(description.indexOf('(') + 1, description.indexOf(','));
            String rightID = description.substring(description.lastIndexOf(' ') + 1, description.indexOf(')'));

            Node node = cache.computeIfAbsent(id, Node::new);
            Node left = cache.computeIfAbsent(leftId, Node::new);
            Node right = cache.computeIfAbsent(rightID, Node::new);
            node.setLeft(left).setRight(right);
        }
    }

    @Override
    public Object part1() {
        Node current = cache.get("AAA");
        Node end = cache.get("ZZZ");
        int routePointer = 0;
        long steps = 0;
        while (current != end) {
            if (routePointer >= route.length()) {
                routePointer = 0;
            }
            current = next(current, routePointer++);
            steps++;
        }
        return steps;
    }

    // Iterating until it synced up was taking far too long, people were suggesting LCM of the loop size
    // Loop size just happens to be the first time it takes to reach Z
    // This feels like the wrong approach but it works 🙃
    // No wonder it took forever 13_524_038_372_771 is quite a large number :D
    @Override
    public Object part2() {
        Node[] nodes = cache.entrySet().stream()
                .filter(e -> e.getKey().endsWith("A"))
                .map(Entry::getValue)
                .toArray(Node[]::new);

        long[] distances = new long[nodes.length];
        Arrays.fill(distances, -1);
        int distancesComplete = 0;
        int routePointer = 0;
        long steps = 0;

        while (distancesComplete != distances.length) {
            if (routePointer >= route.length()) {
                routePointer = 0;
            }

            for (int i = 0; i < distances.length; i++) {
                if (distances[i] != -1) {
                    continue;
                }
                nodes[i] = next(nodes[i], routePointer);
                if (nodes[i].id().endsWith("Z")) {
                    distances[i] = steps + 1;
                    distancesComplete++;
                }
            }
            routePointer++;
            steps++;
        }

        return MathUtils.lcm(distances);
    }

    private Node next(Node current, int pointer) {
        char direction = route.charAt(pointer);
        return direction == 'L' ? current.left() : current.right();
    }

    static final class Node {

        private final String id;
        private Node left;
        private Node right;

        Node(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }

        public Node left() {
            return left;
        }

        public Node right() {
            return right;
        }

        public Node setLeft(Node left) {
            this.left = left;
            return this;
        }

        public Node setRight(Node right) {
            this.right = right;
            return this;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (Node) obj;
            return Objects.equals(this.id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override
        public String toString() {
            return "Node[" + "id=" + id + ", " + "left=" + left.id + ", " + "right=" + right.id + ']';
        }
    }
}
