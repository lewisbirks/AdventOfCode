package git.lewisbirks.adventofcode.year21.day;

import git.lewisbirks.adventofcode.common.Day;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class DayOf2021 extends Day {

  public DayOf2021(int num) {
    super(num, "2021");
  }

  protected <I> List<I> getInput(Function<String, I> transformer) {
    String input = readFile(INPUTS_FOLDER);
    return input.lines().map(transformer).collect(Collectors.toUnmodifiableList());
  }

  protected <I> List<I> getExample(Function<String, I> transformer) {
    String input = readFile(EXAMPLES_FOLDER);
    return input.lines().map(transformer).collect(Collectors.toUnmodifiableList());
  }
}
