package git.lewisbirks.adventofcode.common;

import java.io.IOException;

public abstract class Day<I, O> implements Comparable<Day<?, ?>>{

  private final int num;

  public Day(int num) {
    this.num = num;
  }

  // REFACTOR: 01/12/2021 will require changing if a day has 2 different inputs,
  //  probs just read the input as part of the part that needs it
  public void process() {
    System.out.printf("Day %02d%n", num);
    try {
      I input = readInput("day" + num + ".txt");
      O result = part1(input);
      System.out.printf("\tPart 1: %s%n", result);
      result = part2(input);
      System.out.printf("\tPart 2: %s%n", result);
    } catch (IOException e) {
      System.err.printf("Failed to process day %02d%n", num);
      e.printStackTrace();
    }
  }

  protected abstract O part1(I input);

  protected abstract O part2(I input);

  protected abstract I readInput(String file) throws IOException;

  @Override
  public int compareTo(Day<?, ?> o) {
    return Integer.compare(num, o.num);
  }
}
