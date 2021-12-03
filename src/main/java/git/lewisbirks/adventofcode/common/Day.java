package git.lewisbirks.adventofcode.common;

import git.lewisbirks.adventofcode.utils.ResourceUtil;

import java.io.File;
import java.util.Objects;

public abstract class Day implements Comparable<Day> {

  protected static final String SEPARATOR = File.separator;
  protected static final String EXAMPLES_FOLDER = "examples";
  protected static final String INPUTS_FOLDER = "inputs";

  private final int num;
  private final String year;

  public Day(int num, String year) {
    this.num = num;
    this.year = year;
  }

  public void process() {
    System.out.printf("Day %02d%n", num);
    try {
      System.out.printf("\tPart 1: %s%n", part1());
      System.out.printf("\tPart 2: %s%n", part2());
    } catch (Exception e) {
      System.err.printf("Failed to process day %02d%n", num);
      e.printStackTrace();
    }
  }

  protected abstract Object part1();

  protected abstract Object part2();

  protected String readFile(String folder) {
    String location = folder + SEPARATOR + year + SEPARATOR + "day" + num + ".txt";
    String input = ResourceUtil.getResourceFileAsString(location);
    return Objects.requireNonNull(input, "input must not be null");
  }

  @Override
  public int compareTo(Day o) {
    return Integer.compare(num, o.num);
  }
}
