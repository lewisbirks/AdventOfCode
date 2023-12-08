package com.lewisbirks.adventofcode.day;

import com.lewisbirks.adventofcode.common.domain.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Day8 extends Day {

  private String route;
  private Node start;
  private Node end;

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
    Map<String, Node> cache = new HashMap<>();

    while (!map.isEmpty()) {
      String description = map.remove(0);
      String id = description.substring(0, description.indexOf(' '));
      String leftId = description.substring(description.indexOf('(') + 1, description.indexOf(','));
      String rightID = description.substring(description.lastIndexOf(' ') + 1,
          description.indexOf(')'));

      Node node = cache.computeIfAbsent(id, Node::new);
      Node left = cache.computeIfAbsent(leftId, Node::new);
      Node right = cache.computeIfAbsent(rightID, Node::new);
      node.setLeft(left).setRight(right);

      if (id.equals("AAA")) {
        start = node;
      }
      if (id.equals("ZZZ")) {
        end = node;
      }
    }
  }

  @Override
  public Object part1() {
    Node current = start;
    int routePointer = 0;
    long steps = 0;
    while (current != end) {
      if (routePointer >= route.length()) {
        routePointer = 0;
      }
      char direction = route.charAt(routePointer++);
      current = direction == 'L' ? current.left() : current.right();
      steps++;
    }
    return steps;
  }

  @Override
  public Object part2() {
    return null;
  }

  static final class Node {

    private final String id;
    private Node left;
    private Node right;

    Node(String id) {
      this.id = id;
    }

    public String id() {return id;}

    public Node left() {return left;}

    public Node right() {return right;}

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
      return "Node[" +
             "id=" + id + ", " +
             "left=" + left.id + ", " +
             "right=" + right.id + ']';
    }
  }
}
