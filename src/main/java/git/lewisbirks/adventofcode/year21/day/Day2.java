package git.lewisbirks.adventofcode.year21.day;

import git.lewisbirks.adventofcode.common.Day;
import git.lewisbirks.adventofcode.utils.ResourceUtil;
import git.lewisbirks.adventofcode.year21.utils.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Day2 extends Day {

  private List<Vector> vectors;

  public Day2() {
    super(2);
    vectors = null;
  }

  @Override
  protected Object part1() {
    int depth = 0;
    int horizontal = 0;

    for (Vector vector : readInput()) {
      switch (vector.direction) {
        case UP:
          depth -= vector.distance;
          break;
        case DOWN:
          depth += vector.distance;
          break;
        case FORWARD:
          horizontal += vector.distance;
          break;
      }
    }

    return depth * horizontal;
  }

  @Override
  protected Object part2() {
    int depth = 0;
    int horizontal = 0;
    int aim = 0;

    for (Vector vector : readInput()) {
      switch (vector.direction) {
        case UP:
          aim -= vector.distance;
          break;
        case DOWN:
          aim += vector.distance;
          break;
        case FORWARD:
          horizontal += vector.distance;
          depth += aim * vector.distance;
          break;
      }
    }

    return depth * horizontal;
  }


  private List<Vector> readInput() {
    vectors = Objects.requireNonNullElseGet(vectors, () -> {
      String input = ResourceUtil.getResourceFileAsString("day2.txt");
      Objects.requireNonNull(input, "input must not be null");
      return input.lines().map(Vector::of).collect(Collectors.toUnmodifiableList());
    });
    return vectors;
  }
}
