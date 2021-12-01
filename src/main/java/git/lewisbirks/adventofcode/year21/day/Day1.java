package git.lewisbirks.adventofcode.year21.day;

import git.lewisbirks.adventofcode.common.Day;
import git.lewisbirks.adventofcode.utils.ResourceUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day1 extends Day<List<Long>, Long> {

  public Day1() {
    super(1);
  }

  @Override
  protected Long part1(List<Long> input) {
    return calculate(input, 1);
  }

  @Override
  protected Long part2(List<Long> input) {
    return calculate(input, 3);
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

  @Override
  protected List<Long> readInput(String file) throws IOException {
    String inputStrings = ResourceUtil.getResourceFileAsString(file);
    if (inputStrings == null) {
      return List.of();
    }
    return Arrays.stream(inputStrings.split(System.lineSeparator()))
        .map(Long::parseLong)
        .collect(Collectors.toList());
  }
}
