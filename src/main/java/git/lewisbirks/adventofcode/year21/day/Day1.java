package git.lewisbirks.adventofcode.year21.day;

import git.lewisbirks.adventofcode.common.Day;
import git.lewisbirks.adventofcode.utils.ResourceUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day1 extends Day {

  private List<Long> depths;

  public Day1() {
    super(1);
    depths = null;
  }

  @Override
  protected Object part1() {
    return calculate(getDepths(), 1);
  }

  @Override
  protected Object part2() {
    return calculate(getDepths(), 3);
  }

  private Long calculate(List<Long> input, int windowSize) {
    return IntStream.range(windowSize, input.size())
        .filter(i -> isWindowIncreasing(input, i, windowSize))
        .count();
  }

  private boolean isWindowIncreasing(List<Long> input, int index, int windowSize) {
    long previousWindow = 0;
    long currentWindow = 0;
    for (int offset = 0; offset < windowSize; offset++) {
      previousWindow += input.get(index - offset - 1);
      currentWindow += input.get(index - offset);
    }
    return previousWindow < currentWindow;
  }

  private List<Long> getDepths() {
    depths = Objects.requireNonNullElseGet(depths, () -> {
      String input = ResourceUtil.getResourceFileAsString("day1.txt");
      Objects.requireNonNull(input, "input must not be null");
      return Arrays.stream(input.split(System.lineSeparator()))
          .map(Long::parseLong)
          .collect(Collectors.toList());
    });
    return depths;
  }
}
