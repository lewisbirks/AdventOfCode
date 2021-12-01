package git.lewisbirks.adventofcode.day;

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
  Long calculateResultsPart1(List<Long> input) {
    return IntStream.range(1, input.size()).filter(i -> input.get(i) > input.get(i - 1)).count();
  }

  @Override
  Long calculateResultsPart2(List<Long> input) {
    return IntStream.range(3, input.size()).filter(i -> {
      long previousWindow = input.get(i - 3) + input.get(i - 2) + input.get(i - 1);
      long currentWindow = input.get(i - 2) + input.get(i - 1) + input.get(i);
      return previousWindow < currentWindow;
    }).count();
  }

  @Override
  List<Long> readInput(String file) throws IOException {
    String inputStrings = ResourceUtil.getResourceFileAsString(file);
    if (inputStrings == null) {
      return List.of();
    }
    return Arrays.stream(inputStrings.split(System.lineSeparator()))
        .map(Long::parseLong)
        .collect(Collectors.toList());
  }
}
