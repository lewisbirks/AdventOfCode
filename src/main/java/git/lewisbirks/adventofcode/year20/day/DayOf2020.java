package git.lewisbirks.adventofcode.year20.day;

import git.lewisbirks.adventofcode.common.Day;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class DayOf2020 extends Day {

  public DayOf2020(int num) {
    super(num, "2020");
  }

  protected <I> List<I> getInput(Function<String, I> transformer) {
    return getInput(transformer, Collectors.toUnmodifiableList());
  }

  protected <C> C getInput(Collector<String, ?, C> collector) {return getInput(Function.identity(), collector);}

  protected <I, C> C getInput(Function<String, I> transformer, Collector<I, ?, C> collector) {
    String input = readFile(INPUTS_FOLDER);
    return input.lines().map(transformer).collect(collector);
  }

  protected <I> List<I> getExample(Function<String, I> transformer) {
    return getExample(transformer, Collectors.toUnmodifiableList());
  }

  protected <C> C getExample(Collector<String, ?, C> collector) {return getExample(Function.identity(), collector);}

  protected <I, C> C getExample(Function<String, I> transformer, Collector<I, ?, C> collector) {
    String input = readFile(EXAMPLES_FOLDER);
    return input.lines().map(transformer).collect(collector);
  }
}
